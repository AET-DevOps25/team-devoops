package meet_at_mensa.matching.client;

import org.openapitools.client.api.GenAiApi;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.RestException;

import org.openapitools.client.ApiClient;

import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;

/**
 * GenAiClient uses the generated java client to handle REST requests to the GenAi-Microservice
 *
 */
@Service
public class GenAiClient {

    private ApiClient defaultClient;

    public GenAiClient() {

        // get new client
        this.defaultClient = new ApiClient();

        // set path
        this.defaultClient.setBasePath("http://meetatmensa-genai:80");

    }


    /**
     * Uses a Generated client to a REST request to genai-service for a ConverationStarterCollection object
     * 
     *
     * @param users server-style UserCollection object of the user being fetched
     * @return server-style ConversationStarterCollection object (org.openapitools.model.ConversationStarterCollection)
     */
    public ConversationStarterCollection getPrompts(UserCollection users) {

        // create instance of the API
        GenAiApi apiInstance = new GenAiApi(this.defaultClient);

        System.out.println("Users being sent:" + users.toString());

        try {

            // initiate object
            org.openapitools.client.model.ConversationStarterCollection clientCollection;

            // try to fetch prompts from ConversationStarter
            clientCollection = apiInstance.getApiV2GenaiConversationStarter(

                // convert from server-type to client-type
                convertServerUserCollectionToClientUserCollection(users)

            );

            // convert from client-type to server-type
            return convertClientPromptsToServerPrompts(clientCollection);

        } catch (Exception e) {

            throw new RestException(e.toString());

        }
    }



    /**
     * Converts a Server-Style User object into a Client-Style User object.
     * 
     * Generated clients and servers are generated separatly, and each have their own version of model objects
     * A user from org.openapitools.model.User and a user from org.openapitools.client.model.User have the same
     * fields, but are distinct in a formal sense. 
     *
     * These conversion functions convert between client-type objects returned by generated client functions
     * and server-type objects used by the rest of the application
     *
     * @param serverUser server-style user object (org.openapitools.model.User)
     * @return clientUser client-style user object (org.openapitools.client.model.User)
     */
    private org.openapitools.client.model.User convertServerUserToClientUser(User serverUser) {

        // create new object of client type
        org.openapitools.client.model.User clientUser =  new org.openapitools.client.model.User();

        // copy fields
        clientUser.setUserID(serverUser.getUserID());
        clientUser.setEmail(serverUser.getEmail());
        clientUser.setFirstname(serverUser.getFirstname());
        clientUser.setLastname(serverUser.getLastname());
        clientUser.setBirthday(serverUser.getBirthday());
        clientUser.setGender(serverUser.getGender());
        clientUser.setDegree(serverUser.getDegree());
        clientUser.setDegreeStart(serverUser.getDegreeStart());
        clientUser.setInterests(serverUser.getInterests());
        clientUser.setBio(serverUser.getBio());

        // return object
        return clientUser;

    }


    /**
     * Converts a Server-Style UserCollection object into a Client-Style UserCollection object.
     * 
     * Generated clients and servers are generated separatly, and each have their own version of model objects
     * A user from org.openapitools.model.User and a user from org.openapitools.client.model.User have the same
     * fields, but are distinct in a formal sense. 
     *
     * These conversion functions convert between client-type objects returned by generated client functions
     * and server-type objects used by the rest of the application
     *
     * @param serverUsers server-style userCollection object (org.openapitools.model.UserCollection)
     * @return clientUsers client-style userCollection object (org.openapitools.client.model.UserCollection)
     */
    private org.openapitools.client.model.UserCollection convertServerUserCollectionToClientUserCollection(UserCollection serverUsers) {

        // create new client-type UserCollection
        org.openapitools.client.model.UserCollection clientUsers = new org.openapitools.client.model.UserCollection();

        // convert to client-user and add to collection
        for (User serverUser : serverUsers.getUsers()) {
            clientUsers.addUsersItem(
                convertServerUserToClientUser(serverUser)
            );
        }

        // return object
        return clientUsers;

    }

    
    /**
     * Converts a Client-Style ConversationStarter object into a Server-Style ConversationStarter object.
     * 
     * Generated clients and servers are generated separatly, and each have their own version of model objects
     * A user from org.openapitools.model.User and a user from org.openapitools.client.model.User have the same
     * fields, but are distinct in a formal sense. 
     *
     * These conversion functions convert between client-type objects returned by generated client functions
     * and server-type objects used by the rest of the application
     *
     * @param clientPromt Client-Style ConversationStarter object (org.openapitools.client.model.ConversationStarter)
     * @return serverPrompt Server-Style ConversationStarter object (org.openapitools.model.ConversationStarter)
     */
    private ConversationStarter convertClientPromptToServerPrompt(org.openapitools.client.model.ConversationStarter clientPrompt) {

        // create new object of server type
        ConversationStarter serverPrompt = new ConversationStarter();

        // copy fields
        serverPrompt.setPrompt(clientPrompt.getPrompt());

        // return server object
        return serverPrompt;
        
    }


    /**
     * Converts a Client-Style ConversationStarterCollection object into a Server-Style ConversationStarterCollection object.
     * 
     * Generated clients and servers are generated separatly, and each have their own version of model objects
     * A user from org.openapitools.model.User and a user from org.openapitools.client.model.User have the same
     * fields, but are distinct in a formal sense. 
     *
     * These conversion functions convert between client-type objects returned by generated client functions
     * and server-type objects used by the rest of the application
     *
     * @param clientPromts Client-Style ConversationStarterCollection object (org.openapitools.client.model.ConversationStarterCollection)
     * @return serverPrompts Server-Style ConversationStarterCollection object (org.openapitools.model.ConversationStarterCollection)
     */
    private ConversationStarterCollection convertClientPromptsToServerPrompts(org.openapitools.client.model.ConversationStarterCollection clientPrompts) {

        // create new object of server type
        ConversationStarterCollection serverPrompts = new ConversationStarterCollection();

        // convert to server-type and add to collection
        for (org.openapitools.client.model.ConversationStarter clientPrompt : clientPrompts.getConversationsStarters()) {

            serverPrompts.addConversationsStartersItem(
                convertClientPromptToServerPrompt(clientPrompt)
            );
        }

        // return server object
        return serverPrompts;
        
    }

}
