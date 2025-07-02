package meet_at_mensa.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import org.openapitools.model.User;
import org.openapitools.model.UserNew;
import org.openapitools.model.UserUpdate;

import meet_at_mensa.user.repository.*;
import meet_at_mensa.user.exception.UserConflictException;
import meet_at_mensa.user.exception.UserMalformedException;
import meet_at_mensa.user.exception.UserNotFoundException;
import meet_at_mensa.user.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    // UserRepository manages the userdb database
    @Autowired
    private UserRepository userRepository;

    // IntererstRepository manages the interests database
    @Autowired
    InterestRepository interestRepository;


    /**
     * Searches the database for a user with id {userID}
     *
     * Generates a User object from the database entity abstractions
     *
     * @param userID UUID of the user being searched for
     * @return User object if a user was found
     * @throws UserNotFoundException if no user with id {userID} is found
     */
    public User getUser(UUID userID) {

        // search the database for a user
        // throws a UserNotFoundException if no user is found
        UserEntity userEntity = userRepository.findById(userID)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", userID)));

        // seach the database for a user's interests
        List<String> interests = getInterests(userID);

        // construct new User object
        User user = new User(
            userEntity.getUserID(), // id
            userEntity.getEmail(), // email
            userEntity.getFirstname(), // firstname
            userEntity.getLastname(), // lastname
            userEntity.getBirthday(), // birthday
            userEntity.getGender(), // gender
            userEntity.getDegree(), // degree
            userEntity.getDegreeStart(), // degreeStart
            interests, // interests
            userEntity.getBio() // bio
         );

         // return
         return user;
    }

    /**
     * Creates database entries for a new user based on a UserNew object
     *
     * Generates UUIDs
     *
     * @param userNew UserNew object containing information about the new user
     * @return User object of the user that has been created
     */
    public User registerUser(UserNew userNew) {

        // validate email
        if(!isValidEmail(userNew.getEmail())) {
            throw new UserMalformedException();
        }

        // construct new UserEntity object
        UserEntity userEntity = new UserEntity(
            userNew.getEmail(), // email
            userNew.getFirstname(), // firstname
            userNew.getLastname(), // lastname
            userNew.getBirthday(), // birthday
            userNew.getGender(), // gender
            userNew.getDegree(), // degree
            userNew.getDegreeStart(), // degreeStart
            userNew.getBio() // bio
        );

        try {

            // save user entity to database
            userEntity = userRepository.save(userEntity);

        // throw Exception if duplicate email
        } catch (DataIntegrityViolationException e) {
            throw new UserConflictException(e.toString());
        }

        

        // register interests
        registerInterests(userEntity.getUserID(), userNew.getInterests());

        // fetch User object and return
        return getUser(userEntity.getUserID());
    }

    /**
     * Updates the database entries for a user with id {UserID} based on a UserUpdate object.
     *
     * Keeps old value if corresponding value in UserUpdate is null.
     * Overwrites previously existing values otherwise.
     *
     * @param userID UUID of the user being updated
     * @return User object of the user that has been updated
     * @throws UserNotFoundException if no user with id {userID} is found
     */
    public User updateUser(UUID userID, UserUpdate userUpdate) {

        // search the database for a user
        // throws a UserNotFoundException if no user is found
        UserEntity userEntity = userRepository.findById(userID)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", userID))); 
        
        // update Email
        if (userUpdate.getEmail() != null){
            userEntity.setEmail(userUpdate.getEmail());
        }

        // update Firstname
        if (userUpdate.getFirstname() != null){
            userEntity.setFirstname(userUpdate.getFirstname());
        }

        // update Lastname
        if (userUpdate.getLastname() != null){
            userEntity.setLastname(userUpdate.getLastname());
        }

        // update Birthday
        if (userUpdate.getBirthday() != null){
            userEntity.setBirthday(userUpdate.getBirthday());
        }

        // update Gender
        if (userUpdate.getGender() != null){
            userEntity.setGender(userUpdate.getGender());
        }

        // update Degree
        if (userUpdate.getDegree() != null){
            userEntity.setDegree(userUpdate.getDegree());
        }

        // update DegreeStart
        if (userUpdate.getDegreeStart() != null){
            userEntity.setDegreeStart(userUpdate.getDegreeStart());
        }

        // update Bio
        if (userUpdate.getBio() != null){
            userEntity.setBio(userUpdate.getBio());
        }

        // save changes to the database
        userRepository.save(userEntity);

        // update Interests
        if (userUpdate.getBio() != null){
            
            // remove old interests
            deleteInterests(userID);

            // add new interest list
            registerInterests(userID, userUpdate.getInterests());

        }

        // return User object fetched new from the database
        return getUser(userID);

    }

    /**
     * Removes all database entries for a user with id {UserID}.
     *
     *
     * @param userID UUID of the user being deleted
     * @throws UserNotFoundException if no user with id {userID} is found
     */
    public void deleteUser(UUID userID) {

        // Check that user exists
        // throws a UserNotFoundException if no user is found
        userRepository.findById(userID)
            .orElseThrow(() -> new UserNotFoundException(String.format("User with ID %s not found", userID)));
        
        // delete userEntity from database
        userRepository.deleteById(userID);

        // delete corresponding interests
        deleteInterests(userID);
    }

    /**
     * Searches the interests database for all interests corresponding to user with id {userID}
     *
     * Generates a list of Strings to return
     *
     * @param userID UUID of the user who's interests are being searched for
     * @return List of interests corresponding to that user
     */
    private List<String> getInterests(UUID userID) {

        // search the database for interestEntity entries
        Iterable<InterestEntity> interestEntities = interestRepository.findByUserID(userID);

        // create an empty list to store interests
        List<String> interests = new ArrayList<>();
        
        // extract string values into new list
        for (InterestEntity interestEntity : interestEntities) {
            interests.add(interestEntity.getInterest());
        }

        // return
        return interests;
    }

    /**
     * Deletes all entries from interests database for user with id {userID}
     *
     * @param userID UUID of the user who's interests are being deleted
     */
    private void deleteInterests(UUID userID) {

        // search the database for interestEntity entries
        Iterable<InterestEntity> interestEntities = interestRepository.findByUserID(userID);

        // delete all entries from list
        for (InterestEntity interestEntity : interestEntities) {
            interestRepository.deleteById(interestEntity.getListID());
        }

    }

    /**
     * Creates database entries for interests corresponding to a user with id {userID}
     *
     * Deletes all previous entries for that user and generates new ones
     *
     * @param userID UUID of the user being searched for
     * @param interests List<String> String values of the user's interests
     * @return The list of interests corresponding to the user after registering
     */
    private List<String> registerInterests(UUID userID, List<String> interests) {

        // for each interest
        for (String interest : interests) {

            // create an entity object and add it to the database
            interestRepository.save(new InterestEntity(userID, interest));
        }

        // return the interests for this userID
        return getInterests(userID);

    }

    /**
     * DEBUG METHOD ONLY. DO NOT USE IN PRODUCTION
     * 
     * Generate a list of all UserEntity objects managed by the databases
     *
     * @return List of all UserEntity objects in the database
     */
    public Iterable<UserEntity> debugGetAllUserEntities() {
        return userRepository.findAll();
    }

    /**
     * DEBUG METHOD ONLY. DO NOT USE IN PRODUCTION
     * 
     * Generate a list of all InterestEntity objects managed by the databases
     *
     * @return List of all InterestEntity objects in the database
     */
    public Iterable<InterestEntity> debugGetAllInterestEntities() {
        return interestRepository.findAll();
    }

    /**
     * DEBUG METHOD ONLY. DO NOT USE IN PRODUCTION
     * 
     * Generate a list of all User Objects managed by the database
     *
     * @return List of all User objects in the database
     */
    public Iterable<User> debugGetAllUsers() {
        
        // get list of userentities
        Iterable<UserEntity> allUserEntities = userRepository.findAll();

        List<User> users = new ArrayList<>();

        // generate User objects for each
        for (UserEntity userEntity : allUserEntities) {
            users.add(
                getUser(userEntity.getUserID())
            );
        }

        return users;

    }

    /**
     * Checks if an email is valid
     *
     * @param email to be checked
     * @return true if valid, false if not
     */
    public Boolean isValidEmail(String email){

        // Regex taken from https://www.baeldung.com/java-email-validation-regex
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return email.matches(emailRegex);
    }

}
