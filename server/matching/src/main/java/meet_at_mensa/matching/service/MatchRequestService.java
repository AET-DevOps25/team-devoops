package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.UUID;

import org.openapitools.model.MatchRequest;
import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.MatchRequestNew;
import org.openapitools.model.MatchRequestUpdate;
import org.openapitools.model.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.RequestNotFoundException;
import meet_at_mensa.matching.exception.RequestOverlapException;
import meet_at_mensa.matching.model.MatchRequestEntity;
import meet_at_mensa.matching.repository.MatchRequestRepository;

@Service
public class MatchRequestService {

    // Remove Expired

    @Autowired
    private MatchRequestRepository requestRepository;

    @Autowired
    private TimeslotService timeslotService;

    /**
     * Fetch a single MatchRequest based on its requestID
     *
     *
     * @param requestID UUID of the request being fetched
     * @return MatchRequest object corresponding to the request
     * @throws RequestNotFoundException if no request with id {requestID} is found
     */
    public MatchRequest getRequest(UUID requestID) {

        // find match request based on it's matchID
        // throws RequestNotFoundException if no request is found
        MatchRequestEntity requestEntity = requestRepository.findById(requestID)
            .orElseThrow(() -> new RequestNotFoundException());

        // create new MatchRequest Object
        MatchRequest request = new MatchRequest(
            requestEntity.getRequestID(), // requestID
            requestEntity.getUserID(), // userID
            requestEntity.getDate(), // date 
            timeslotService.getTimeslots(requestID), // timeslots
            requestEntity.getLocation(), 
            requestEntity.getPreferences(), 
            requestEntity.getRequestStatus()
        );


        // return new match object
        return request;
    }


    /**
     * Fetch all MatchRequests for a user with userID
     *
     *
     * @param userID UUID of the user who's requests are being fetched
     * @return MatchRequestCollection object corresponding to all the requests
     * @throws RequestNotFoundException if no request with id {requestID} is found
     */
    public MatchRequestCollection getUserRequests(UUID userID) {


        // search the database for match requests associated with the userID
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findByUserID(userID);

        // if the list is empty, throw a RequestNotFound Exception
        if (!requestEntities.iterator().hasNext()) {
            throw new RequestNotFoundException();
        }

        // Create empty collection
        MatchRequestCollection requestCollection = new MatchRequestCollection();

        for (MatchRequestEntity requestEntity : requestEntities) {
            
            // create new MatchRequest Object
            MatchRequest request = new MatchRequest(
                requestEntity.getRequestID(), // requestID
                requestEntity.getUserID(), // userID
                requestEntity.getDate(), // date 
                timeslotService.getTimeslots(requestEntity.getRequestID()), // timeslots
                requestEntity.getLocation(), // location
                requestEntity.getPreferences(), // Match preferences
                requestEntity.getRequestStatus() // Status
            );

            // add to request collection
            requestCollection.addRequestsItem(request);
        }

        // returns the collection
        return requestCollection;
    }


    /**
     * Fetch all MatchRequests for a given Date
     *
     *
     * @param date date of the request
     * @return MatchRequestCollection of all requests for this date
     * @throws RequestNotFoundException if no request with id {requestID} is found
     */
    public MatchRequestCollection getUnmatchedRequests(LocalDate date) {


        // search the database for match requests associated with the given date
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findByDate(date);

        // if the list is empty, throw a RequestNotFound Exception
        if (!requestEntities.iterator().hasNext()) {
            throw new RequestNotFoundException();
        }

        // Create empty collection
        MatchRequestCollection requestCollection = new MatchRequestCollection();

        for (MatchRequestEntity requestEntity : requestEntities) {

            // if the request is unmatched
            if (requestEntity.getRequestStatus() != RequestStatus.MATCHED){

                // create new MatchRequest Object
                MatchRequest request = new MatchRequest(
                    requestEntity.getRequestID(), // requestID
                    requestEntity.getUserID(), // userID
                    requestEntity.getDate(), // date 
                    timeslotService.getTimeslots(requestEntity.getRequestID()), // timeslots
                    requestEntity.getLocation(), // location
                    requestEntity.getPreferences(), // Match preferences
                    requestEntity.getRequestStatus() // Status
                );

                // add to request collection
                requestCollection.addRequestsItem(request);
            }
            
        }

        // returns the collection
        return requestCollection;
    }


