package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.UUID;

import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Group;
import org.openapitools.model.Location;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchingService {
    
    // Do matching (run the algorithim)
    // Send out invites
    // Update Statuses
    // Check if re-mathching is needed


    // handles MatchRequests
    @Autowired
    MatchRequestService matchRequestService;

    // handles Matches
    @Autowired
    MatchService matchService;

    // handles Groups
    @Autowired
    GroupService groupService;

    // handles genAI conversation starters
    @Autowired
    ConversationStarterService conversationStarterService;

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

}
