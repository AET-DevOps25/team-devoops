package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.MatchCollection;
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
     * Runs the matching algorithm and generates groups
     * 
     * @param date the date for which to run the algorithm
     * @return List of all matched groups
     */
    public List<Group> match(LocalDate date){

        // --------------------
        // Part 1: Prepare Data
        // --------------------

        // get all unmatched requests for today's date
        MatchRequestCollection requests = requestService.getUnmatchedRequests(date);

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
        MatchingAlgorithm algorithm = new MatchingAlgorithm(users, requests);

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
            throw new ScheduleException("Operation expireMatches() cannot be run before 11:00!");
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

}
