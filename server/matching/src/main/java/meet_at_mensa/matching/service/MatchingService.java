package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.MatchStatus;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.MatchCollection;
import org.openapitools.model.MatchRequestNew;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.Location;
import org.openapitools.model.Match;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import meet_at_mensa.matching.MatchingApplication;
import meet_at_mensa.matching.algorithm.ClusteringAlgorithm;
import meet_at_mensa.matching.algorithm.MatchingAlgorithm;
import meet_at_mensa.matching.algorithm.MatchingSolution;
import meet_at_mensa.matching.algorithm.MatchingSolutionBlock;
import meet_at_mensa.matching.client.UserClient;
import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.exception.IllegalInputException;
import meet_at_mensa.matching.exception.ScheduleException;

@Service
public class MatchingService {
    
    // Send out invites
    // group health
    // Update Statuses
    // Check if re-matching is needed
    // remove group


    // handles MatchRequests
    @Autowired
    MatchRequestService requestService;

    // handles Matches
    @Autowired
    MatchService matchService;

    // handles Groups
    @Autowired
    GroupService groupService;

    // handles genAI conversation starters
    @Autowired
    ConversationStarterService conversationStarterService;

    // hanles user-service calls
    @Autowired
    UserClient userClient;


    /**
     * Sends out an invite email for the corresponding matchID
     * 
     * TODO: @AK Implement this function
     * 
     * @param matchID the matchID of the invite being sent
     * @return Match object of the updated match
     */
    public Match sendInvite(UUID matchID){

        // get match object
        Match match = matchService.getMatch(matchID);

        // get user object for email and data
        // WARNING: This function currently fails due to auth issues
        User user = userClient.getUser(match.getUserID());
        
        //
        //
        // TODO: Craft and send email
        //
        //

        // Update match status to SENT
        match = matchService.updateStatus(matchID, InviteStatus.SENT);

        // return updated match
        return match;
    }

    /**
     * Handle an incomming response to an invite
     * 
     * 
     * @param matchID the matchID of the invite being sent
     * @param response the new status set by the response
     * @return Match object of the updated match
     */
    public Match respondInvite(UUID matchID, InviteStatus response){

        // get match object
        Match match = matchService.getMatch(matchID);

        if(response == InviteStatus.CONFIRMED || response == InviteStatus.REJECTED) {

            // Update match status to SENT
            match = matchService.updateStatus(matchID, response);

        } else {

            // throw exception if response is EXPIRED, UNSENT or SENT
            throw new IllegalInputException("User response to invite was invalid");

        }

        // return updated match
        return match;
    }

    /**
     * Creates entries in the Group and Match tables for a new matched group
     *
     * @param Users UserCollection of users
     * @param date LocalDate date of the group meeting
     * @param time Integer timeslot of the group meeting
     * @param location Location of the group meeting
     */
    public Group createGroup(UserCollection users, LocalDate date, Integer time, Location location) {

        // Creating a Group object requires entries across 3 database tables:
            // 1: groupRepository (managed by GroupService)
                // This contains information about the time and place of the meeting
            // 2: matchRepository (managed by MatchService)
                // This contains information about individual users's invite status
            // 3: promptRepository (managed by conversationStarterService)
                // This containst the conversation starter prompts

        // Step 1: GroupEntity
        // this creates an entry in groupRepository and generates a groupID which we can use for matches and prompts
        UUID groupID = groupService.registerGroup(date, time, location);

        // Step 2: MatchEntities
        // this creates an entry in matchRepository for each user
        for (User user : users.getUsers()) {
            matchService.registerMatch(user.getUserID(), groupID);
        }

        // Step 3: PromptEntities
        // this creates an entry in promptRepository for the group
        conversationStarterService.registerPrompts(
            groupID, 
            conversationStarterService.generatePrompts(users)
        );

        // fetch the group object to return
        return groupService.getGroup(groupID);

    }


