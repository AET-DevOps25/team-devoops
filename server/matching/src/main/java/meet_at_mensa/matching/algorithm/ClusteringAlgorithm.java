package meet_at_mensa.matching.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.UserCollection;

public class ClusteringAlgorithm extends MatchingAlgorithm {
    
    private CandidateClusters clusters;

    public ClusteringAlgorithm(UserCollection users, MatchRequestCollection requests){
        
        // call parent constructor
        super(users, requests);

        // add candidates to clusters
        this.clusters = new CandidateClusters(unmatched);

    }



    @Override
    public MatchingSolution generateSolution() {

        // Step 1 - Eliminate all dead clusters with fewer than 2 users
        eliminateDeadClusters();
        
        // Step 2 - Select a "Critical User (least availability)"
        determineCriticalCandidate();

        // Step 3 - Create a "minimal-group" around the critical user. Remove users from candidates list
            // Step 3.1 Create all possible minimal-groups for critical-user
        // Step 4 - Repeat until all minimal groups exist
        // Step 5 - Attempt to fit unmatcheable users into existing groups

        return null;

    }


    private void eliminateDeadClusters() {

        Map<Integer, Integer> viability = clusters.candidatesPerCluster();

        for (int i = 0; i < 17; i++) {
            
            // if the timeslot has no viable groups and hasn't been removed
            if (viability.get(i) <= 2 && viability.get(i) != 0) {

                // remove all entries in the cluster and return the userID of candidates whos entry got removed
                List<UUID> cutIDs = clusters.removeCluster(i);

                // check if any of the removed users are now unmatchable
                for (UUID cutID : cutIDs) {
                    
                    checkCandidateViability(cutID);

                }

            }

        }

    }

    private void checkCandidateViability(UUID userID) {

        if(clusters.clustersWithCandidate(userID).size() == 0) {

            // if user is no longer in any cluster, set it as unmatchable
            userUnmatchable(userID);

            System.out.println("User is unmatchable" + userID.toString());

        }

    }

    private Candidate determineCriticalCandidate() {

        Candidate mostCritical = null;
        Integer leastAvailablity = 16;

        for (Candidate candidate : unmatched) {

            Integer availability = clusters
                                    .clustersWithCandidate(candidate.getUserID())
                                    .size();
            

            // if this is the first candidate
            if (mostCritical == null) {

                mostCritical = candidate;
                leastAvailablity = availability;
                continue;

            // if the current candidate has more critical availabilty
            } else if (availability < leastAvailablity) {

                mostCritical = candidate;
                leastAvailablity = availability;
                continue;

            // if the current candiate has the same availability
            } else if (availability == leastAvailablity) {

                // prioritize the most flexible candidate
                if(candidate.getFlexibility() > mostCritical.getFlexibility()) {

                    mostCritical = candidate;
                    leastAvailablity = availability;
                    continue;

                }

            }

        }

        return mostCritical;

    }

    private CandidateGroup determineIdealGroup(UUID critUserID) {

        // create empty list of possible groups
        List<CandidateGroup> candidateGroups = new ArrayList<>();

        // for each start window
        for (Integer startTime : clusters.clustersWithCandidate(critUserID)) {
            
            // get all possible combinations of users
            List<List<Candidate>> combinations = getCandidateCombinationsWith(matched, critUserID);

            // create groups for all these combinations
            for (List<Candidate> combination : combinations) {
                
                candidateGroups.add(new CandidateGroup(combination, startTime));

            }

        } 

        CandidateGroup ideal = null;
        Integer maxQuality = 0;

        for (CandidateGroup candidateGroup : candidateGroups) {
            
            // if this is the first run
            if(ideal == null ) {

                ideal = candidateGroup;
                maxQuality = candidateGroup.getQuality();

            // if the current quality is higher than the previous max
            } else if (maxQuality < candidateGroup.getQuality()) {

                ideal = candidateGroup;
                maxQuality = candidateGroup.getQuality();

            }

        }

        
        return ideal;

    }

    private List<List<Candidate>> getCandidateCombinationsWith(List<Candidate> candidates, UUID userID) {

        List<List<Candidate>> combinations = new ArrayList<>();

        Integer n = candidates.size();

        // get all 3-candidate combinations
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {

                    // add to result
                    List<Candidate> combination = Arrays.asList(candidates.get(i), candidates.get(j), candidates.get(k));
                    
                    // if the user is in this combination
                    if(
                        combination
                            .stream()
                            .map(Candidate::getUserID)
                            .collect(Collectors.toSet())
                            .contains(userID)
                    ) {
                        combinations.add(combination);
                    }

                }
            }
        }

        return combinations;

    }

    private Boolean stillSolveable() {

        return (Collections.max(clusters.candidatesPerCluster().values()) >= 3);

    }
}


