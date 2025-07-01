package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.Group;
import org.openapitools.model.Location;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;

import meet_at_mensa.matching.algorithm.MatchingAlgorithm;
import meet_at_mensa.matching.algorithm.MatchingSolution;
import meet_at_mensa.matching.algorithm.MatchingSolutionBlock;
import meet_at_mensa.matching.client.UserClient;

public class MatchingService {
    
    // Send out invites
    // Update Statuses
    // Check if re-mathching is needed
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

            // for successfully matched groups
            if (solutionBlock.getStatus() == RequestStatus.MATCHED) {

                // update request status to matched
                for (MatchRequest request : solutionBlock.getRequests().getRequests()) {
                    requestService.updateRequstStatus(request.getRequestID(), RequestStatus.MATCHED);
                }

                // create a Group, Matches, and ConversationStarters
                groups.add(
                    createGroup(
                        solutionBlock.getUsers(),
                        solutionBlock.getDate(),
                        solutionBlock.getTime(),
                        solutionBlock.getLocation()
                    )
                );

            // for Unmatchable requests
            } else if (solutionBlock.getStatus() == RequestStatus.UNMATCHABLE) {

                // update request status to unmatchable
                for (MatchRequest request : solutionBlock.getRequests().getRequests()) {
                    requestService.updateRequstStatus(request.getRequestID(), RequestStatus.UNMATCHABLE);
                }
            }
        }

        // return groups once completed
        return groups;
    }

}
