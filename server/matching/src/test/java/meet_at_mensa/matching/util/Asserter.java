package meet_at_mensa.matching.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openapitools.client.model.MatchPreferences;
import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.MatchStatus;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;

import meet_at_mensa.matching.algorithm.MatchingSolution;
import meet_at_mensa.matching.algorithm.MatchingSolutionBlock;

public class Asserter {
    

    public static void assertConversationStarterCollectionsMatch(ConversationStarterCollection collection1, ConversationStarterCollection collection2) {

        assertNotNull(collection1, "Value should not be null!");
        assertNotNull(collection2, "Value should not be null!");

        Set<String> prompts1 = collection1.getConversationsStarters().stream()
            .map(ConversationStarter::getPrompt)
            .collect(Collectors.toSet());

        Set<String> prompts2 = collection2.getConversationsStarters().stream()
            .map(ConversationStarter::getPrompt)
            .collect(Collectors.toSet());
        
        assertEquals(prompts1, prompts2, "Conversation Starter prompts do not match!");

    }

    public static void assertUserIDsMatchCollection(List<UUID> userIDs, UserCollection collection) {

        assertNotNull(userIDs, "Value should not be null!");
        assertNotNull(collection, "Value should not be null!");

        Set<UUID> ids1 = new HashSet<UUID>(userIDs);
        Set<UUID> ids2 = collection.getUsers().stream()
                            .map(User::getUserID)
                            .collect(Collectors.toSet());

        assertEquals(ids1, ids2, "UserIDs do not match!");

    }

    public static void assertMatchStatusCreatedSuccessfully(List<UUID> userIDs, List<MatchStatus> userStatus) {

        assertNotNull(userIDs, "Value should not be null!");
        assertNotNull(userStatus, "Value should not be null!");

        Set<UUID> ids1 = new HashSet<>(userIDs);
        Set<UUID> ids2 = userStatus.stream()
                            .map(MatchStatus::getUserID)
                            .collect(Collectors.toSet());

        // check that both lists contain the same IDs
        assertEquals(ids1, ids2, "UserIDs should match!");

        // check that all statuses are set to UNSENT
        for (MatchStatus matchStatus : userStatus) {
            assertEquals(matchStatus.getStatus(), InviteStatus.UNSENT, "Status should be UNSENT!");
        }
    }

    public static void assertConversationStarterCollectionGeneratedProperly(ConversationStarterCollection collection) {
        
        assertNotNull(collection, "Value should not be null!");

        for (ConversationStarter prompt : collection.getConversationsStarters()) {
            
            assertNotNull(prompt.getPrompt(), "Prompt should not be null!");

        }

    }

    public static void assertUserStatusesAreIdentical(List<MatchStatus> status1, List<MatchStatus> status2) {

        Set<UUID> ids1 = status1.stream()
                            .map(MatchStatus::getUserID)
                            .collect(Collectors.toSet());
        Set<UUID> ids2 = status1.stream()
                            .map(MatchStatus::getUserID)
                            .collect(Collectors.toSet());

        // check that the users are present in both sets
        assertEquals(ids1, ids2);

        // check that the values for status are identical
        for (MatchStatus stat1: status1) {
            
            for (MatchStatus stat2: status2) {
                
                if (stat1.getUserID() == stat2.getUserID()){

                    assertEquals(stat1.getStatus(), stat2.getStatus(), "Statuses should match");

                }

            }

        }

    }

    public static void assertGroupsAreIdentical(Group group1, Group group2) {

        // basic values
        assertEquals(group1.getGroupID(), group2.getGroupID(), "Group IDs should match");
        assertEquals(group1.getDate(), group2.getDate(), "Dates should match!");
        assertEquals(group1.getTime(), group2.getTime(), "Timeslots should match!");
        assertEquals(group1.getLocation(), group2.getLocation(), "Locations should match!");
        
        // conversations starters
        assertConversationStarterCollectionsMatch(group1.getConversationStarters(), group2.getConversationStarters());

        // UserStatus
        assertUserStatusesAreIdentical(group1.getUserStatus(), group2.getUserStatus());

    }

    public static void assertMatchRequestsAreIdentical(MatchRequest request1, MatchRequest request2) {

        // check that neither value is null
        assertNotNull(request1, "Value should not be null");
        assertNotNull(request2, "Value should not be null");

        // check that basic values match
        assertEquals(request1.getRequestID(), request2.getRequestID(), "RequestIDs should be identical");
        assertEquals(request1.getUserID(), request2.getUserID(), "RequestIDs should be identical");
        assertEquals(request1.getDate(), request2.getDate(), "RequestIDs should be identical");
        assertEquals(request1.getLocation(), request2.getLocation(), "RequestIDs should be identical");
        assertEquals(request1.getStatus(), request2.getStatus(), "RequestIDs should be identical");

        // check that timeslots are the same
        assertEquals(
            new HashSet<>(request1.getTimeslot()),
            new HashSet<>(request2.getTimeslot()),
            "Timeslots should Match"
        );

        assertEquals(request1.getPreferences().getAgePref(), request2.getPreferences().getAgePref(), "Preferences should match");
        assertEquals(request1.getPreferences().getDegreePref(), request2.getPreferences().getDegreePref(), "Preferences should match");
        assertEquals(request1.getPreferences().getGenderPref(), request2.getPreferences().getGenderPref(), "Preferences should match");

    }

    public static void assertSolutionIsValid(MatchingSolution solution) {


        for (MatchingSolutionBlock solutionBlock : solution.getSolution()) {
            
            
            // check group sizes if not Unmatchable
            if (solutionBlock.getStatus() == RequestStatus.UNMATCHABLE) {

                assertEquals(null, solutionBlock.getTime(), "Timeslot should be null for unmatched users");
                assertEquals(null, solutionBlock.getDate(), "Date should be null for unmatched users");
                assertEquals(null, solutionBlock.getLocation(), "Date should be null for unmatched users");
                
            } else if (solutionBlock.getStatus() == RequestStatus.MATCHED) {
                
                // Check Requests
                for (MatchRequest matchRequest : solutionBlock.getRequests().getRequests()) {
                    
                    // Dates match
                    assertEquals(solutionBlock.getDate(), matchRequest.getDate(), "Dates should match");
    
                    // Locations match
                    assertEquals(solutionBlock.getLocation(), matchRequest.getLocation(), "Dates should match");
    
                    // Starttimes OK
                    assertTrue(
                        matchRequest.getTimeslot().contains(solutionBlock.getTime()),
                        "Users should be available when they were matched"
                    );
    
                    // Availability OK
                    assertTrue(
                        matchRequest.getTimeslot().contains(solutionBlock.getTime())
                        && matchRequest.getTimeslot().contains(solutionBlock.getTime() + 1)
                        && matchRequest.getTimeslot().contains(solutionBlock.getTime() + 2),
                        "Users should be available for at least 45 minutes after the start time but: " +
                        "Start time: " + solutionBlock.getTime() + " and User: " + matchRequest.getTimeslot().toString()
                    );
    
                }

                assertTrue(solutionBlock.getUsers().getUsers().size() <= 5, "Groups should have at most 5 users");

            }
            

        }
    }

}
