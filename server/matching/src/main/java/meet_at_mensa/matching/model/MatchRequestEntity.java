package meet_at_mensa.matching.model;

// import persistence tags
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// import utils
import java.util.UUID;

// import from openapi spec
import org.openapitools.model.RequestStatus;

import java.time.LocalDate;

// Class MatchRequest represents a single entry (row) in the matchdb/match_requests table
@Entity
@Table(name = "match_requests")
public class MatchRequestEntity {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL matchdb/match_requests table)
    // ----------

    // Auto-generated unique ID for this Match request
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "request_id")
    private UUID requestID;
    
    // ID of the user who made the match request
    @Column(name = "user_id")
    private UUID userID;

    // date for the match
    @Column(name = "request_date")
    private LocalDate date;

    // date for the match
    @Column(name = "request_location")
    private String location;

    // Whether to prioritize students with the same degree
    @Column(name = "degree_pref")
    private Boolean degreePref;

    // Whether to prioritize students with the same age
    @Column(name = "age_pref")
    private Boolean agePref;

    // Whether to prioritize students with the same gender
    @Column(name = "gender_pref")
    private Boolean genderPref;

    @Column(name = "request_status")
    private RequestStatus requestStatus;

    // -------
    // Getters
    // -------

    public UUID getRequestID() {
        return requestID;
    }
    
    public UUID getUserID() {
        return userID;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
    
    public Boolean getDegreePref() {
        return degreePref;
    }
    
    public Boolean getAgePref() {
        return agePref;
    }
    
    public Boolean getGenderPref() {
        return genderPref;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    // -------
    // Setters
    // -------

    // no setters for
    // - RequestID
    // - UserID

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDegreePref(Boolean degreePref) {
        this.degreePref = degreePref;
    }

    public void setAgePref(Boolean agePref) {
        this.agePref = agePref;
    }

    public void setGenderPref(Boolean genderPref) {
        this.genderPref = genderPref;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    // ------------
    // Constructors
    // ------------

    public MatchRequestEntity() {

        // default constructor required by JPA

    }

    // Note: Constructor doesn't contain group ID since it's filled only when matching is fullfilled!
    public MatchRequestEntity(UUID userID, LocalDate date, String location, Boolean degreePref, Boolean agePref, Boolean genderPref) {

        this.userID = userID;
        this.date = date;
        this.location = location;
        this.degreePref = degreePref;
        this.agePref = agePref;
        this.genderPref = genderPref;

        // all new requests are created with the pending status
        this.requestStatus = RequestStatus.PENDING;

    }

    // ------------
    // Others
    // ------------

    // whether or not this request has been fulfilled
    public Boolean isMatched() {
        
        return (this.requestStatus == RequestStatus.MATCHED);

    }

    // returns true if this match requests' date is in the past
    public Boolean isOutdated() {
        return (this.date.isBefore(LocalDate.now()));
    }

}
