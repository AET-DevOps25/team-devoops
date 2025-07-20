package meet_at_mensa.matching.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.openapitools.model.Group;
import org.openapitools.model.InviteStatus;
import org.openapitools.model.Location;
import org.openapitools.model.Match;
import org.openapitools.model.MatchCollection;
import org.openapitools.model.User;
import org.openapitools.model.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import meet_at_mensa.matching.exception.MatchNotFoundException;
import meet_at_mensa.matching.model.MatchEntity;
import meet_at_mensa.matching.repository.MatchRepository;
import meet_at_mensa.matching.util.Asserter;

@SpringBootTest
@Testcontainers
class MatchServiceTests {


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
    MatchService matchService;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    GroupService groupRepository;

    @Autowired
    MatchingService matchingService;


    @Test
    void canRegisterPartialMatch() {

        //-----
        // DATA
        //-----

        UUID userID = UUID.randomUUID();
        UUID groupID = UUID.randomUUID();

        //-----
        // PREP
        //-----

        //-----
        // ACT
        //-----

        // attempt to register a partial match
        UUID matchID = matchService.registerMatch(
            userID,
            groupID
        );

        // retrieve entity from repository
        MatchEntity matchEntity = matchRepository.findById(matchID)
            .orElse(null);

        //------
        // ASSERT
        //------

        // assert that the returned value is not null
        assertNotNull(matchEntity, "Value should not be null!");

        // check basic values
        assertEquals(matchID, matchEntity.getMatchID());
        assertEquals(userID, matchEntity.getUserID());
        assertEquals(groupID, matchEntity.getGroupID());
        assertTrue(
                matchEntity.getInviteStatus() == InviteStatus.UNSENT || matchEntity.getInviteStatus() == InviteStatus.SENT
            );

    }


    @Test
    void canUpdateStatus() {

        //-----
        // DATA
        //-----

        List<InviteStatus> statuses = List.of(InviteStatus.CONFIRMED, InviteStatus.EXPIRED, InviteStatus.REJECTED, InviteStatus.SENT, InviteStatus.UNSENT);

        //-----
        // PREP
        //-----

        // create a group entry including matches
        Group group = registerTestGroup();

        // get first users's userID
        UUID userID = group.getUserStatus().get(0).getUserID();

        // get the matchID
        UUID matchID = matchService.getMatches(userID).getMatches().get(0).getMatchID();

        //-------------
        // ACT / ASSERT
        //-------------

        for (InviteStatus inviteStatus : statuses) {
            
            
            // attempt to update status
            matchService.updateStatus(matchID, inviteStatus);

            // retrieve entity from repository
            MatchEntity matchEntity = matchRepository.findById(matchID)
                .orElse(null);

            // assert that entity is not null
            assertNotNull(matchEntity, "Value should not be null!");

            // check that 
            assertEquals(matchID, matchEntity.getMatchID(), "MatchIDs should match");
            assertEquals(userID, matchEntity.getUserID(), "UserIDs should match");
            assertEquals(inviteStatus, matchEntity.getInviteStatus(), "Statuses Should Match");

        }

    }

    @Test
    void canGetMatch() {

        //-----
        // DATA
        //-----

        //-----
        // PREP
        //-----

        // create a group entry including matches
        Group group = registerTestGroup();

        // get first users's userID
        UUID userID = group.getUserStatus().get(0).getUserID();

        // get the matchID
        UUID matchID = matchService.getMatches(userID).getMatches().get(0).getMatchID();

        //-----
        // ACT
        //-----

        Match match = matchService.getMatch(matchID);

        //--------
        // ASSERT
        //--------

        assertNotNull(match, "Value should not be null");

        assertEquals(matchID, match.getMatchID(), "MatchIDs should match");
        assertEquals(userID, match.getUserID(), "UserIDs should match");
        assertTrue(
                match.getStatus() == InviteStatus.UNSENT || match.getStatus() == InviteStatus.SENT,
                "Match Statuses should be Unsent or Sent"
            );
        

        Asserter.assertGroupsAreIdentical(group, match.getGroup());

    }

    @Test
    void canGetAllMatchsForGroup() {

        //-----
        // DATA
        //-----

        //-----
        // PREP
        //-----

        // create a group entry including matches
        Group group = registerTestGroup();

        //-----
        // ACT
        //-----

        MatchCollection matches = matchService.getMatchesByGroup(group.getGroupID());

        //--------
        // ASSERT
        //--------

        assertNotNull(matches, "Value should not be null");

        assertTrue(matches.getMatches().size() == 3);

        for (Match match : matches.getMatches()) {
            
            assertEquals(match.getGroup().getGroupID(), group.getGroupID(), "Match is not for the expected group");
        }

    }


    void canRemoveMatch() {

        //-----
        // DATA
        //-----

        UUID userID = UUID.randomUUID();
        UUID groupID = UUID.randomUUID();

        //-----
        // PREP
        //-----

        // attempt to register a partial match
        UUID matchID = matchService.registerMatch(
            userID,
            groupID
        );

        //-----
        // ACT
        //-----

        // attempt to delete a value
        Boolean removed = matchService.removeMatch(matchID);
        
        // retrieve entity from repository
        MatchEntity matchEntity = matchRepository.findById(matchID)
            .orElse(null);

        //--------
        // ASSERT
        //--------

        assertNull(matchEntity, "Returned match entity should be null");
        assertTrue(removed, "Boolean removed should be true");

        assertThrows(
            MatchNotFoundException.class,
            () -> matchService.getMatch(matchID)
        );


    }


    private Group registerTestGroup() {

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

        return group;
    }


}
