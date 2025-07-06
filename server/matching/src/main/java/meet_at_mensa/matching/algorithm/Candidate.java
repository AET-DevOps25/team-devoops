package meet_at_mensa.matching.algorithm;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openapitools.model.Location;
import org.openapitools.model.MatchRequest;
import org.openapitools.model.User;

public class Candidate {
    
    private User user;
    private MatchRequest request;

    public Candidate(User user, MatchRequest request) {

        this.user = user;
        this.request = request;

    }

    // Core Objects

    public User getUser() {
        return this.user;
    }

    public MatchRequest getRequest() {
        return this.request;
    }

    // IDs

    public UUID getUserID() {
        return this.user.getUserID();
    }

    public UUID getRequestID() {
        return this.request.getRequestID();
    }

    // Times and Dates

    public LocalDate getDate() {
        return this.request.getDate();
    }

    public Location getLocation() {
        return this.request.getLocation();
    }

    public List<Integer> getTimeslots() {
        return this.request.getTimeslot();
    }

    public List<Integer> getStartingTimes() {

        // Create an empty list of integers
        List<Integer> startingTimes = new ArrayList<>();

        // Get the list of timeslots a user is avalabli
        List<Integer> timeslots = getTimeslots();

        for (Integer timeslot : timeslots) {
            
            // Check if user is available for 45 minutes starting at the first timeslot
            if(timeslots.contains(timeslot + 1) && (timeslots.contains(timeslot + 2))){

                // if the user is availbable for 45 minutes starting at timeslot x (so x, x+1 and x+2)
                // then add it to the list of starting times.
                startingTimes.add(timeslot);

            } 

        }

        return startingTimes;

    }

    // Preferences and Characteristics

    public Integer getFlexibility() {

        Integer flexibility = 0;

        if (!getAgePref()) {flexibility++;}
        if (!getGenderPref()) {flexibility++;}
        if (!getDegreePref()) {flexibility++;}

        return flexibility;
    }

    public Boolean getGenderPref() {
        return this.request.getPreferences().getGenderPref();
    }

    public String getGender() {
        return this.user.getGender();
    }

    public Boolean getDegreePref() {
        return this.request.getPreferences().getDegreePref();
    }

    public String getDegree() {
        return this.user.getDegree();
    }

    public Boolean getAgePref() {
        return this.request.getPreferences().getAgePref();
    }

    public Integer getAge() {
        return Period.between(this.user.getBirthday(),LocalDate.now())
            .getYears();
    }

    



}
