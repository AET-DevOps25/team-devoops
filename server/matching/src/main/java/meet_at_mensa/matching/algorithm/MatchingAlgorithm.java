package meet_at_mensa.matching.algorithm;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.UserCollection;

public class MatchingAlgorithm {
    
    private UserCollection users;
    private MatchRequestCollection requests;

    public MatchingAlgorithm(UserCollection users, MatchRequestCollection requests) {
        this.users = users;
        this.requests = requests;
    }

    public MatchingSolution generateSolution() {

        // TODO: implement matching algorithim
        return null;
        
    }


}
