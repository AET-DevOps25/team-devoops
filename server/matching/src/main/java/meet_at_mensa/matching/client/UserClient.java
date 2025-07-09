package meet_at_mensa.matching.client;

import java.util.UUID;
import java.util.List;

import org.openapitools.client.ApiClient;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.api.UserApi;
import org.openapitools.client.model.User;

import org.openapitools.model.UserCollection;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.RestException;

@Service
public class UserClient {

    private ApiClient defaultClient;

    public UserClient() {

        // get default client
        this.defaultClient = Configuration.getDefaultApiClient();

        // set path
        this.defaultClient.setBasePath("meetatmensa-user");
    }

    private org.openapitools.model.User convertUser(User clientUser) {

        return new org.openapitools.model.User(
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

    

    public org.openapitools.model.User getUser(UUID userID) {

        // create instance of the API
        UserApi apiInstance = new UserApi(this.defaultClient);

        try {

            // request user Object from user-service
            User userClient = apiInstance.getApiV2UserUserID(userID);

            // convert to server-type object and return
            return convertUser(userClient);

        } catch (Exception e) {
            throw new RestException();
        }
    }

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
