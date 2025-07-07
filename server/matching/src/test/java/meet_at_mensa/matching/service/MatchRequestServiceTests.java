package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.openapitools.model.MatchRequestUpdate;
import org.openapitools.model.Location;
import org.openapitools.model.MatchPreferences;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.MatchRequestNew;
import org.openapitools.model.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

import meet_at_mensa.matching.exception.RequestNotFoundException;
import meet_at_mensa.matching.exception.RequestOverlapException;
import meet_at_mensa.matching.model.MatchRequestEntity;
import meet_at_mensa.matching.repository.MatchRequestRepository;
import meet_at_mensa.matching.util.Asserter;

@SpringBootTest
@Testcontainers
class MatchingRequestServiceTests {


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
    MatchRequestService matchRequestService;

    @Autowired
    MatchRequestRepository requestRepository;


    @Test
    void canRegisterRequest() {

        //-----
        // DATA
        //-----

        MatchRequestNew matchRequestNew = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, true) // preference
        );

        //-----
        // PREP
        //-----

        //-----
        // ACT
        //-----

        // get MatchRequest
        MatchRequest request = matchRequestService.registerRequest(matchRequestNew);

        // get MatchRequestEntity
        MatchRequestEntity requestEntity = requestRepository.findById(request.getRequestID())
            .orElse(null);

        //--------
        // ASSERT
        //--------

        assertNotNull(request, "Values should not be null");
        assertNotNull(requestEntity, "Values should not be null");

        assertEquals(requestEntity.getRequestID(), request.getRequestID());
        
        assertEquals(matchRequestNew.getUserID(), requestEntity.getUserID());
        assertEquals(matchRequestNew.getDate(), requestEntity.getDate());
        assertEquals(matchRequestNew.getLocation(), requestEntity.getLocation());
        assertEquals(matchRequestNew.getPreferences().getAgePref(), requestEntity.getPreferences().getAgePref());
        assertEquals(matchRequestNew.getPreferences().getGenderPref(), requestEntity.getPreferences().getGenderPref());
        assertEquals(matchRequestNew.getPreferences().getDegreePref(), requestEntity.getPreferences().getDegreePref());
        
        // check that timeslots are the same
        assertEquals(
            new HashSet<>(matchRequestNew.getTimeslot()),
            new HashSet<>(request.getTimeslot()),
            "Timeslots should Match"
        );
    }

    @Test
    void canUpdateRequest() {

        //-----
        // DATA
        //-----

        MatchRequestNew matchRequestNew = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, true) // preference
        );

        MatchRequestUpdate matchRequestUpdate = new MatchRequestUpdate()
            .date(LocalDate.now().plusDays(2))
            .timeslot(List.of(1,2,3,4))
            .location(Location.ARCISSTR)
            .preferences(new MatchPreferences(false, false, false));

        //-----
        // PREP
        //-----

        //-----
        // ACT
        //-----

        // get MatchRequest
        MatchRequest request = matchRequestService.registerRequest(matchRequestNew);

        request.setDate(matchRequestUpdate.getDate());
        request.setTimeslot(matchRequestUpdate.getTimeslot());
        request.setLocation(matchRequestUpdate.getLocation());
        request.setPreferences(matchRequestUpdate.getPreferences());

        // update request
        MatchRequest updatedRequest = matchRequestService.updateRequest(request.getRequestID(), matchRequestUpdate);

        //--------
        // ASSERT
        //--------

        Asserter.assertMatchRequestsAreIdentical(request, updatedRequest);
    }

    @Test
    void canUpdateRequestStatus() {

        //-----
        // DATA
        //-----

        MatchRequestNew matchRequestNew = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, true) // preference
        );

        //-----
        // PREP
        //-----

        // Register Match
        MatchRequest request = matchRequestService.registerRequest(matchRequestNew);

        //-----
        // ACT ASSERT
        //-----

        List<RequestStatus> statuses = List.of(RequestStatus.EXPIRED, RequestStatus.MATCHED, RequestStatus.PENDING, RequestStatus.REMATCH, RequestStatus.UNMATCHABLE);


        for (RequestStatus requestStatus : statuses) {

            // update status
            MatchRequest updatedRequest = matchRequestService.updateRequestStatus(request.getRequestID(), requestStatus);

            // assert that update has worked
            request.setStatus(requestStatus);
            Asserter.assertMatchRequestsAreIdentical(request, updatedRequest);

        }
    }

    @Test
    void canRemoveRequest() {

        //-----
        // DATA
        //-----

        MatchRequestNew matchRequestNew = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, true) // preference
        );

        //-----
        // PREP
        //-----

        // Register request
        MatchRequest request = matchRequestService.registerRequest(matchRequestNew);

        //-----
        // ACT
        //-----

        // remove request
        matchRequestService.removeRequest(request.getRequestID());

        //--------
        // ASSERT
        //--------

        assertThrows(
            RequestNotFoundException.class,
            () -> matchRequestService.getRequest(request.getRequestID())
        );
    }


    @Test
    void canGetAllUserRequests() {

        //-----
        // DATA
        //-----

        UUID userID = UUID.randomUUID();

        MatchRequestNew new1 = new MatchRequestNew(
            userID, // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, false) // preference
        );

        MatchRequestNew new2 = new MatchRequestNew(
            userID, // userID
            LocalDate.now().plusDays(2), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        MatchRequestNew new3 = new MatchRequestNew(
            userID, // userID
            LocalDate.now().plusDays(3), // date
            List.of(13,14,15,16), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(false, false, true) // preference
        );

        //-----
        // PREP
        //-----

        // Register requests
        MatchRequest request1 = matchRequestService.registerRequest(new1);
        MatchRequest request2 = matchRequestService.registerRequest(new2);
        MatchRequest request3 = matchRequestService.registerRequest(new3);

        //-----
        // ACT
        //-----

        MatchRequestCollection requests = matchRequestService.getUserRequests(userID);

        //--------
        // ASSERT
        //--------

        // assert that there are 3 requests
        assertEquals(requests.getRequests().size(), 3);

        // assert that both have the same requestIDs
        assertEquals(
            requests.getRequests().stream()
                .map(MatchRequest::getRequestID)
                .collect(Collectors.toSet()),
            List.of(request1, request2, request3).stream()
                .map(MatchRequest::getRequestID)
                .collect(Collectors.toSet())
        );
        
        // for each request assert that it matches
        for (MatchRequest requestRetrieved : requests.getRequests()) {

            for (MatchRequest requestLocal : List.of(request1, request2, request3)){

                if (requestLocal.getRequestID() == requestRetrieved.getRequestID()) {

                    Asserter.assertMatchRequestsAreIdentical(requestLocal, requestRetrieved);

                }
            }
        }   
    }

    @Test
    void canGetUnmatchedRequests() {

        //-----
        // DATA
        //-----

        MatchRequestNew new1 = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, false) // preference
        );

        MatchRequestNew new2 = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        MatchRequestNew new3 = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        MatchRequestNew new4 = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        MatchRequestNew new5 = new MatchRequestNew(
            UUID.randomUUID(), // userID
            LocalDate.now().plusDays(1), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        //-----
        // PREP
        //-----

        // Register requests
        MatchRequest pendingRequest = matchRequestService.registerRequest(new1);
        matchRequestService.updateRequestStatus(pendingRequest.getRequestID(), RequestStatus.PENDING);

        MatchRequest matchedRequest = matchRequestService.registerRequest(new2);
        matchRequestService.updateRequestStatus(matchedRequest.getRequestID(), RequestStatus.MATCHED);

        MatchRequest unmatchableRequest = matchRequestService.registerRequest(new3);
        matchRequestService.updateRequestStatus(unmatchableRequest.getRequestID(), RequestStatus.UNMATCHABLE);

        MatchRequest rematchRequest = matchRequestService.registerRequest(new4);
        matchRequestService.updateRequestStatus(rematchRequest.getRequestID(), RequestStatus.REMATCH);

        MatchRequest expiredRequest = matchRequestService.registerRequest(new5);
        matchRequestService.updateRequestStatus(expiredRequest.getRequestID(), RequestStatus.EXPIRED);

        
        

        //----
        // ACT
        //----

        MatchRequestCollection requests = matchRequestService.getUnmatchedRequests(LocalDate.now().plusDays(1));

        //-------
        // ASSERT
        //-------

        // assert at least one value has been found
        assertTrue(requests.getRequests().size() >= 3);

        for (MatchRequest request : requests.getRequests()) {
            assertNotEquals(request.getStatus(), RequestStatus.MATCHED);
            assertNotEquals(request.getStatus(), RequestStatus.EXPIRED);
        }
    }

    @Test
    void rejectsDuplicateEntries() {

        //-----
        // DATA
        //-----

        UUID userID = UUID.randomUUID();

        MatchRequestNew new1 = new MatchRequestNew(
            userID, // userID
            LocalDate.now().plusDays(1), // date
            List.of(6,7,8,9), // timeslot
            Location.GARCHING, // location
            new MatchPreferences(true, false, false) // preference
        );

        MatchRequestNew new2 = new MatchRequestNew(
            userID, // userID
            LocalDate.now().plusDays(1), // date
            List.of(10,11,12,13), // timeslot
            Location.ARCISSTR, // location
            new MatchPreferences(false, true, false) // preference
        );

        //-----
        // PREP
        //-----

        // Register requests
        matchRequestService.registerRequest(new1);
        

        //-------------
        // ACT / ASSERT
        //-------------

        assertThrows(
            RequestOverlapException.class,
            () -> matchRequestService.registerRequest(new2)
        );

    }




}