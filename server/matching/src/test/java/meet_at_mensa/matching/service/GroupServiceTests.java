package meet_at_mensa.matching.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import meet_at_mensa.matching.model.GroupEntity;
import meet_at_mensa.matching.repository.GroupRepository;

@SpringBootTest
@Testcontainers
class GroupServiceTests {


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
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MatchService matchService;

    @Autowired
    ConversationStarterService conversationStarterService;


    @Test
    void canPartialRegisterGroup(){
        
        // ----
        // PREP
        // ----

        LocalDate date = LocalDate.now();
        Integer timeslot = 6;
        Location location = Location.ARCISSTR;
        

        // ----
        // ACT
        // ----

        // add partial group do database
        UUID groupID = groupService.registerGroup(date, timeslot, location);

        // Get GroupEntity from repository
        // This is not a full group, but just the entries from the group table
        GroupEntity groupEntity = groupRepository.findById(groupID)
            .orElse(null);

        // -----
        // ASSERT
        // -----

        assertNotNull(groupEntity, "Group entity should not be Null!");
        assertEquals(date, groupEntity.getDate(), "The dates should match!");
        assertEquals(timeslot, groupEntity.getTimeslot(), "The timeslots should match!");
        assertEquals(location, groupEntity.getLocation(), "The Locations should match!");
        assertEquals(groupID, groupEntity.getGroupID(), "The Group IDs should match!");

    }

    @Test
    void canGetGroup(){
        
        // ----
        // PREP
        // ----

        // basic template
        LocalDate date = LocalDate.now();
        Integer timeslot = 6;
        Location location = Location.ARCISSTR;

        // generate random userIDs
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();

        // create placeholder conversation starters
        ConversationStarterCollection prompts = new ConversationStarterCollection();
        prompts.addConversationsStartersItem(
            new ConversationStarter("User 1 likes Cats")
        );
        prompts.addConversationsStartersItem(
            new ConversationStarter("User 2 likes Dogs")
        );

        // add partial group to database
        UUID groupID = groupService.registerGroup(date, timeslot, location);

        // add matches to database
        matchService.registerMatch(userID1, groupID);
        matchService.registerMatch(userID2, groupID);

        // add Conversation Starters to database
        conversationStarterService.registerPrompts(groupID, prompts);

        // ----
        // ACT
        // ----

        Group group = groupService.getGroup(groupID);

        // -----
        // ASSERT
        // -----

        assertNotNull(group, "Group should not be Null");
        assertEquals(groupID, group.getGroupID(), "IDs should match!");
        assertEquals(date, group.getDate(), "Dates should Match");
        assertEquals(timeslot, group.getTime(), "Timeslots should match!");
        assertEquals(location, group.getLocation(), "locations should match");

        // check that conversationStarters match
        assertEquals(
            group.getConversationStarters().getConversationsStarters().stream()
                .map(ConversationStarter::getPrompt)
                .collect(Collectors.toSet()),

            prompts.getConversationsStarters().stream()
                .map(ConversationStarter::getPrompt)
                .collect(Collectors.toSet())
        );

        // check if the userIDs match
        assertEquals(
                new HashSet<>(List.of(userID1, userID2)),
                new HashSet<>(group.getUserStatus().stream()
                                .map(MatchStatus::getUserID)
                                .collect(Collectors.toList())
                ),
                "User IDs should Match"
            );

        // check if every entry has unsent status
        for (MatchStatus matchStatus : group.getUserStatus()) {
            assertEquals(matchStatus.getStatus(), InviteStatus.UNSENT, "Status is wrong!");
        }

        
    }

    @Test
    void canGetMatchStatuses(){
        
        // ----
        // PREP
        // ----

        // template information
        LocalDate date = LocalDate.now();
        Integer timeslot = 6;
        Location location = Location.ARCISSTR;

        // add partial group to database
        UUID groupID = groupService.registerGroup(date, timeslot, location);

        // generate random userIDs
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();

        // add matches
        matchService.registerMatch(userID1, groupID);
        matchService.registerMatch(userID2, groupID);
        

        // ----
        // ACT
        // ----

        // attempt to get status
        List<MatchStatus> status = groupService.getGroupStatus(groupID);

        // -----
        // ASSERT
        // -----

        // check if the userIDs match
        assertEquals(
                new HashSet<>(List.of(userID1, userID2)),
                new HashSet<>(status.stream()
                                .map(MatchStatus::getUserID)
                                .collect(Collectors.toList())
                ),
                "User IDs should Match"
            );

        // check if every entry has unsent status
        for (MatchStatus matchStatus : status) {
            assertEquals(matchStatus.getStatus(), InviteStatus.UNSENT, "Status is wrong!");
        }
    }


    
}