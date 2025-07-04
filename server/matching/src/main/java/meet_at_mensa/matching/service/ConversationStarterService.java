package meet_at_mensa.matching.service;

import java.util.UUID;

import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.client.GenAiClient;
import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.exception.RestException;
import meet_at_mensa.matching.model.PromptEntity;
import meet_at_mensa.matching.repository.PromptRepository;

@Service
public class ConversationStarterService {

    @Autowired
    private PromptRepository promptRepository;

    /**
     * Calls remote service to generate prompts for a given group
     * 
     * TODO: Currently this function catches the REST exceptions thrown by GenAiClient
     * and substitutes placeholder values. This behavior is not desirable in production
     *
     * @param users UserCollection object with all users in a given group
     * @return generated ConversationStarterCollection
     * @throws RestException if the call fails (See TODO)
     */
    public ConversationStarterCollection generatePrompts(UserCollection users) {

        // initialize genAI client
        GenAiClient genAiClient = new GenAiClient();

        ConversationStarterCollection prompts;

        try {

            // try to get conversation starters
            prompts = genAiClient.getPrompts(users);
            
        } catch (RestException e) {

            System.out.println(e.toString());

            // generate dummy prompts if REST call fails
            prompts = generateDummyPrompts(users.getUsers().size());

        }

        
        return prompts;

    }

    /**
     * Fetches all prompts for a given group
     *
     *
     * @param groupID UUID of the group the prompts correspond to
     * @return List of prompts fetched from the database after the operation
     */
    public ConversationStarterCollection getPrompts(UUID groupID) {

        // find all prompts belonging to a specific group
        Iterable<PromptEntity> prompts = promptRepository.findByGroupID(groupID);

        // create return object
        ConversationStarterCollection result = new ConversationStarterCollection();

        // add all results to return object
        for (PromptEntity prompt : prompts) {

            // create ConversationStarter object
            ConversationStarter conversationStarter = new ConversationStarter();
            conversationStarter.setPrompt(prompt.getPrompt());

            // ad to return object
            result.addConversationsStartersItem(conversationStarter);
        }

        return result;
    }


    /**
     * Registers a list of prompts for a given group
     *
     *
     * @param groupID UUID of the group the prompts correspond to
     * @param conversationStarters ConversationStarterCollection conversation starter prompts
     * @return ConversationStarterCollection of prompts fetched from the database after the operation
     */
    public ConversationStarterCollection registerPrompts(UUID groupID, ConversationStarterCollection conversationStarters) {

        // save each conversationstarter to the database
        for (ConversationStarter conversationStarter : conversationStarters.getConversationsStarters()) {            
            promptRepository.save(new PromptEntity(groupID, conversationStarter.getPrompt()));
        }

        // return all prompts from library
        return getPrompts(groupID);

    }

    /**
     * Remove all prompts for a given group
     *
     *
     * @param groupID UUID of the group the prompts correspond to
     * @return true if entries were removed
     * @throws GroupNotFoundException if no entries for this group are found
     */
    public Boolean removePrompts(UUID groupID) {

        // search the database for prompts associated with a groupID
        Iterable<PromptEntity> promptEntities = promptRepository.findByGroupID(groupID);

        // if list is empty, throw exception
        if (!promptEntities.iterator().hasNext()) {
            throw new GroupNotFoundException();
        }

        // remove all entries from database
        for (PromptEntity promptEntity : promptEntities) {
            promptRepository.deleteById(promptEntity.getPromptID());
        }

        return true;

    }


    /**
     * Replace all prompts currently in the system with new ones
     *
     *
     * @param groupID UUID of the group the prompts correspond to
     * @param ConversationStarterCollection new conversation starters
     * @return ConversationStarterCollection after the operation is complete
     * @throws GroupNotFoundException if no entries for this group are found
     */
    public ConversationStarterCollection updatePrompts(UUID groupID, ConversationStarterCollection update) {

        removePrompts(groupID);

        registerPrompts(groupID, update);

        return getPrompts(groupID);

    }


    /**
     * Generate "Dummy" placeholder prompts in case rest-service fails
     *
     *
     * @param promptCount how many prompts to add to collection
     * @return Placeholder prompts in a collection
     */
    private ConversationStarterCollection generateDummyPrompts(Integer promptCount) {

        ConversationStarterCollection dummyPrompts = new ConversationStarterCollection();

        for (int i = 0; i < promptCount; i++) {
            
            // add placeholder prompts
            dummyPrompts.addConversationsStartersItem(
                new ConversationStarter(
                    "Failed to get prompt from GenAI microservice. This is a placeholder prompt."
                )
            );
        }

        return dummyPrompts;

    }

}
