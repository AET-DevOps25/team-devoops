package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.time.LocalTime;
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
import meet_at_mensa.matching.exception.ScheduleException;
import meet_at_mensa.matching.model.MatchRequestEntity;
import meet_at_mensa.matching.repository.MatchRequestRepository;

@Service
public class MatchRequestService {

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
            if (requestEntity.getRequestStatus() != RequestStatus.MATCHED && requestEntity.getRequestStatus() != RequestStatus.EXPIRED){

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
    public MatchRequest registerRequest(MatchRequestNew requestNew) {

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

        // save to database
        requestRepository.save(requestEntity);

        // register timeslots
        timeslotService.registerTimeslots(
            requestEntity.getRequestID(), // newly generated requestID
            requestNew.getTimeslot() // timeslots
        );

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
     * Check if a user has submitted a match request on a given day
     *
     *
     * @param date LocalDate of the date
     * @param userID UUID of the user
     * @return MatchRequest for the user on the date
     * @throws RequestNotFoundException if no request is found
     */
    public MatchRequest getUserRequestOn(LocalDate date, UUID userID) {

        // get all entries for user on date
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findByDateAndUserID(date, userID);

        if(!requestEntities.iterator().hasNext()) {
            throw new RequestNotFoundException();
        }
    
        return getRequest(requestEntities.iterator().next().getRequestID());
    }


    /**
     * Remove a MatchRequest from the database including it's timeslots
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
    public MatchRequest updateRequestStatus(UUID requestID, RequestStatus statusNew) {

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


    /**
     * Checks all requests for expiration and set their status accordingly
     * 
     * WARNING: This should only be triggered by a cron job AFTER performing the matches
     * as it WILL set requests for TODAY as expired. It 11:00
     * 
     * @throws ScheduleException if it is triggered before 11:00 on a given day
     */
    protected void expireRequests() {

        // get all request-entities
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findAll();

        // Throws an exception if this function is triggered before 15:00
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))){
            // TODO: Figure out a better way to handle this case
            //throw new ScheduleException("Operation expireRequests() cannot be run before 11:00!");
        }

        // check all matchRequests
        for (MatchRequestEntity requestEntity : requestEntities) {
            
            // if the request is for TODAY or BEFORE, then set it as expired
            if (requestEntity.getDate().isBefore(LocalDate.now()) || requestEntity.getDate().isEqual(LocalDate.now())) {

                // set request status to expired
                updateRequestStatus(requestEntity.getRequestID(), RequestStatus.EXPIRED);

            }

        }

    }

    /**
     * Removes all expired requests from the database
     * 
     * WARNING: This should only be triggered by a cron job
     * 
     */
    protected void cleanupExpired() {

        // get all request-entities
        Iterable<MatchRequestEntity> requestEntities = requestRepository.findAll();

        // check all matchRequests
        for (MatchRequestEntity requestEntity : requestEntities) {
            
            // if the request is expired
            if (requestEntity.getRequestStatus() == RequestStatus.EXPIRED) {

                // remove the request and it's associated timetables
                removeRequest(requestEntity.getRequestID());
            }
        }
    }
}