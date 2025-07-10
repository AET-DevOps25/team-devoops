package meet_at_mensa.matching.algorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.Location;
import org.openapitools.model.MatchCollection;
import org.openapitools.model.MatchPreferences;
import org.openapitools.model.Match;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



import static org.junit.jupiter.api.Assertions.*;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.exception.MatchNotFoundException;
import meet_at_mensa.matching.util.Asserter;
import meet_at_mensa.matching.util.Generator;

@SpringBootTest
@Testcontainers
class MatchingAlgorithmTests {

    @Container
    static MySQLContainer<?> matchdb = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("matchdb")
        .withInitScript("init_matchdb_test.sql")
        .withUsername("root")
        .withPassword("root");


    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", matchdb::getJdbcUrl);
        registry.add("spring.datasource.username", matchdb::getUsername);
        registry.add("spring.datasource.password", matchdb::getPassword);
    }

    // generate random values for testing
    private Generator generator = new Generator();


    @Test
    void canClusterMatchKnown() {

        // ----
        // DATA
        // ----

        // Start empty lists
        List<User> users = new ArrayList<>();
        List<MatchRequest> requests = new ArrayList<>();

        // User 1

        User user1 = new User()
            .userID(UUID.randomUUID())
            .email("alice.anderson@example.com")
            .firstname("Alice")
            .lastname("Anderson")
            .birthday(LocalDate.of(1998, 3, 14))
            .gender("female")
            .degree("msc_informatics")
            .degreeStart(2022)
            .interests(List.of("gaming", "reading"))
            .bio("I love exploring how data shapes our world.");

        MatchRequest request1 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user1.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(1, 2, 3, 4, 5, 6, 7, 8, 13, 14, 15, 16)) // 10:00 - 12:00 and 13:00 - 14:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(true, false, true))
            .status(RequestStatus.PENDING);

        User user2 = new User()
            .userID(UUID.randomUUID())
            .email("ben.brown@example.com")
            .firstname("Ben")
            .lastname("Brown")
            .birthday(LocalDate.of(1996, 7, 22))
            .gender("male")
            .degree("bsc_mechanical_engineering")
            .degreeStart(2021)
            .interests(List.of("cycling", "robotics"))
            .bio("Mechanical design is my passion, both digital and hands-on.");

        MatchRequest request2 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user2.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(9, 10, 11, 12, 13, 14, 15, 16)) // 12:00 - 14:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(false, true, true))
            .status(RequestStatus.PENDING);

        User user3 = new User()
            .userID(UUID.randomUUID())
            .email("carla.carter@example.com")
            .firstname("Carla")
            .lastname("Carter")
            .birthday(LocalDate.of(2002, 12, 4))
            .gender("female")
            .degree("msc_mechanical_engineering")
            .degreeStart(2023)
            .interests(List.of("painting", "hiking"))
            .bio("Enjoys finding creative solutions for technical challenges.");

        MatchRequest request3 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user3.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(2, 3, 4, 5, 6, 7, 8, 9, 10)) // from 11:15 - 13:30
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(true, false, false))
            .status(RequestStatus.PENDING);

        User user4 = new User()
            .userID(UUID.randomUUID())
            .email("daniel.davis@example.com")
            .firstname("Daniel")
            .lastname("Davis")
            .birthday(LocalDate.of(1995, 5, 19))
            .gender("male")
            .degree("bsc_informatics")
            .degreeStart(2020)
            .interests(List.of("photography", "machine learning"))
            .bio("Always curious about the intersection of art and technology.");

        MatchRequest request4 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user4.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(9, 10, 11, 12, 13, 14, 15, 16)) // 12:00 - 14:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(true, true, false))
            .status(RequestStatus.PENDING);

        User user5 = new User()
            .userID(UUID.randomUUID())
            .email("emma.evans@example.com")
            .firstname("Emma")
            .lastname("Evans")
            .birthday(LocalDate.of(2001, 8, 27))
            .gender("other")
            .degree("msc_informatics")
            .degreeStart(2022)
            .interests(List.of("yoga", "coding", "travel"))
            .bio("Avid traveler who loves learning about cultures and code.");

        MatchRequest request5 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user5.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)) // 11:00 - 14:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(false, false, false))
            .status(RequestStatus.PENDING);

        User user6 = new User()
            .userID(UUID.randomUUID())
            .email("felix.foster@example.com")
            .firstname("Felix")
            .lastname("Foster")
            .birthday(LocalDate.of(1998, 1, 30))
            .gender("male")
            .degree("bsc_mechanical_engineering")
            .degreeStart(2021)
            .interests(List.of("basketball", "motorsports"))
            .bio("If it's fast and mechanical, I'm interested!");

        MatchRequest request6 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user6.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)) // 11:00 - 14:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(true, true, true))
            .status(RequestStatus.PENDING);

        User user7 = new User()
            .userID(UUID.randomUUID())
            .email("grace.green@example.com")
            .firstname("Grace")
            .lastname("Green")
            .birthday(LocalDate.of(2000, 6, 10))
            .gender("female")
            .degree("msc_mechanical_engineering")
            .degreeStart(2023)
            .interests(List.of("dancing", "writing"))
            .bio("Storyteller at heart who loves engineering the future.");

        MatchRequest request7 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user7.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(5, 6, 7, 8)) // 11:00 - 12:00
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(false, true, false))
            .status(RequestStatus.PENDING);

        User user8 = new User()
            .userID(UUID.randomUUID())
            .email("henry.hill@example.com")
            .firstname("Henry")
            .lastname("Hill")
            .birthday(LocalDate.of(1999, 9, 18))
            .gender("male")
            .degree("bsc_informatics")
            .degreeStart(2021)
            .interests(List.of("gaming", "puzzles"))
            .bio("Loves solving problems both big and small.");

        MatchRequest request8 = new MatchRequest()
            .requestID(UUID.randomUUID())
            .userID(user8.getUserID())
            .date(LocalDate.now().plusDays(1))
            .timeslot(List.of(5, 6, 7, 8, 9, 10)) // 11:00 - 12:30
            .location(Location.GARCHING)
            .preferences(new MatchPreferences(true, false, true))
            .status(RequestStatus.PENDING);


        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);

        requests.add(request1);
        requests.add(request2);
        requests.add(request3);
        requests.add(request4);
        requests.add(request5);
        requests.add(request6);
        requests.add(request7);
        requests.add(request8);

        

        // Convert to collections
        UserCollection userCollection = new UserCollection(users);
        MatchRequestCollection requestCollection = new MatchRequestCollection(requests);

        // ----
        // ACT
        // ----

        // Init matching algorithm
        MatchingAlgorithm algorithm = new ClusteringAlgorithm(userCollection, requestCollection);
        
        // generate solution
        MatchingSolution solution = algorithm.generateSolution();
        
        // ------
        // ASSERT
        // ------


        Asserter.assertSolutionIsValid(solution);

        
    }


    @Test
    void canClusterMatchRandom() {

        // repeat 20 times
        for (int j = 0; j < 20; j++) {
            
            Generator localGenerator = new Generator();
    
            // Start empty lists
            List<User> users = new ArrayList<>();
            List<MatchRequest> requests = new ArrayList<>();
    
            // generate 20 random users and requests
            for (int i = 0; i < 20; i++) {
    
                User user = localGenerator.generateUser();
                MatchRequest request = localGenerator.generateMatchRequest(user);
                
                users.add(user);
                requests.add(request);
    
            }
    
            // Convert to collections
            UserCollection userCollection = new UserCollection(users);
            MatchRequestCollection requestCollection = new MatchRequestCollection(requests);
    
            // ----
            // ACT
            // ----
    
            // Init matching algorithm
            MatchingAlgorithm algorithm = new ClusteringAlgorithm(userCollection, requestCollection);
            
            // generate solution
            MatchingSolution solution = algorithm.generateSolution();
            
            // ------
            // ASSERT
            // ------
    
    
            Asserter.assertSolutionIsValid(solution);

        }


    }

}