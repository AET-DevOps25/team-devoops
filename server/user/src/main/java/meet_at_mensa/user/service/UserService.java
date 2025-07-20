package meet_at_mensa.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.openapitools.model.UserNew;
import org.openapitools.model.UserUpdate;

import meet_at_mensa.user.repository.*;
import meet_at_mensa.user.exception.UserConflictException;
import meet_at_mensa.user.exception.UserMalformedException;
import meet_at_mensa.user.exception.UserNotFoundException;
import meet_at_mensa.user.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Add Micrometer imports for Prometheus metrics
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // UserRepository manages the userdb database
    @Autowired
    private UserRepository userRepository;

    // IntererstRepository manages the interests database
    @Autowired
    InterestRepository interestRepository;

    // IdentityRepository manages Auth0 identity pairs
    @Autowired
    IdentityRepository identityRepository;

    // Prometheus metrics
    private final Counter usersCreatedCounter;

    public UserService(MeterRegistry meterRegistry) {
        // Initialize custom metrics
        this.usersCreatedCounter = Counter.builder("users_created_total")
                .description("Total number of users created")
                .register(meterRegistry);
    }

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
        logger.debug("Attempting to retrieve user with ID: {}", userID);
        UserEntity userEntity = userRepository.findById(userID)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found", userID);
                    return new UserNotFoundException(String.format("User with ID %s not found", userID));
                });
        // seach the database for a user's interests
        logger.info("User with ID {} retrieved successfully", userID);
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
     * Searches the database for a user with id {userID}
     *
     * Generates a User object from the database entity abstractions
     *
     * @param userID UUID of the user being searched for
     * @return User object if a user was found
     * @throws UserNotFoundException if no user with id {userID} is found
     */
    public User getUserByAuthID(String authID) {
        // search the database for a user
        // throws a UserNotFoundException if no user is found
        logger.debug("Attempting to retrieve user by AuthID: {}", authID);
        IdentityEntity identityEntity = identityRepository.findByAuthID(authID);
        // .orElseThrow(() -> new UserNotFoundException(String.format("User with AuthID
        // %s not found", authID)));
        if (identityEntity == null) {
            logger.warn("User with AuthID {} not found", authID);
            throw new UserNotFoundException(String.format("User with AuthID %s not found", authID));
        }
        // return
        logger.info("User with AuthID {} found, retrieving user details", authID);
        return getUser(identityEntity.getUserID());
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
        logger.info("Registering new user with email: {}", userNew.getEmail());
        if (!isValidEmail(userNew.getEmail())) {
            logger.warn("Invalid email provided: {}", userNew.getEmail());
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
            // construct new Identity entity
            IdentityEntity identityEntity = new IdentityEntity(
                    userEntity.getUserID(),
                    userNew.getAuthID());
            // save identity entity to database
            identityEntity = identityRepository.save(identityEntity);
            logger.info("User created successfully: {}", userEntity.getUserID());
            // throw Exception if duplicate email
            usersCreatedCounter.increment(); // Increment the counter for user creation
        } catch (DataIntegrityViolationException e) {
            logger.error("User registration failed due to duplicate email: {}", userNew.getEmail());
            throw new UserConflictException(e.toString());
        }
        // register interests
        registerInterests(userEntity.getUserID(), userNew.getInterests());
        logger.debug("Registered interests for user: {}", userEntity.getUserID());
        // fetch User object and return
        return getUser(userEntity.getUserID());
    }

    /**
     * Updates the database entries for a user with id {UserID} based on a
     * UserUpdate object.
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
        logger.info("Updating user with ID: {}", userID);
        UserEntity userEntity = userRepository.findById(userID)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found for update", userID);
                    return new UserNotFoundException(String.format("User with ID %s not found", userID));
                });
        // update Email
        if (userUpdate.getEmail() != null) {
            userEntity.setEmail(userUpdate.getEmail());
        }
        // update Firstname
        if (userUpdate.getFirstname() != null) {
            userEntity.setFirstname(userUpdate.getFirstname());
        }
        // update Lastname
        if (userUpdate.getLastname() != null) {
            userEntity.setLastname(userUpdate.getLastname());
        }
        // update Birthday
        if (userUpdate.getBirthday() != null) {
            userEntity.setBirthday(userUpdate.getBirthday());
        }
        // update Gender
        if (userUpdate.getGender() != null) {
            userEntity.setGender(userUpdate.getGender());
        }
        // update Degree
        if (userUpdate.getDegree() != null) {
            userEntity.setDegree(userUpdate.getDegree());
        }
        // update DegreeStart
        if (userUpdate.getDegreeStart() != null) {
            userEntity.setDegreeStart(userUpdate.getDegreeStart());
        }
        // update Bio
        if (userUpdate.getBio() != null) {
            userEntity.setBio(userUpdate.getBio());
        }
        // save changes to the database
        userRepository.save(userEntity);
        logger.info("User with ID {} updated successfully", userID);
        // update Interests
        if (userUpdate.getBio() != null) {
            // remove old interests
            logger.debug("Updating interests for user: {}", userID);
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
        logger.info("Deleting user with ID: {}", userID);
        userRepository.findById(userID)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found for deletion", userID);
                    return new UserNotFoundException(String.format("User with ID %s not found", userID));
                });
        // delete userEntity from database
        userRepository.deleteById(userID);
        logger.info("User with ID {} deleted from userRepository", userID);
        // delete corresponding interests
        logger.debug("Deleting interests for user: {}", userID);
        deleteInterests(userID);
        logger.debug("Deleted interests for user: {}", userID);
    }

    /**
     * Searches the interests database for all interests corresponding to user with
     * id {userID}
     *
     * Generates a list of Strings to return
     *
     * @param userID UUID of the user who's interests are being searched for
     * @return List of interests corresponding to that user
     */
    private List<String> getInterests(UUID userID) {
        // search the database for interestEntity entries
        logger.debug("Fetching interests for user: {}", userID);
        Iterable<InterestEntity> interestEntities = interestRepository.findByUserID(userID);
        // create an empty list to store interests
        List<String> interests = new ArrayList<>();
        // extract string values into new list
        for (InterestEntity interestEntity : interestEntities) {
            interests.add(interestEntity.getInterest());
        }
        logger.debug("Found {} interests for user: {}", interests.size(), userID);
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
        logger.debug("Deleting interests for user: {}", userID);
        Iterable<InterestEntity> interestEntities = interestRepository.findByUserID(userID);
        // delete all entries from list
        for (InterestEntity interestEntity : interestEntities) {
            interestRepository.deleteById(interestEntity.getListID());
        }
        logger.debug("All interests deleted for user: {}", userID);
    }

    /**
     * Creates database entries for interests corresponding to a user with id
     * {userID}
     *
     * Deletes all previous entries for that user and generates new ones
     *
     * @param userID    UUID of the user being searched for
     * @param interests List<String> String values of the user's interests
     * @return The list of interests corresponding to the user after registering
     */
    private List<String> registerInterests(UUID userID, List<String> interests) {
        // for each interest
        logger.debug("Registering {} interests for user: {}", interests.size(), userID);
        for (String interest : interests) {
            // create an entity object and add it to the database
            interestRepository.save(new InterestEntity(userID, interest));
        }
        logger.debug("Interests registered for user: {}", userID);
        // return the interests for this userID
        return getInterests(userID);
    }

    /**
     * Creates database entries for 3 "Test Users" used to showcase Meet@Mensa functionality
     *
     * Checks if users already exist before creating them.
     *
     * @return UserCollection of the Demo Users
     */
    public UserCollection createDemoUsers() {

        // create empty return collection
        UserCollection demoUsers = new UserCollection();

        // If users are in database, return them
        try {

            getUserByAuthID("TestUser001");
            getUserByAuthID("TestUser002");
            getUserByAuthID("TestUser003");            
            
        // If users are not it database, create them
        } catch (UserNotFoundException e) {

            registerUser(
                new UserNew(
                    "TestUser001",
                    "max.mustermann@meetatmensa.com",
                    "Max",
                    "Mustermann",
                    LocalDate.of(1999, 9, 6),
                    "male",
                    "msc_informatics",
                    2024,
                    List.of("Cats", "Rock Climbing", "Magic the Gathering"),
                    "Just a student that loves chilling with fluffy kitties. I'm also a bit more into magic the gathering than I'd like to admint, hahaha."
                )
            );

            registerUser(
                new UserNew(
                    "TestUser002",
                    "maria.musterfrau@meetatmensa.com",
                    "Maria",
                    "Musterfrau",
                    LocalDate.of(2000, 4, 20),
                    "female",
                    "msc_informatics",
                    2025,
                    List.of("Dogs", "Boardgames", "Plants"),
                    "Heyyy there! I'm Maria and my favorite thing is taking my dog to the park or meeting friends for a good boardgame."
                )
            );

            registerUser(
                new UserNew(
                    "TestUser003",
                    "daniel.musterdivers@meetatmensa.com",
                    "Daniel",
                    "Musterdivers",
                    LocalDate.of(2001, 8, 27),
                    "other",
                    "bsc_informatics",
                    2022,
                    List.of("Astronomy", "Video Games", "Tabletop RPGs"),
                    "Daniel here, but all my friends call me Dani. I look at pictures of stars during the day, and pretend to be a wizard at night."
                )
            );
            
        }

        // add demo users to return object
        demoUsers.addUsersItem(getUserByAuthID("TestUser001"));
        demoUsers.addUsersItem(getUserByAuthID("TestUser002"));
        demoUsers.addUsersItem(getUserByAuthID("TestUser003"));

        // return demo users
        return demoUsers;

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
                    getUser(userEntity.getUserID()));
        }

        return users;

    }

    /**
     * Checks if an email is valid
     *
     * @param email to be checked
     * @return true if valid, false if not
     */
    public Boolean isValidEmail(String email) {

        // Regex taken from https://www.baeldung.com/java-email-validation-regex
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return email.matches(emailRegex);
    }

    /**
     * Gets the current count of users created (for testing/debugging purposes)
     *
     * @return current count of users created
     */
    public double getUsersCreatedCount() {
        return usersCreatedCounter.count();
    }

}
