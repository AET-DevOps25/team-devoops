package meet_at_mensa.matching.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;

import meet_at_mensa.matching.repository.MatchRequestRepository;
import meet_at_mensa.matching.repository.TimeslotRepository;
import meet_at_mensa.matching.model.MatchRequest;
import meet_at_mensa.matching.model.Timeslot;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// MatchingApplicationTests is a test class for the matching application
class RequestServiceTest {

    //private RequestService requestService = new RequestService();
    
    //@Autowired
    //private MatchRequestRepository matchRequestRepository;

    //@Autowired
    //private TimeslotRepository timeslotRepository;

    //GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("debug-match-database:latest")).withExposedPorts(3306);
    
    @Test
    void testContainerStarts() {
        try (
            GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("debug-match-database:latest")).withExposedPorts(3306);



        ) {
            container.start();
            assertTrue(container.isRunning(), "Container should be running");
            
            // Optionally, print mapped port
            System.out.println("MySQL mapped port: " + container.getMappedPort(3306));
        }

    }

    /*
     * 
     @Test
     void testSubmitRequest() {
        
     //myContainer.start();
     
     // data to use
     UUID userID = UUID.randomUUID();
     LocalDate date = LocalDate.now().plusDays(1);
     String location = "garching";
     Boolean degreePref = true;
     Boolean agePref = false;
     Boolean genderPref = false;
     
     // create a new match request
     MatchRequest request = new MatchRequest(
        userID,
        date,
        location,
        degreePref,
        agePref,
        genderPref
        );
        
        // create a list of time slots
        List<Integer> timeslotIntegers = List.of(1, 3, 5);
        
        // create database entries with requestService
        UUID requestId = requestService.submitRequest(request, timeslotIntegers);
        
        // get actual entries from DB
        MatchRequest actualRequest = matchRequestRepository.findById(requestId).orElse(null);
        Iterable<Timeslot> actualTimeslots = timeslotRepository.findByRequestID(requestId);
        
        // Assert that request objects match
        assertEquals(request, actualRequest, "Requests should match!");
        
        // Assert that time slots are correct
        for (Timeslot timeslot : actualTimeslots) {
            
        // Assert that the ID is correct
        assertEquals(request.getRequestID(), timeslot.getRequestID(), "Request IDs should Match");
        
        // Assert that the timeslot matches
        assertTrue(timeslotIntegers.contains(timeslot.getTimeslot()), "Time Slots Should Match!");
    }
}
*/

}
