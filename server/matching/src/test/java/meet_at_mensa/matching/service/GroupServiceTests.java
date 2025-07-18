package meet_at_mensa.matching.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.openapitools.model.ConversationStarter;
import org.openapitools.model.ConversationStarterCollection;
import org.openapitools.model.Group;
import org.openapitools.model.Location;
import org.openapitools.model.MatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.model.GroupEntity;
import meet_at_mensa.matching.repository.GroupRepository;
import meet_at_mensa.matching.util.Asserter;

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


        // -------
        // Cleanup
        // -------

        groupRepository.deleteById(groupEntity.getGroupID());

    }

    @Test
    void canGetGroup(){
        
        // ----
        // DATA
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

        // ----
        // PREP
        // ----

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

        // ID is not null
        assertNotNull(group, "Group should not be Null");

        // basic values match
        assertEquals(groupID, group.getGroupID(), "IDs should match!");
        assertEquals(date, group.getDate(), "Dates should Match");
        assertEquals(timeslot, group.getTime(), "Timeslots should match!");
        assertEquals(location, group.getLocation(), "locations should match");

        // Assert that ConversationStarters match
        Asserter.assertConversationStarterCollectionsMatch(
            prompts,
            group.getConversationStarters()
        );
        
        // Assert that group.getUserStatus matches and was created successfully
        Asserter.assertMatchStatusCreatedSuccessfully(
            List.of(userID1, userID2),
            group.getUserStatus()
        );

        
    }

    @Test
    void canGetMatchStatuses(){
        
        // ----
        // DATA
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

        // ----
        // PREP
        // ----

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

        // Assert that UserStatus matches and was created successfully
        Asserter.assertMatchStatusCreatedSuccessfully(
            List.of(userID1, userID2),
            status
        );
    }


    @Test
    void canGetGroupsOnDate(){
        
        // ----
        // DATA
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

        // ----
        // PREP
        // ----

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

        List<Group> groups = groupService.getGroupsOnDate(LocalDate.now());

        // -----
        // ASSERT
        // -----

        // Group is not null
        assertNotNull(groups, "Group should not be Null");

        // At least one element in group
        assertTrue(groups.size() >= 1, "List of groups should not be empty");


        for (Group group : groups) {
            
            assertEquals(LocalDate.now(), group.getDate(), "Group should take place today!");

        }

        
    }


    @Test
    void canGetGroupsBeforeDate(){
        
        // ----
        // DATA
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

        // ----
        // PREP
        // ----

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

        // get all groups older than 1 day after today
        List<Group> groups = groupService.getGroupsOlderThan(LocalDate.now().plusDays(1));

        // -----
        // ASSERT
        // -----

        // Group is not null
        assertNotNull(groups, "Group should not be Null");

        // At least one element in group
        assertTrue(groups.size() >= 1, "List of groups should not be empty");


        for (Group group : groups) {

            assertTrue(group.getDate().isBefore(LocalDate.now().plusDays(1)), "Groups should be before Tomorrow!");

        }

        
    }



    @Test
    void canRemoveGroup(){
        
        // ----
        // DATA
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

        // ----
        // PREP
        // ----

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

        // attempt to remove group
        groupService.removeGroup(groupID);

        // -----
        // ASSERT
        // -----

        assertThrows(
            GroupNotFoundException.class,
            () -> groupService.getGroup(groupID)
        );
        
    }


    
}