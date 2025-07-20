package meet_at_mensa.matching.client;

import java.util.UUID;
import java.util.List;

import org.openapitools.client.ApiClient;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.api.UserApi;

import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.RestException;

/**
 * User Client uses the generated java client to handle REST requests to the User-Microservice
 * 
 *
 */
@Service
public class UserClient {

    // API client object
    private ApiClient defaultClient;

    // Constructor
    public UserClient() {

        // get default client
        this.defaultClient = Configuration.getDefaultApiClient();

        // set path
        this.defaultClient.setBasePath("http://meetatmensa-user:80");
    }



    /**
     * Converts a Client-Style User object into a Server-Style User object.
     * 
     * Generated clients and servers are generated separatly, and each have their own version of model objects
     * A user from org.openapitools.model.User and a user from org.openapitools.client.model.User have the same
     * fields, but are distinct in a formal sense. 
     *
     * These conversion functions convert between client-type objects returned by generated client functions
     * and server-type objects used by the rest of the application
     *
     * @param clientUser client-style user object (org.openapitools.client.model.User)
     * @return serverUser server-style user object (org.openapitools.model.User)
     */
    private User convertClientUserToServerUser(org.openapitools.client.model.User clientUser) {

        return new User(
                clientUser.getUserID(),
                clientUser.getEmail(), 
                clientUser.getFirstname(), 
                clientUser.getLastname(),
                clientUser.getBirthday(), 
                clientUser.getGender(), 
                clientUser.getDegree(), 
                clientUser.getDegreeStart(), 
                clientUser.getInterests(), 
                clientUser.getBio()
        );

    }

    
    /**
     * Uses the Generated client to send a REST request to user-service for a User object
     * 
     *
     * @param userID userID of the user being fetched
     * @return serverUser server-style user object (org.openapitools.model.User)
     */
    public User getUser(UUID userID) {

        // create instance of the API
        UserApi apiInstance = new UserApi(this.defaultClient);

        try {

            org.openapitools.client.model.User userClient;

            // request user Object from user-service
            userClient = apiInstance.getApiV2UserUserID(userID);

            // convert to server-type object and return
            return convertClientUserToServerUser(userClient);

        } catch (Exception e) {
            throw new RestException(e.toString());
        }
    }

    /**
     * Uses the Generated client to send a REST request to user-service for a User object
     * 
     * This method fetches users by their AuthID, and is not designed for external use
     *
     * @param authID userID of the user being fetched
     * @return serverUser server-style user object (org.openapitools.model.User)
     */
    private User getUserByAuthID(String authID) {

        // create instance of the API
        UserApi apiInstance = new UserApi(this.defaultClient);

        try {

            org.openapitools.client.model.User userClient;

            // request user Object from user-service
            userClient = apiInstance.getApiV2UserMeAuthId(authID);

            // convert to server-type object and return
            return convertClientUserToServerUser(userClient);

        } catch (Exception e) {
            throw new RestException(e.toString());
        }
    }


    /**
     * Uses the Generated client to send multiple REST requests to user-service for the 3 demo-users
     *
     * @return serverUserCollection server-style user object (org.openapitools.model.UserCollection)
     */
    public UserCollection getDemoUsers() {

        // create empty UserCollection
        UserCollection demoUsers = new UserCollection();

        // create instance of the API
        UserApi apiInstance = new UserApi(this.defaultClient);

        try {

            org.openapitools.client.model.UserCollection userCollectionClient;

            // request user Object from user-service
            userCollectionClient = apiInstance.getApiV2UsersDemo();

            // Convert to server users and add to list
            for (org.openapitools.client.model.User userClient : userCollectionClient.getUsers()) {
                
                demoUsers.addUsersItem(
                    convertClientUserToServerUser(userClient)
                );

            }

            // convert to server-type object and return
            return demoUsers;

        } catch (Exception e) {
            throw new RestException(e.toString());
        }

    }

    /**
     * Uses the Generated client to send multiple REST requests to user-service for multiple User objects
     * 
     *
     * @param userIDs userID of the user being fetched
     * @return serverUserCollection server-style user object (org.openapitools.model.UserCollection)
     */
    public UserCollection getUsers(List<UUID> userIDs) {

        // create empty UserCollection
        UserCollection users = new UserCollection();

        // get each user individually
        for (UUID userID : userIDs) {
            users.addUsersItem(getUser(userID));
        }

        // return userCollection
        return users;

    }

}
