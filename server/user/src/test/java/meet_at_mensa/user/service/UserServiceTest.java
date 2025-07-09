package meet_at_mensa.user.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.openapitools.model.User;
import org.openapitools.model.UserNew;
import org.openapitools.model.UserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import meet_at_mensa.user.exception.UserConflictException;
import meet_at_mensa.user.exception.UserMalformedException;
import meet_at_mensa.user.exception.UserNotFoundException;
import meet_at_mensa.user.model.IdentityEntity;
import meet_at_mensa.user.repository.IdentityRepository;

@SpringBootTest
@Testcontainers
public class UserServiceTest {
    
    @Container
    static MySQLContainer<?> userdb = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("userdb")
        .withInitScript("init_userdb_test.sql")
        .withUsername("root")
        .withPassword("root");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", userdb::getJdbcUrl);
        registry.add("spring.datasource.username", userdb::getUsername);
        registry.add("spring.datasource.password", userdb::getPassword);
    }

    @Autowired
    UserService userService;

    @Autowired
    IdentityRepository identityRepository;

    
    

    @Test
    void canRegisterUser() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max.registermann@example.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userNew = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        // ----
        // ACT
        // ----

        // register user
        User user = userService.registerUser(userNew);

        // ------
        // ASSERT
        // ------

        // assert not null
        assertNotNull(user, "return value should not be null");
        assertNotNull(user.getUserID(), "user must have an ID");

        // assert expected
        assertEquals(template.getEmail(), user.getEmail(), "Email should match!");
        assertEquals(template.getFirstname(), user.getFirstname(), "firstname should match!");
        assertEquals(template.getLastname(), user.getLastname(), "lastname should match!");
        assertEquals(template.getBirthday(), user.getBirthday(), "birthday should match!");
        assertEquals(template.getGender(), user.getGender(), "gender should match!");
        assertEquals(template.getDegree(), user.getDegree(), "degree should match!");
        assertEquals(template.getDegreeStart(), user.getDegreeStart(), "degree start should match!");
        assertEquals(new HashSet<>(template.getInterests()), new HashSet<>(user.getInterests()), "interests should match!");
        assertEquals(template.getBio(), user.getBio(), "bio should match!");

    }

    @Test
    void canGetUser() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max.getmann@example.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userNew = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        // register user and save it's ID
        UUID userID = userService.registerUser(userNew).getUserID();

        // ----
        // ACT
        // ----

        // attempt to get user
        User user = userService.getUser(userID);

        // ------
        // ASSERT
        // ------

        // assert not null
        assertNotNull(user, "return value should not be null");
        assertNotNull(user.getUserID(), "user must have an ID");

        // assert expected
        assertEquals(template.getEmail(), user.getEmail(), "Email should match!");
        assertEquals(template.getFirstname(), user.getFirstname(), "firstname should match!");
        assertEquals(template.getLastname(), user.getLastname(), "lastname should match!");
        assertEquals(template.getBirthday(), user.getBirthday(), "birthday should match!");
        assertEquals(template.getGender(), user.getGender(), "gender should match!");
        assertEquals(template.getDegree(), user.getDegree(), "degree should match!");
        assertEquals(template.getDegreeStart(), user.getDegreeStart(), "degree start should match!");
        assertEquals(new HashSet<>(template.getInterests()), new HashSet<>(user.getInterests()), "interests should match!");
        assertEquals(template.getBio(), user.getBio(), "bio should match!");

    }


    @Test
    void canUpdateUser() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max.updatemann@example.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userNew = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        UserUpdate updateTemplate = new UserUpdate()
            .email("maxine.musterfrau@example.com")
            .firstname("Maxine")
            .lastname("Musterfrau")
            .birthday(LocalDate.of(1942, 4, 2))
            .gender("female")
            .degree("msc_political_science")
            .degreeStart(2025)
            .interests(List.of("cats", "boardgames"))
            .bio("I am a Triceratops");

        // register user and save it's ID
        UUID userID = userService.registerUser(userNew).getUserID();

        // ----
        // ACT
        // ----

        // attempt to edit a user
        User user = userService.updateUser(userID, updateTemplate);

        // ------
        // ASSERT
        // ------

        // assert not null
        assertNotNull(user, "return value should not be null");
        assertNotNull(user.getUserID(), "user must have an ID");

        // assert expected
        assertEquals(userID, user.getUserID(), "User IDs should not change!"); // userID doesn't change
        assertEquals(updateTemplate.getEmail(), user.getEmail(), "Email should match!");
        assertEquals(updateTemplate.getFirstname(), user.getFirstname(), "firstname should match!");
        assertEquals(updateTemplate.getLastname(), user.getLastname(), "lastname should match!");
        assertEquals(updateTemplate.getBirthday(), user.getBirthday(), "birthday should match!");
        assertEquals(updateTemplate.getGender(), user.getGender(), "gender should match!");
        assertEquals(updateTemplate.getDegree(), user.getDegree(), "degree should match!");
        assertEquals(updateTemplate.getDegreeStart(), user.getDegreeStart(), "degree start should match!");
        assertEquals(new HashSet<>(updateTemplate.getInterests()), new HashSet<>(user.getInterests()), "interests should match!");
        assertEquals(updateTemplate.getBio(), user.getBio(), "bio should match!");

    }


    @Test
    void canRemoveUser() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max.updatemann@example.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userNew = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        // register user and save it's ID
        UUID userID = userService.registerUser(userNew).getUserID();

        // ----
        // ACT
        // ----

        // attempt to edit a user
        userService.deleteUser(userID);

        // ------
        // ASSERT
        // ------

        // assert that the user cant be found and an exception is raised
        assertThrows(
            UserNotFoundException.class,
            () -> userService.getUser(userID)
        );

    }

    @Test
    void rejectsInvalidEmail() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("bademail.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userBadEmail = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());


        // ------
        // ASSERT
        // ------

        assertThrows(
            UserMalformedException.class,
            () -> userService.registerUser(userBadEmail)
        );

    }

    @Test
    void rejectsDuplicateEmail() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max@duplicatemann.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew user1 = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        UserNew user2 = new UserNew()
            .authID("placeholder" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        // -------
        // Act
        // -------

        userService.registerUser(user1);

        // ------
        // ASSERT
        // ------

        assertThrows(
            UserConflictException.class,
            () -> userService.registerUser(user2)
        );

    }


    @Test
    void canGetUserByAuthID() {

        // -------
        // PREPARE
        // -------

        User template = new User()
            .email("max@authman.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        UserNew userNew = new UserNew()
            .authID("placeholderAuth0_" + UUID.randomUUID().toString())
            .email(template.getEmail())
            .firstname(template.getFirstname())
            .lastname(template.getLastname())
            .birthday(template.getBirthday())
            .gender(template.getGender())
            .degree(template.getDegree())
            .degreeStart(template.getDegreeStart())
            .interests(template.getInterests())
            .bio(template.getBio());

        // -------
        // Act
        // -------

        userService.registerUser(userNew);

        User user = userService.getUserByAuthID(userNew.getAuthID());

        IdentityEntity identityEntity = identityRepository.findByAuthID(userNew.getAuthID());

        // ------
        // ASSERT
        // ------

        // assert not null
        assertNotNull(user, "return value should not be null");
        assertNotNull(user.getUserID(), "user must have an ID");

        // assert IdentityEntity works
        assertEquals(userNew.getAuthID(), identityEntity.getAuthID(), "AuthIDs should match");
        assertEquals(user.getUserID(), identityEntity.getUserID(), "UserIDs should match");

        // assert returned user object is correct
        assertEquals(template.getEmail(), user.getEmail(), "Email should match!");
        assertEquals(template.getFirstname(), user.getFirstname(), "firstname should match!");
        assertEquals(template.getLastname(), user.getLastname(), "lastname should match!");
        assertEquals(template.getBirthday(), user.getBirthday(), "birthday should match!");
        assertEquals(template.getGender(), user.getGender(), "gender should match!");
        assertEquals(template.getDegree(), user.getDegree(), "degree should match!");
        assertEquals(template.getDegreeStart(), user.getDegreeStart(), "degree start should match!");

    }

}
