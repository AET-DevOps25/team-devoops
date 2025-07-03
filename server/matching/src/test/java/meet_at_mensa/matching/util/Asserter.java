package meet_at_mensa.matching.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.MatchStatus;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;

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


}
