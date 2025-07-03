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


}
