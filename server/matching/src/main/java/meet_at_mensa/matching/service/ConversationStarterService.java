package meet_at_mensa.matching.service;

import java.util.UUID;

import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.model.PromptEntity;
import meet_at_mensa.matching.repository.PromptRepository;

public class ConversationStarterService {

    @Autowired
    private PromptRepository promptRepository;

    /**
     * Calls remote service to generate prompts for a given group
     *
     * This function only generates the propmts, to add them to the database registerPrompts must be called separately!
     * 
     * TODO: This function is currently unimplemented, and returns placeholder values
     *
     * @param users UserCollection object with all users in a given group
     * @return generated ConversationStarterCollection
     * @throws RestException if the call fails
     */
    public ConversationStarterCollection generatePrompts(UserCollection users) {

        // create empty Collection
        ConversationStarterCollection collection = new ConversationStarterCollection();

        // TODO: this is a dummy implementation. This should just the ConversationStarterCollection returned by the GenAi service
        for (User user : users.getUsers()) {
            
            // new placeholder prompt
            ConversationStarter prompt = new ConversationStarter("This is a placeholder, it should be fetched from the GenAi microservice");

            // add to collection
            collection.addConversationsStartersItem(prompt);
        }

        // return collection
        return collection;

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

}
