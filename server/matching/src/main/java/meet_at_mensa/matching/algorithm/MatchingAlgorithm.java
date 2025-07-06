package meet_at_mensa.matching.algorithm;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.UserCollection;
import org.openapitools.model.User;

import java.util.List;
import java.util.UUID;

import org.openapitools.model.MatchRequest;


public abstract class MatchingAlgorithm {
    
    // List of Candidates
    protected List<Candidate> unmatched;
    protected List<Candidate> matched;
    protected List<Candidate> unmatchable;

    // Constructor
    public MatchingAlgorithm(UserCollection users, MatchRequestCollection requests) {

        // find the matching User-Request pair
        for (MatchRequest request : requests.getRequests()) {
            for (User user : users.getUsers()) {

                // Add matching User-Request pair to candidates
                if (request.getUserID() == user.getUserID()){
                    unmatched.add(new Candidate(user, request));
                    break;
                }
            }
        }
    }


    // Generate a matching solution for the list of candidates
    public abstract MatchingSolution generateSolution();

    protected Boolean userUnmatchable(UUID userID) {

        for (Candidate candidate : unmatched) {
            
            if (candidate.getUserID() == userID) {

                unmatchable.add(candidate);
                unmatched.remove(candidate);
                return true;

            }

        }

        return false;

    }

}
