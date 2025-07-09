package meet_at_mensa.matching.algorithm;

import java.time.LocalDate;

import org.openapitools.model.Location;
import org.openapitools.model.MatchRequestCollection;
import org.openapitools.model.RequestStatus;
import org.openapitools.model.UserCollection;

public class MatchingSolutionBlock {
    
    private UserCollection users;
    private MatchRequestCollection requests;
    private LocalDate date;
    private Integer time;
    private Location location;
    private RequestStatus status;

    public MatchingSolutionBlock(UserCollection users, MatchRequestCollection requests, LocalDate date, Integer time, Location location, RequestStatus status) {

        this.users = users;
        this.requests = requests;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;

    }

    public UserCollection getUsers() {
        return users;
    }

    public MatchRequestCollection getRequests() {
        return requests;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setUsers(UserCollection users) {
        this.users = users;
    }

    public void setRequests(MatchRequestCollection requests) {
        this.requests = requests;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

}
