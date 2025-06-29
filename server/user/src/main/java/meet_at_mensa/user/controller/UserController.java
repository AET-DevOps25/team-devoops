package meet_at_mensa.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import meet_at_mensa.user.exception.UserNotFoundException;
import meet_at_mensa.user.service.UserService;

import org.openapitools.api.UserApi;
import org.openapitools.model.User;
import org.openapitools.model.UserNew;
import org.openapitools.model.UserUpdate;

import java.util.UUID;



@RestController
public class UserController implements UserApi {

    // UserService handles database operations
    @Autowired
    private UserService userService;


    // DELETE @ api/v2/user/{userID}
    @Override
    public ResponseEntity<Void> deleteApiV2UserUserID(UUID userID) {

        // 200
        try {
            
            // attempt to delete user with given ID
            userService.deleteUser(userID);

            // if delete operation succeeded, return 200
            return ResponseEntity.ok().build();

        // 404
        } catch (UserNotFoundException e) {

            // if user was not found, return 404
            return ResponseEntity.notFound().build();
        
        // 500
        } catch (Exception e) {

            // if some other exception occurs
            return ResponseEntity.internalServerError().build();

        }

    }


    // GET @ api/v2/user/{userID}
    @Override
    public ResponseEntity<User> getApiV2UserUserID(UUID userID) {

        // 200
        try {
        
            // attempt to get user with the given ID
            User user =  userService.getUser(userID);

            // return 200 with User
            return ResponseEntity.ok(user);
        
        // 404
        } catch (UserNotFoundException e) {
            
            // if user was not found, return 404
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // if some other exception occurs
            return ResponseEntity.internalServerError().build();

        }

    }


    // POST @ api/v2/user/register
    @Override
    public ResponseEntity<User> postApiV2UserRegister(UserNew userNew) {

        // 200
        try {

            // attempt to register a new user
            User newUser = userService.registerUser(userNew);

            return ResponseEntity.status(201).body(newUser);

        // 500
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        
        }
    }

    // PUT @ api/v2/user/{userID}
    @Override
    public ResponseEntity<User> putApiV2UserUserID(UUID userID, UserUpdate userUpdate) {

        // 200
        try {
            
            // attempt to update User
            User updatedUser = userService.updateUser(userID, userUpdate);

            // return the updated user
            return ResponseEntity.ok(updatedUser);

        // 404
        } catch (UserNotFoundException e) {
            
            // if user was not found, return 404
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // if some other exception occurs
            return ResponseEntity.internalServerError().build();

        }

    }

}