    /**
     * Perform a group Health Check.
     * 
     * Forces a rematch if a group is no longer viable
     * 
     * @param date date of groups to health-check
     * @param strict 
     * False: check if there are too many rejections
     * True: check if there are not enough confirmations
     *
     */
    public void groupHealthCheck(LocalDate date, Boolean strict) {

        // check all groups on a given date
        for (Group group : groupService.getGroupsOnDate(date)) {

            int rejected = 0;
            int confirmed = 0;

            for (MatchStatus userStatus : groupService.getGroupStatus(group.getGroupID())) {
                
                if(userStatus.getStatus() == InviteStatus.CONFIRMED) {

                    confirmed++;

                } else if (userStatus.getStatus() == InviteStatus.REJECTED) {

                    rejected++;

                }

            }

            // if the strict flag is set
            if (strict) {

                // Rematch if less than 2 people have confirmed
                if (confirmed <= 2) {

                    rematchGroup(group.getGroupID(), strict);

                }

            } else {

                // Rematch if so many have rejected that a group is not possible
                if (group.getUserStatus().size() - rejected <= 2) {

                    rematchGroup(group.getGroupID(), strict);

                }
            }

        }

    }



    /**
     * Dissolves a group and forces a rematch
     * 
     * 
     * @param groupID UUID of the group to rematch
     * @param strict 
     * False: rematch CONFIRMED and SENT requests, expire REJECTED
     * True: rematch CONFIRMED, expire SENT and REJECTED
     *
     */
    public void rematchGroup(UUID groupID, Boolean strict) {

        Group group = groupService.getGroup(groupID);

        // get all matches for a the given group
        MatchCollection matches = matchService.getMatchesByGroup(group.getGroupID());

        for (Match match : matches.getMatches()) {

            // mark the match as expired
            matchService.updateStatus(match.getMatchID(), InviteStatus.EXPIRED);
                
            // if a match has been sent but not responded to
            if (match.getStatus() == InviteStatus.CONFIRMED) {

                // TODO send out Email informing of change

                // update Request status to REMATCH
                requestService.updateRequestStatus(
                    requestService.getUserRequestOn(group.getDate(), match.getUserID()).getRequestID(),
                    RequestStatus.REMATCH
                );

            } else if (match.getStatus() == InviteStatus.SENT) {

                // if strict then expire SENT requests
                if (strict) {

                    // TODO send out Email informing of change

                    requestService.updateRequestStatus(
                        requestService.getUserRequestOn(group.getDate(), match.getUserID()).getRequestID(),
                        RequestStatus.EXPIRED
                    );

                // if not strict rematch SENT requests
                } else {

                    // TODO send out Email informing of change

                    requestService.updateRequestStatus(
                        requestService.getUserRequestOn(group.getDate(), match.getUserID()).getRequestID(),
                        RequestStatus.REMATCH
                    );

                }

            }

        }

    }


    /**
     * Runs the matching algorithm and generates groups
     * 
     * @param date the date for which to run the algorithm
     * @return List of all matched groups
     */
    public List<Group> match(LocalDate date, Location location){

        // --------------------
        // Part 1: Prepare Data
        // --------------------

        // get all unmatched requests for today's date
        MatchRequestCollection allRequests = requestService.getUnmatchedRequests(date);

        // filter out requests by Mensa
        MatchRequestCollection requests = new MatchRequestCollection(
            allRequests.getRequests().stream()
                .filter(filterRequest -> filterRequest.getLocation() == location)
                .collect(Collectors.toList())
        );

        // get list of all user UUIDs
        List<UUID> userIDs = new ArrayList<>();
        for (MatchRequest request : requests.getRequests()) {
            userIDs.add(request.getUserID());
        }

        // get all Users from today's Requests
        UserCollection users = userClient.getUsers(userIDs);

        // --------------------
        // Part 2: Run Algorithm
        // --------------------

        // Prepare matching algorithm
        MatchingAlgorithm algorithm = new ClusteringAlgorithm(users, requests);

        // Generate solution
        MatchingSolution solution = algorithm.generateSolution();

        // --------------------
        // Part 3: Create Groups
        // --------------------

        // create empty group list
        List<Group> groups = new ArrayList<>();

        // for each group in the solution
        for (MatchingSolutionBlock solutionBlock : solution.getSolution()) {

            // register entries in all tables and add to groups list
            groups.add(implementSolution(solutionBlock));
          
        }

        // return groups once completed
        return groups;
    }


