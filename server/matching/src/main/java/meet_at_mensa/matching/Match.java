package meet_at_mensa.matching;

// import persistence tags
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// import utils
import java.util.UUID;

// Class Match represents a single entry (row) in the matchdb/matches table
@Entity
@Table(name = "matches")
public class Match {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL matchdb/matches table)
    // ----------

    // Auto-generated unique ID for this Match request
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "match_id")
    private UUID matchID;
    
    // ID of the user who made the match request
    @Column(name = "user_id")
    private UUID userID;

    // group ID for the match. Null if currently unmatched
    @Column(name = "group_id")
    private UUID groupID;

    // Whether to prioritize students with the same gender
    @Column(name = "rsvp")
    private Boolean rsvp;

    // -------
    // Getters
    // -------

    public UUID getMatchID() {
        return matchID;
    }
    
    public UUID getUserID() {
        return userID;
    }
    
    public UUID getGroupID() {
        return groupID;
    }
    
    public Boolean getRsvp() {
        return rsvp;
    }

    // -------
    // Setters
    // -------

    // no setters for
    // - MatchID

    public void setUserId(UUID userID) {
        this.userID = userID;
    }

    public void setGroupID(UUID groupID) {
        this.groupID = groupID;
    }

    public void setRsvp(Boolean rsvp) {
        this.rsvp = rsvp;
    }

    // ------------
    // Constructors
    // ------------

    public Match() {

        // default constructor required by JPA

    }

    public Match(UUID userID, UUID groupID, Boolean rsvp) {

        this.userID = userID;
        this.groupID = groupID;
        this.rsvp = rsvp;

    }

    // ------------
    // Others
    // ------------


}
