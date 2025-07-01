package meet_at_mensa.matching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
        // TODO: Implement deletion logic using requestId
        return ResponseEntity.ok().build();
    }

    // GET @ api/v2/matching/matches/{userID}
    @Override
    public ResponseEntity<MatchCollection> getApiV2MatchingMatchesUserID(UUID userId) {
        // TODO: Fetch matches for userId
        MatchCollection collection = new MatchCollection();
        return ResponseEntity.ok(collection);
    }

    // GET @ api/v2/matching/requests/{userID}
    @Override
    public ResponseEntity<MatchRequestCollection> getApiV2MatchingRequestsUserID(UUID userId) {
        // TODO: Fetch match requests for userId
        MatchRequestCollection collection = new MatchRequestCollection();
        return ResponseEntity.ok(collection);
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
    public ResponseEntity<Void> postApiV2MatchingRequestSubmit(@Valid MatchRequestNew matchRequestNew) {
        // TODO: Handle new match request submission
        return ResponseEntity.ok().build();
    }

    // PUT @ api/v2/matching/request/{requestID}
    @Override
    public ResponseEntity<MatchRequest> putApiV2MatchingRequestRequestId(
            UUID requestId,
            @Valid MatchRequestUpdate matchRequestUpdate) {
        // TODO: Handle updating match request
        MatchRequest updated = new MatchRequest(); // Fill with updated data
        return ResponseEntity.ok(updated);
    }

}