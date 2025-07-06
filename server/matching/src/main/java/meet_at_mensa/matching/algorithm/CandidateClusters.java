package meet_at_mensa.matching.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CandidateClusters {

    private Map<Integer, List<Candidate>> clusters;

    CandidateClusters(List<Candidate> candidates){

        clusters = new HashMap<>();

        // initialize clusters
        for (int i = 1; i < 17; i++) {
            clusters.put(i, new ArrayList<Candidate>());
        }

        // add candidates to clusters
        for (Candidate candidate : candidates) {
            
            // add user to each cluster they are available for
            for (Integer startTime : candidate.getStartingTimes()) {
                clusters.get(startTime).add(candidate);
            }
        }
    }

    public List<Candidate> getCluster(Integer startTime) {

        return clusters.get(startTime);

    }

    
    public Map<Integer, Integer> candidatesPerCluster(){

        // initialize return object
        Map<Integer, Integer> candidatesPerCluster = new HashMap<>();

        // initialize clusters
        for (int i = 1; i < 17; i++) {
            candidatesPerCluster.put(i, clusters.get(i).size());
        }

        // return objects
        return candidatesPerCluster;

    }

    public Boolean candidateInCluster(UUID userID, Integer startTime) {

        List<UUID> userIDs = getCluster(startTime).stream()
            .map(Candidate::getUserID)
            .collect(Collectors.toList());

        return userIDs.contains(userID);

    }

    public List<Integer> clustersWithCandidate(UUID userID) {

        List<Integer> clustersWithCandidate = new ArrayList<>();

        for (int i = 1; i < 17; i++) {
            
            if(candidateInCluster(userID, i)){
                clustersWithCandidate.add(i);
            }

        }

        return clustersWithCandidate;

    }



    public Boolean removeEntry(UUID userID, Integer clusterID) {

        for (Candidate candidate : clusters.get(clusterID)) {

            if (candidate.getUserID() == userID) {

                clusters.get(clusterID).remove(candidate);
                return true;
                
            }
        }

        return false;

    }

    public List<UUID> removeCluster(Integer clusterID) {

        List<UUID> removedUsers = new ArrayList<>();

        for (Candidate candidate : clusters.get(clusterID)) {

            // remove entry and add the user ID to the list of removed users
            if (removeEntry(candidate.getUserID(), clusterID)) {
                removedUsers.add(candidate.getUserID());
            }

        }

        return removedUsers;

    }

}
