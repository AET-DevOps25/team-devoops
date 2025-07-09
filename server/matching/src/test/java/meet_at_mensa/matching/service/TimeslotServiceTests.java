package meet_at_mensa.matching.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import meet_at_mensa.matching.exception.IllegalInputException;
import meet_at_mensa.matching.exception.TimeslotNotFoundException;

@SpringBootTest
@Testcontainers
class TimeslotServiceTests {


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
    TimeslotService timeslotService;


    @Test
    void canRegisterTimeslot(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();

        List<Integer> timeslots = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        

        // ----
        // ACT
        // ----

        List<Integer> output = timeslotService.registerTimeslots(
            requestID,
            timeslots
        );

        // ----
        // ASSERT
        // ----

        assertEquals(16, output.size(), "There should be 16 timeslots!");
        assertEquals(new HashSet<>(timeslots), new HashSet<>(output), "Timeslots should match!");
    }


    @Test
    void canGetTimeslots(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();

        // create a list
        List<Integer> timeslots = List.of(6,9);

        // register timeslots
        timeslotService.registerTimeslots(
            requestID,
            timeslots
        );
        

        // ----
        // ACT
        // ----

        List<Integer> output = timeslotService.getTimeslots(requestID);
        

        // ----
        // ASSERT
        // ----

        assertEquals(2, output.size(), "There should be 2 timeslots!");
        assertEquals(new HashSet<>(timeslots), new HashSet<>(output), "Timeslots should match!");
    }


    @Test
    void canUpdateTimeslots(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();

        // create a list with initial input
        List<Integer> timeslots = List.of(1,2);

        // create a list with the update
        List<Integer> update = List.of(1,15,16);

        // register timeslots
        timeslotService.registerTimeslots(
            requestID,
            timeslots
        );
        

        // ----
        // ACT
        // ----

        List<Integer> output = timeslotService.updateTimeslots(requestID, update);
        

        // ----
        // ASSERT
        // ----

        assertEquals(3, output.size(), "There should be 3 timeslots!");
        assertEquals(new HashSet<>(update), new HashSet<>(output), "Timeslots should match!");
    }


    @Test
    void canDeleteTimeslots(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();

        // create a list with initial input
        List<Integer> timeslots = List.of(4,2);

        // register timeslots
        timeslotService.registerTimeslots(
            requestID,
            timeslots
        );
        

        // ----
        // ACT
        // ----

        // attempt to delete
        timeslotService.deleteTimeslots(requestID);
        

        // ----
        // ASSERT
        // ----

        assertThrows(
            TimeslotNotFoundException.class,
            () -> timeslotService.getTimeslots(requestID),
            "Should Throw TimeslotNotFoundException"
        );
    }


    @Test
    void rejectsInvalidTimeslots(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();

        // create a list with initial input
        List<Integer> timeslots = List.of(17);
        List<Integer> timeslots2 = List.of(0);
        

        // ----
        // ACT
        // ----
        

        // ----
        // ASSERT
        // ----

        assertThrows(
            IllegalInputException.class,
            () -> timeslotService.registerTimeslots(requestID, timeslots),
            "Should not accept >16"
        );

        assertThrows(
            IllegalInputException.class,
            () -> timeslotService.registerTimeslots(requestID, timeslots2),
            "Should not accept 0"
        );
    }


    @Test
    void failsGetGracefully(){
        
        // ----
        // PREP
        // ----

        // generate a random UUID
        UUID requestID = UUID.randomUUID();
        

        // ----
        // ACT
        // ----
        

        // ----
        // ASSERT
        // ----

        assertThrows(
            TimeslotNotFoundException.class,
            () -> timeslotService.getTimeslots(requestID),
            "Should throw TimeslotNotFoundException"
        );
    }

}