    /**
     * Register a new MatchRequest
     *
     *
     * @param requestNew MatchRequestNew object containing info about the new request
     * @return MatchRequest object of the registered request
     * @throws RequestOverlapException if a request already exists for this user on this date
     */
    public MatchRequest registerRequests(MatchRequestNew requestNew) {

        // checks if user already has a request for this date. Throws an exeption if yes
        if (userHasRequestOn(requestNew.getDate(), requestNew.getUserID())) {
            throw new RequestOverlapException();
        }

        // Create a new MatchRequest Entity
        MatchRequestEntity requestEntity = new MatchRequestEntity(
            requestNew.getUserID(), // userID
            requestNew.getDate(), // date
            requestNew.getLocation(), // location
            requestNew.getPreferences().getDegreePref(), // degree pref
            requestNew.getPreferences().getAgePref(),  // age pref
            requestNew.getPreferences().getGenderPref() // gender pref
        );

        // register timeslots
        timeslotService.registerTimeslots(
            requestEntity.getRequestID(), // newly generated requestID
            requestNew.getTimeslot() // timeslots
        );

        // save to database
        requestRepository.save(requestEntity);

        // return newly generated request
        return getRequest(requestEntity.getRequestID());
        
    }


    /**
     * Check if a user has submitted a match request on a given day
     *
     *
     * @param date LocalDate of the date
     * @param userID UUID of the user
     * @return true if the user has a request for this date in the system, false if not
     */
    public Boolean userHasRequestOn(LocalDate date, UUID userID) {

        // get all entries for user on date
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findByDateAndUserID(date, userID);
    
        // return true if the iterator is not empty
        // return false if the iterator is empty
        return requestEntities.iterator().hasNext();
    }


    /**
     * Remove a MatchRequest from the database
     *
     *
     * @param requestID the ID of the match request to remove
     * @return true if a request has been removed
     * @throws RequestNotFoundException if no request is found
     */
    public Boolean removeRequest(UUID requestID) {

        // find match request based on it's matchID
        // throws RequestNotFoundException if no request is found
        requestRepository.findById(requestID)
            .orElseThrow(() -> new RequestNotFoundException());

        // delete the request
        requestRepository.deleteById(requestID);

        // remove entries from the timeslot table as well
        timeslotService.deleteTimeslots(requestID);

        return true;
    }


    /**
     * Update a MatchRequest
     *
     *
     * @param requestID ID of the match request being updated
     * @param MatchRequestUpdate object containing updated MatchRequest
     * @return MatchRequest object fetched from database after the update
     * @throws RequestNotFoundException if no request is found
     */
    public MatchRequest updateRequest(UUID requestID, MatchRequestUpdate requestUpdate) {

        // find match request based on it's matchID
        // throws RequestNotFoundException if no request is found
        MatchRequestEntity requestEntity = requestRepository.findById(requestID)
            .orElseThrow(() -> new RequestNotFoundException());

        // update date
        if(requestUpdate.getDate() != null) {
            requestEntity.setDate(requestUpdate.getDate());
        }

        // update timeslot
        if(requestUpdate.getTimeslot() != null) {
            timeslotService.updateTimeslots(requestID, requestUpdate.getTimeslot());
        }

        // update location
        if(requestUpdate.getLocation() != null) {
            requestEntity.setLocation(requestUpdate.getLocation());
        }

        // update preferences
        if (requestUpdate.getPreferences() != null) {
            requestEntity.setPreferences(requestUpdate.getPreferences());
        }

        // save changes to database
        requestRepository.save(requestEntity);

        // fetch from database to return
        return getRequest(requestID);

    }

    /**
     * Update the status of a MatchRequest
     *
     *
     * @param requestID ID of the match request being updated
     * @param statusNew new RequestStatus
     * @return MatchRequest object fetched from database after the update
     * @throws RequestNotFoundException if no request is found
     */
    public MatchRequest updateRequstStatus(UUID requestID, RequestStatus statusNew) {

        // find match request based on it's matchID
        // throws RequestNotFoundException if no request is found
        MatchRequestEntity requestEntity = requestRepository.findById(requestID)
            .orElseThrow(() -> new RequestNotFoundException());

        // set new status
        requestEntity.setRequestStatus(statusNew);

        // save to database
        requestRepository.save(requestEntity);

        // fetch new request from database for return
        return getRequest(requestID);

    }
}