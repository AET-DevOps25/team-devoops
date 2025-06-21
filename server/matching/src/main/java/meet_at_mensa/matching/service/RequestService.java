package meet_at_mensa.matching.service;

// spring imports
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

// local repositories
import meet_at_mensa.matching.repository.TimeslotRepository;
import meet_at_mensa.matching.repository.MatchRequestRepository;

// local models
import meet_at_mensa.matching.model.MatchRequest;
import meet_at_mensa.matching.model.Timeslot;

import java.time.LocalDate;
// java utils
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RequestService {
    
    // Contains individual match requests
    @Autowired
    private MatchRequestRepository requestRepository;

    // Contains: Timeslots a user has selected in their match request
    @Autowired
    private TimeslotRepository timeslotRepository;

    /**
    * Creates database entries for in the matchdb/match_requests and matchdb/match_timeslots tables
    *
    * @param request Request object to be added to the database
    * @param timeslots List of timeslot values corresponding to that request
    * @return generated UUID of the request (requestID)
    */
    public UUID submitRequest(MatchRequest request, List<Integer> timeslots) {

        // TODO: verify input

        // save request to matchdb/match_requests
        // store output in request so we can fetch the UUID later
        request = requestRepository.save(request);

        // save each slot number to the database along with the requestID
        for (Integer slotNumber : timeslots) {
            
            timeslotRepository.save(
                new Timeslot(request.getRequestID(), slotNumber)
            );

        }

        // return the UUID of the request
        return request.getRequestID();

    }

    /**
    * Finds all timeslots corresponding to a given requestID
    *
    * @param requestID UUID of the corresponding request
    * @return ArrayList of the integer values corresponding to the timeslots
    */
    public List<Integer> getTimeslots(UUID requestID) {

        // Get all timeslots for this request ID
        Iterable<Timeslot> timeslots = timeslotRepository.findByRequestID(requestID);

        // Create an empty list to return
        List<Integer> timeslotIntegers = new ArrayList<>();

        // Add timeslots to the list
        for (Timeslot t : timeslots) {
            timeslotIntegers.add(t.getTimeslot());
        }

        // Return list with timeslots
        return timeslotIntegers;

    }

    /**
    * Deletes all entries regarding a specific request from the match_request and match_timeslot databases
    *
    * @param requestID UUID of the match request to be deleted
    */
    public void deleteRequest (UUID requestID) {
        
        // delete request from MatchRequests
        requestRepository.deleteById(requestID);

        // get corresponding timeslot entries
        Iterable<Timeslot> timeslots = timeslotRepository.findByRequestID(requestID);

        // delete timeslot entries
        for (Timeslot timeslot : timeslots) {
            timeslotRepository.delete(timeslot);
        }

    }

    /**
    * Deletes all outdated entries regarding from the match_request and match_timeslot databases
    */
    public void deleteAllOutdated () {

        // Gets all requests
        Iterable<MatchRequest> requests = requestRepository.findAll();

        for (MatchRequest request : requests) {

            // removes outdated requests
            if (request.isOutdated()) {
                deleteRequest(request.getRequestID());
            }
        }

    }

}