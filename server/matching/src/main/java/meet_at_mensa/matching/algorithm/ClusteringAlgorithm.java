package meet_at_mensa.matching.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
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

        // TODO: add separate runs per Mensa

        List<CandidateGroup> candidateGroups = new ArrayList<>();

        // Step 1 - Eliminate all dead clusters with fewer than 2 users
        eliminateDeadClusters();

        while (stillSolveable()) {
            
            // Step 2 - Select a "Critical User (least availability)"
            Candidate criticalCandidate = determineCriticalCandidate();

            // Step 3 - Create a "minimal-group" around the critical user. Remove users from candidates list
            CandidateGroup idealGroup = determineIdealGroup(criticalCandidate.getUserID());

            // Step 3.1 - Add group to list
            candidateGroups.add(idealGroup);

            // Step 4 - Remove users in the candidateGroup from clusters
            for (Candidate candidate : idealGroup.getMembers()) {
                
                // move users to matched
                userMatched(candidate.getUserID());

                // removed entries from clusters
                clusters.removeUser(candidate.getUserID());

            }

            // Step 5 - Eliminate all dead clusters with fewer than 2 users
            eliminateDeadClusters();

            // Repeat until no more matches are possible

        }

        // Step 6 - Attempt to fit unmatcheable users into existing groups

        fitAllUnmatchable(candidateGroups);

        // Step 7 - Convert to MatchingSolutionBlocks and return

        List<MatchingSolutionBlock> solutionBlocks = convertToSolutionBlocks(candidateGroups, unmatchableFinal);

        return new MatchingSolution(solutionBlocks);

    }


    private void eliminateDeadClusters() {

        // Get the number of users within a each cluster ("Viability")
        Map<Integer, Integer> viability = clusters.candidatesPerCluster();

        // Iterate over each cluster
        for (int i = 1; i < 17; i++) {

            // If a cluster already has 0 viability, skip it
            if (viability.get(i) == 0) {

                continue;

            // If a cluster has less than 2 viability, remove all entries in it
            } else if (viability.get(i) <= 2 ) {

                // remove all entries in the cluster and return the userID of candidates whos entry got removed
                List<UUID> cutCandidateUUIDs = clusters.removeCluster(i);

                // check if any of the removed users are now unmatchable
                // if they are, move them to the unmatchable list
                registerUnmatchable(cutCandidateUUIDs);

            }

        }

    }

    public List<UUID> registerUnmatchable(List<UUID> candidateIDs) {

        // Init return value
        List<UUID> removed = new ArrayList<>();

        for (UUID candidateID : candidateIDs) {
            
            // if a candidate is not matchable
            if (!candidateMatchable(candidateID)){

                // move it to this.unmatchable
                userUnmatchable(candidateID);

                // add it to return value
                removed.add(candidateID);

            }

        }

        // return list with all candidates found to be unmatchable
        return removed;

    }

    public boolean candidateMatchable(UUID candidateID){

        // Check if the candidate is still in any clusters
        if(clusters.clustersWithCandidate(candidateID).size() == 0) {

            // returns false if candidate is in no clusters (and therefore unmatchable)
            return false;

        } else {

            // returns true if candidate is still in clusters (and therefore still matchable)
            return true;

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
            List<List<Candidate>> combinations = getCandidateCombinationsWith(clusters.getCluster(startTime), critUserID);

            // create groups for all these combinations
            for (List<Candidate> combination : combinations) {
                
                candidateGroups.add(new CandidateGroup(combination, startTime));

            }

        } 

        CandidateGroup ideal = null;
        Float maxQuality = 0f;

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


    private void fitAllUnmatchable(List<CandidateGroup> candidateGroups) {

        List<UUID> success = new ArrayList<>();
        List<UUID> failure = new ArrayList<>();

        for (Candidate unmatchableCandidate : unmatchable) {    

            if (fitUnmatchable(candidateGroups, unmatchableCandidate)) {

                success.add(unmatchableCandidate.getUserID());

            } else {

                failure.add(unmatchableCandidate.getUserID());

            }

        }

        for (UUID failureID : failure) {
            
            userUnmatchableFinal(failureID);

        }

        for (UUID successID: success) {

            userDochMatchable(successID);

        }

    }

    private Boolean fitUnmatchable(List<CandidateGroup> groups, Candidate candidate) {

        // get empty list of potential groups
        List<CandidateGroup> potentialGroups = new ArrayList<>();
        List<CandidateGroup> replacesGroups = new ArrayList<>();

        // Check for any groups that the user may fit into
        for (CandidateGroup group : groups) {
            
            // check if user shares a timeslot with group
            if (candidate.getStartingTimes().contains(group.getTimeslot())) {

                // create a new group with added member
                potentialGroups.add(group.addMember(candidate));

                // remember the group it replaces
                replacesGroups.add(group);

            }

        }

        // if the user fits into at least one group
        if (potentialGroups.iterator().hasNext()) {

            CandidateGroup bestGroup = null;
            CandidateGroup replacesGroup = null;
            Float bestQuality = 0f;

            // for each group
            for (int i = 0; i < potentialGroups.size(); i++) {

                // if this is the first run
                if(bestGroup == null) {

                    bestGroup = potentialGroups.get(i);
                    replacesGroup = replacesGroups.get(i);
                    bestQuality = bestGroup.getQuality();
                    continue;

                }
                
                // if the quality is better than the current best
                if(potentialGroups.get(i).getQuality() > bestQuality) {

                    bestGroup = potentialGroups.get(i);
                    replacesGroup = replacesGroups.get(i);
                    bestQuality = bestGroup.getQuality();

                }

            }

                // remove replaced group
                groups.remove(replacesGroup);

                // add new group
                groups.add(bestGroup);

                // returns true if user was fit
                return true;


        // if user does not fit into any groups
        } else {

            // returns false if user was not fit
            return false;

        }

    }

    private List<MatchingSolutionBlock> convertToSolutionBlocks(List<CandidateGroup> groups, List<Candidate> unmatchables) {
        
        List<MatchingSolutionBlock> solutionBlocks = new ArrayList<>();

        // add all matched groups
        for (CandidateGroup candidateGroup : groups) {
            
            // add each group to solution blocks
            solutionBlocks.add(
                candidateGroup.toSolutionBlock(RequestStatus.MATCHED)
            );

        }

        // add unmatchable solution block
        CandidateGroup unmatchableGroup = new CandidateGroup(unmatchableFinal, 0);
        solutionBlocks.add(unmatchableGroup.toSolutionBlock(RequestStatus.UNMATCHABLE));

        return solutionBlocks;

    }
}