    /**
     * Creates all necessary entries corresponding to a given solution block
     * 
     * @param solution a group of users matched by the algorithm
     * @return a group created containing all the users
     */
    public Group implementSolution(MatchingSolutionBlock solution){

        // for successfully matched groups
        if (solution.getStatus() == RequestStatus.MATCHED) {

            // Update each match request to MATCHED status
            for (MatchRequest request : solution.getRequests().getRequests()) {
                requestService.updateRequestStatus(request.getRequestID(), RequestStatus.MATCHED);
            }

            // create a Group, Matches, and ConversationStarters
            Group group = createGroup(
                solution.getUsers(), 
                solution.getDate(), 
                solution.getTime(), 
                solution.getLocation()
            );

            return group;

        // for Unmatchable requests
        } else if (solution.getStatus() == RequestStatus.UNMATCHABLE) {

            // Update each match request to UNMATCHABLE status
            for (MatchRequest request : solution.getRequests().getRequests()) {
                requestService.updateRequestStatus(request.getRequestID(), RequestStatus.UNMATCHABLE);
            }

            return null;

        } else {return null;}

    }

    /**
     * Sets any match statuses that are SENT to EXPIRED for groups meeting today
     * 
     * WARNING: This expires matches for TODAY, and throws an exception if it's run before the Mensa opens at 11:00
     * 
     * @throws ScheduleException if run before 11:00
     */
    protected void expireMatches() {

        // Throws an exception if this function is triggered before 15:00
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))){
            // TODO: Figure out a better way to handle this edge case
            //throw new ScheduleException("Operation expireMatches() cannot be run before 11:00!");
        }

        List<Group> groups;

        
        try { // get all groups from today

            groups = groupService.getGroupsOnDate(LocalDate.now());
        
        } catch (GroupNotFoundException e) { // if no group is found today, return
        
            return;

        }

        
        for (Group group : groups) {
            
            // get all matches for a the given group
            MatchCollection matches = matchService.getMatchesByGroup(group.getGroupID());

            for (Match match : matches.getMatches()) {
                
                // if a match has been sent but not responded to
                if (match.getStatus() == InviteStatus.SENT) {

                    // mark as expired
                    matchService.updateStatus(match.getMatchID(), InviteStatus.EXPIRED);

                }

            }

        }

    }
    
    
    /**
     * Deletes Groups and Matches that are older than 7 days
     * 
     */
    protected void cleanupExpired() {

        // get all groups older than a week
        List<Group> groups = groupService.getGroupsOlderThan(LocalDate.now().minusDays(7));

        for (Group group : groups) {
            
            // get all matches
            MatchCollection matches = matchService.getMatchesByGroup(group.getGroupID());

            // Remove all matches
            for (Match match : matches.getMatches()) {

                matchService.removeMatch(match.getMatchID());

            }

            // Remove group and conversation starters
            groupService.removeGroup(group.getGroupID());

        }

    }


    /**
     * Generate a Demo-Match with placeholder users
     * 
     * @param groupID UUID of the group to rematch
     * @param strict 
     * False: rematch CONFIRMED and SENT requests, expire REJECTED
     * True: rematch CONFIRMED, expire SENT and REJECTED
     *
     */
    public Group createDemoMatch(MatchRequestNew demoRequest) {

        // get this user and demo users from UserService
        User thisUser = userClient.getUser(demoRequest.getUserID());

        // add database entries for the request
        MatchRequest thisRequest = requestService.registerRequest(demoRequest);

        // get demo users from UserService
        UserCollection demoUsers = userClient.getDemoUsers();

        // Create a collection of demo requests
        MatchRequestCollection demoUserRequests = new MatchRequestCollection();
        for (User demoUser : demoUsers.getUsers()) {
            
            // register a request identical to demoRequest but from one of the demoUsers
            MatchRequest demoUserRequest = requestService.registerRequest(
                new MatchRequestNew(
                    demoUser.getUserID(),
                    demoRequest.getDate(),
                    demoRequest.getTimeslot(),
                    demoRequest.getLocation(),
                    demoRequest.getPreferences()
                )  
            );

            // add generated request to list
            demoUserRequests.addRequestsItem(demoUserRequest);

        }

        // add real user to collections
        demoUsers.addUsersItem(thisUser);
        demoUserRequests.addRequestsItem(thisRequest);

        // Create Solution block
        MatchingSolutionBlock demoSolution = new MatchingSolutionBlock(
            demoUsers,
            demoUserRequests,
            demoRequest.getDate(),
            demoRequest.getTimeslot().get(0),
            demoRequest.getLocation(),
            RequestStatus.MATCHED
        );

        // implement solution
        Group demoGroup = implementSolution(demoSolution);

        // return group object that was just created
        return demoGroup;

    }

}
