package meet_at_mensa.matching.service;

import java.util.UUID;

import org.openapitools.model.InviteStatus;
import org.openapitools.model.Match;
import org.openapitools.model.MatchCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.MatchNotFoundException;
import meet_at_mensa.matching.model.MatchEntity;
import meet_at_mensa.matching.repository.MatchRepository;

@Service
public class MatchService {

    // TODO: Removed Expired


    @Autowired
    private MatchRepository matchRepository;

    @Autowired 
    private GroupService groupService;

    /**
     * Retieves a specific match by it's matchID
     *
     *
     * @param matchID
     * @return Match object
     * @throws MatchNotFoundException if no user with id {userID} is found
     */
    public Match getMatch(UUID matchID) {

        // search the database for a match
        // throw matchnotfound exception if no match is found
        MatchEntity matchEntity = matchRepository.findById(matchID)
            .orElseThrow(() -> new MatchNotFoundException());


        // construct match object
        Match match = new Match(
            matchEntity.getMatchID(), 
            matchEntity.getUserID(), 
            matchEntity.getInviteStatus(), 
            groupService.getGroup(matchEntity.getGroupID())
        );

        // return the match
        return match;

    }


    /**
     * Retieves all matchs for a given userID
     *
     *
     * @param userID
     * @return MatchCollection object
     * @throws MatchNotFoundException if no user with id {userID} is found
     */
    public MatchCollection getMatches(UUID userID) {

        // get all matches in the system for a given group ID
        Iterable<MatchEntity> matchEntities = matchRepository.findByUserID(userID);

        // if list is empty, throw exception
        if (!matchEntities.iterator().hasNext()) {
            throw new MatchNotFoundException();
        }

        // create empty matchcollection
        MatchCollection matchCollection = new MatchCollection();

        // add each match to the collection
        for (MatchEntity matchEntity : matchEntities) {

            // construct match object
            Match match = new Match(
                matchEntity.getMatchID(), 
                matchEntity.getUserID(), 
                matchEntity.getInviteStatus(), 
                groupService.getGroup(matchEntity.getGroupID())
            );

            // add to list
            matchCollection.addMatchesItem(match);
        }

        // return collection of matches
        return matchCollection;
    }

    /**
     * Registers a new match in the database
     *
     * This function is intended for use by internal functions only, and should not be used in the controllers
     *
     * Returns only UUID since this function does not instatiate the group object on its own
     * 
     * @param userID ID of the user matched
     * @param groupID ID of the group user was matched to
     * @return MatchCollection object
     */
    protected UUID registerMatch(UUID userID, UUID groupID) {

        // create a matchEntity object
        MatchEntity matchEntity = new MatchEntity(
            userID,
            groupID,
            InviteStatus.UNSENT
        );
        
        // add it to the database
        matchRepository.save(matchEntity);


        // return the generated ID
        return matchEntity.getMatchID();

    }

    /**
     * Updates the status of a match
     *
     * @param matchID ID of the match to be updated
     * @param statusNew new Status
     * @return Match object with the updated status
     * @throws MatchNotFoundException if a match is not found
     */
    public Match updateStatus(UUID matchID, InviteStatus statusNew) {

        // search the database for a match
        // throw matchnotfound exception if no match is found
        MatchEntity matchEntity = matchRepository.findById(matchID)
            .orElseThrow(() -> new MatchNotFoundException());

        // set new invite status
        matchEntity.setInviteStatus(statusNew);

        // save entity to database
        matchRepository.save(matchEntity);

        // fetch from database and return
        return getMatch(matchID);

    }


    /**
     * Deletes match with matchID from the database
     *
     * @param matchID ID of the match to be deleted
     * @return true if match was deleted
     * @throws MatchNotFoundException if a match is not found
     */
    public boolean removeMatch(UUID matchID) {

        // search the database for a match
        // throw matchnotfound exception if no match is found
        matchRepository.findById(matchID)
            .orElseThrow(() -> new MatchNotFoundException());

        // if an item is found, delete it
        matchRepository.deleteById(matchID);

        return true;
    }

}
