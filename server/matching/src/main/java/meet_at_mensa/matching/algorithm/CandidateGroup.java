package meet_at_mensa.matching.algorithm;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.openapitools.model.Location;
import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.UserCollection;

public class CandidateGroup {
    
private List<Candidate> members;
private Integer quality;
private Integer timeslot;


public CandidateGroup(List<Candidate> members, Integer timeslot) {

    // basic values
    this.members = members;
    this.timeslot = timeslot;

    // calculate quality
    this.quality = calculateQuality();

}


public Integer GetMemberCount() {

    return members.size();

}

public Integer getQuality() {

    return quality;

}

public List<Candidate> getMembers() {

    return members;

}

public MatchingSolutionBlock toSolutionBlock(RequestStatus status) {

    // create new userCollection
    UserCollection users = new UserCollection(
        members.stream()
            .map(Candidate::getUser)
            .collect(Collectors.toList())
    );

    // Create new MatchRequestCollection
    MatchRequestCollection requests = new MatchRequestCollection(
        members.stream()
            .map(Candidate::getRequest)
            .collect(Collectors.toList())
    );

    LocalDate date = members.get(0).getDate();

    Location location = members.get(0).getLocation();

    MatchingSolutionBlock solutionBlock = new MatchingSolutionBlock(
        users,
        requests,
        date,
        timeslot,
        location,
        status
    );

    return solutionBlock;

}

private List<Candidate> getOthers(UUID userID) {

    List<Candidate> others = new ArrayList<>();

    for (Candidate candidate : members) {
        
        if(userID != candidate.getUserID()){
            others.add(candidate);
        }

    }

    return others;

}



private Integer calculateQuality() {

    Integer quality = 0;

    for (Candidate candidate : members) {

        // ---------------------
        // Check Age Preferences
        // ---------------------
        
        // if candidate has an age preference
        if (candidate.getAgePref()) {

            for (Candidate other : getOthers(candidate.getUserID())) {

                // calculate the age difference between candidates
                Integer ageDiff = Math.abs(candidate.getAge() - other.getAge());

                // if age difference is OK, add quality
                if (ageDiff >= 3) {
                    quality++;
                }
                
            }

        // if candidate has no age preference
        } else {

            // add to quality as if it was met perfectly
            quality = quality + members.size() - 1;

        }


        // ------------------------
        // Check Degree Preferences
        // ------------------------

        // if candidate has a degree preference
        if (candidate.getDegreePref()) {

            for (Candidate other : getOthers(candidate.getUserID())) {

                // if users study the same degree
                if (candidate.getDegree() == other.getDegree()) {
                    quality++;
                }
                
            }

        // if candidate has no degree preference
        } else {

            // add to quality as if it was met perfectly
            quality = quality + members.size() - 1;

        }

        // ------------------------
        // Check Gender Preferences
        // ------------------------

        // if candidate has a gender preference
        if (candidate.getGenderPref()) {

            for (Candidate other : getOthers(candidate.getUserID())) {

                // if users study the same degree
                if (candidate.getGender() == other.getGender()) {
                    quality++;
                }
                
            }

        // if candidate has no degree preference
        } else {

            // add to quality as if it was met perfectly
            quality = quality + members.size() - 1;

        }

    }

    return quality;

}


}
