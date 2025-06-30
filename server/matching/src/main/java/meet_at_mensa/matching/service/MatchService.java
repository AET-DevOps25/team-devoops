package meet_at_mensa.matching.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openapitools.model.MatchStatus;
import org.springframework.beans.factory.annotation.Autowired;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.model.MatchEntity;
import meet_at_mensa.matching.repository.MatchRepository;

public class MatchService {
    

    // Fetch all by user
    // Fetch single

    // Register

    // get MatchStatus
    // update MatchStatus

    // Remove Single
    // Removed Expired


    @Autowired
    private MatchRepository matchRepository;

    
    /**
     * Retieves the match status for all users in a group
     *
     *
     * @param groupID UUID of the group
     * @return List<MatchStatus> objects representing each users' status
     * @throws GroupNotFoundException if no user with id {userID} is found
     */
    public List<MatchStatus> getMatchStatus(UUID groupID) {

        // get all matches in the system for a given group ID
        Iterable<MatchEntity> matchEntities = matchRepository.findByGroupID(groupID);

        // if list is empty, throw exception
        if (!matchEntities.iterator().hasNext()) {
            throw new GroupNotFoundException();
        }

        // create blank matchstatus list
        List<MatchStatus> groupStatus = new ArrayList<MatchStatus>();

        for (MatchEntity matchEntity : matchEntities) {

            // create a match Status object for a single user
            MatchStatus matchStatus = new MatchStatus(matchEntity.getUserID(), matchEntity.getInviteStatus());

            // add it to the list
            groupStatus.add(matchStatus);

        }

        // return the list
        return groupStatus;
    }

}
