package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.Location;
import org.openapitools.model.MatchStatus;
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

import meet_at_mensa.matching.model.GroupEntity;
import meet_at_mensa.matching.repository.GroupRepository;
import meet_at_mensa.matching.repository.MatchRepository;
import meet_at_mensa.matching.util.Asserter;

@SpringBootTest
@Testcontainers
class MatchingServiceTests {


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

    @Autowired
    MatchingService matchingService;

    @Test
    void canCreateGroup() {

        // ----
        // DATA
        // ----

        // template values for users
        User user1 = new User()
            .userID(UUID.randomUUID())
            .email("max.onemann@example.com")
            .firstname("Max")
            .lastname("Mustermann")
            .birthday(LocalDate.of(1969, 6, 9))
            .gender("male")
            .degree("msc_informatics")
            .degreeStart(2024)
            .interests(List.of("dnd", "gaming"))
            .bio("I am a Stegosaurus");

        User user2 = new User()
            .userID(UUID.randomUUID())
            .email("maxine.twomann@example.com")
            .firstname("Maxine")
            .lastname("Twomann")
            .birthday(LocalDate.of(1942, 4, 2))
            .gender("female")
            .degree("msc_chemical_engineering")
            .degreeStart(2025)
            .interests(List.of("cats", "dogs"))
            .bio("I am a Deinonychus");

        User user3 = new User()
            .userID(UUID.randomUUID())
            .email("hans.threemann@example.com")
            .firstname("Hans")
            .lastname("Threemann")
            .birthday(LocalDate.of(1984, 8, 4))
            .gender("other")
            .degree("bsc_informatics")
            .degreeStart(2022)
            .interests(List.of("math", "cooking"))
            .bio("I am a Triceratops");

        UserCollection users = new UserCollection(List.of(user1,user2,user3));

        // templates for date time and location
        LocalDate date = LocalDate.now();
        Integer timeslot = 9;
        Location location = Location.GARCHING;

        // ----
        // ACT
        // ----

        // attempt to create the group
        Group group = matchingService.createGroup(
            users,
            date,
            timeslot,
            location
        );

        // ------
        // ASSERT
        // ------

        // ensure that group and groupID have generated
        assertNotNull(group, "Group should not be Null!");
        assertNotNull(group.getGroupID(), "ID should not be null");
        
        // compare basic values
        assertEquals(date, group.getDate(), "Dates should match!");
        assertEquals(timeslot, group.getTime(), "Timeslots should match!");
        assertEquals(location, group.getLocation(), "Locations should Match!");
        
        // Assert ConversationStarters match
        Asserter.assertConversationStarterCollectionGeneratedProperly(group.getConversationStarters());

        // Assert UserStatuses generated properly
        Asserter.assertMatchStatusCreatedSuccessfully(
            // List<UserIDs>
            users.getUsers().stream()
                .map(User::getUserID)
                .collect(Collectors.toList()),
            // List<MatchStatus>
            group.getUserStatus()
        );



    }

}

