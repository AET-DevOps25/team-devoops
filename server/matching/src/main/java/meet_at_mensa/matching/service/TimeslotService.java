package meet_at_mensa.matching.service;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.IllegalInputException;
import meet_at_mensa.matching.exception.RequestNotFoundException;
import meet_at_mensa.matching.exception.TimeslotNotFoundException;
import meet_at_mensa.matching.model.TimeslotEntity;
import meet_at_mensa.matching.repository.TimeslotRepository;

@Service
public class TimeslotService {

    @Autowired
    private TimeslotRepository timeslotRepository;

    /**
     * Searches the database for all timeslots entries for a given request
     *
     * @param requestID UUID of the request being searched for
     * @return List of integer timeslots if found
     * @throws TimeslotNotFoundException if no entries are found for request with id {requestID}
     */
    public List<Integer> getTimeslots(UUID requestID) {

        // search the database for timeslots associated with a requestID
        Iterable<TimeslotEntity> timeslotEntities = timeslotRepository.findByRequestID(requestID);

        // if list is empty, throw exception
        if (!timeslotEntities.iterator().hasNext()) {
            throw new TimeslotNotFoundException();
        }

        // Extract integer values into a new list
        List<Integer> timeslots = new ArrayList<>();
        for (TimeslotEntity timeslotEntity : timeslotEntities) {
            timeslots.add(timeslotEntity.getTimeslot());
        }

        // return
        return timeslots;
    }

    /**
     * Searches the database for all timeslots entries for a given request
     *
     * @param requestID UUID of the request being registered
     * @return List of integer timeslots to be registered
     * @throws IllegalInputException if request is out of bounds 1-16
     */
    public List<Integer> registerTimeslots(UUID requestID, List<Integer> timeslots) {

        // Save each timeslot into the database
        for (Integer timeslot : timeslots) {

            // check in bounds
            if(timeslot < 1 || timeslot > 16){
                throw new IllegalInputException();
            }

            timeslotRepository.save(new TimeslotEntity(requestID, timeslot));
        }

        // fetch from database for return
        return getTimeslots(requestID);
    }


    /**
     * Removes all entries for a given request from the database
     *
     * @param requestID UUID of the request to be removed
     * @return True if a value was deleted
     * @throws RequestNotFoundException if no entries are found for request with id {requestID}
     */
    public Boolean deleteTimeslots(UUID requestID) {

        // search the database for timeslots associated with a requestID
        Iterable<TimeslotEntity> timeslotEntities = timeslotRepository.findByRequestID(requestID);

        // if list is empty, throw exception
        if (!timeslotEntities.iterator().hasNext()) {
            throw new TimeslotNotFoundException();
        }

        // Remove all entries from database
        for (TimeslotEntity timeslotEntity : timeslotEntities) {
            timeslotRepository.deleteById(timeslotEntity.getTimeslotID());
        }

        // return
        return true;
    }


    /**
     * Replaces all entries in a list with the new ones provided
     *
     * @param requestID UUID of the request being updated
     * @param timeslotUpdate List<Integer> list of new timeslots to replace the old one
     * @return List<Integer> of the timeslots, fetched from the database after the operation
     * @throws RequestNotFoundException if no entries are found for request with id {requestID}
     */
    public List<Integer> updateTimeslots(UUID requestID, List<Integer> timeslotUpdate) {

        // delete old timeslot data
        deleteTimeslots(requestID);

        // register new timeslot data
        registerTimeslots(requestID, timeslotUpdate);

        // fetch from database for return
        return getTimeslots(requestID);
    }



}
