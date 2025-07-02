package meet_at_mensa.matching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import meet_at_mensa.matching.exception.MatchNotFoundException;
import meet_at_mensa.matching.exception.RequestNotFoundException;
import meet_at_mensa.matching.exception.RequestOverlapException;
import meet_at_mensa.matching.service.MatchRequestService;
import meet_at_mensa.matching.service.MatchService;
import meet_at_mensa.matching.service.MatchingService;

import java.util.UUID;

import org.openapitools.api.MatchingApi;
import org.openapitools.model.*;

@RestController
public class MatchingController implements MatchingApi {

    @Autowired
    MatchRequestService requestService;

    @Autowired
    MatchingService matchingService;

    @Autowired
    MatchService matchService;

    // DELETE @ api/v2/matching/request/{requestID}
    @Override
    public ResponseEntity<Void> deleteApiV2MatchingRequestRequestId(UUID requestId) {

        // 200
        try {

            // attempt to remove request
            requestService.removeRequest(requestId);

            // if ok, return 200
            return ResponseEntity.ok().build();


        // 404
        } catch (RequestNotFoundException e) {
            
            // if request not found return 404
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // if some other exception occurs
            return ResponseEntity.internalServerError().build();

        }
    }

    // GET @ api/v2/matching/matches/{userID}
    @Override
    public ResponseEntity<MatchCollection> getApiV2MatchingMatchesUserID(UUID userId) {


        // 200
        try {
            
            // attempt to get matches
            MatchCollection matches = matchService.getMatches(userId);

            // return 200 with MatchCollection if found
            return ResponseEntity.ok(matches);

        // 404
        } catch (MatchNotFoundException e) {
            
            // return 404 if no matches are found
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // return 500 if another exception occurs
            return ResponseEntity.internalServerError().build();

        }

    }

    // GET @ api/v2/matching/requests/{userID}
    @Override
    public ResponseEntity<MatchRequestCollection> getApiV2MatchingRequestsUserID(UUID userId) {

        // 200
        try {
            
            // attempt to get MatchRequests
            MatchRequestCollection requests = requestService.getUserRequests(userId);

            // return 200 with MatchRequestCollection if found
            return ResponseEntity.ok(requests);

        // 404
        } catch (RequestNotFoundException e) {
            
            // return 404 if no MatchRequests are found
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // return 500 if another exception occurs
            return ResponseEntity.internalServerError().build();

        }

    }

    // GET @ api/v2/matching/rsvp/{matchID}/accept
    @Override
    public ResponseEntity<Void> getApiV2MatchingRsvpMatchIdAccept(UUID matchId) {
        // TODO: Handle RSVP accept
        return ResponseEntity.ok().build();
    }

    // GET @ api/v2/matching/rsvp/{matchID}/reject
    @Override
    public ResponseEntity<Void> getApiV2MatchingRsvpMatchIdReject(UUID matchId) {
        // TODO: Handle RSVP reject
        return ResponseEntity.ok().build();
    }

    // POST @ api/v2/matching/request/submit
    @Override
    public ResponseEntity<MatchRequest> postApiV2MatchingRequestSubmit(@Valid MatchRequestNew matchRequestNew) {

        // 200
        try {
            
            // attempt to register a new matchRequset
            MatchRequest request = requestService.registerRequests(matchRequestNew);

            // return 200 if successful
            return ResponseEntity.ok(request);

        // 409
        } catch (RequestOverlapException e) {
            
            // return 409 if user already has a request on that day
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        // 500
        } catch (Exception e) {

            // return 500 if another exception occurs
            return ResponseEntity.internalServerError().build();

        }
    }

    // PUT @ api/v2/matching/request/{requestID}
    @Override
    public ResponseEntity<MatchRequest> putApiV2MatchingRequestRequestId(UUID requestId, @Valid MatchRequestUpdate matchRequestUpdate) {
                
        // 200
        try {

            // attempt to update match request
            MatchRequest request = requestService.updateRequest(requestId, matchRequestUpdate);
            
            // return 200 with MatchRequestCollection if found
            return ResponseEntity.ok(request);

        // 404
        } catch (RequestNotFoundException e) {
            
            // return 404 if no MatchRequests are found
            return ResponseEntity.notFound().build();

        // 500
        } catch (Exception e) {

            // return 500 if another exception occurs
            return ResponseEntity.internalServerError().build();

        }
    }

}