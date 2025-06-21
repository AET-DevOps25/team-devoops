package meet_at_mensa.matching.service;

// spring imports
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

// local imports
import meet_at_mensa.matching.repository.GroupRepository;
import meet_at_mensa.matching.repository.MatchRepository;
import meet_at_mensa.matching.repository.TimeslotRepository;
import meet_at_mensa.matching.repository.MatchRequestRepository;

import meet_at_mensa.matching.model.*;

// java utils

@Service
public class MatchingService {
    
    // Contains individual match requests
    @Autowired
    private MatchRequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    // Contains individual (user, group) pairs
    @Autowired
    private MatchRepository matchRepository;

    // Contains info on what timeslots a user has selected in their match request
    @Autowired
    private TimeslotRepository timeslotRepository;

    // Contains info on matched groups
    @Autowired
    private GroupRepository groupRepository;

    /**
    * Perform the actual matching process
    * {details on the algorithm}
    *
    * @param date the date for which to perform matching
    */
    public void match(LocalDate date) {

        // get all Matching requests for this date
        Iterable<MatchRequest> requests = requestRepository.findByDate(date);

        Iterable<Timeslots>

        for (MatchRequest matchRequest : requests) {
            
            // get timeslots
        }
        requestService.getTimeslots(null)

        // get Timeslots

        // TODO: do some matching magic



    }


}