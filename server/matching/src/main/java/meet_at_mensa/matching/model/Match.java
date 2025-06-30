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

// import from openapi generated
import org.openapitools.model.InviteStatus;

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
    @Column(name = "invite_status")
    private InviteStatus inviteStatus;

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
    
    public InviteStatus getRsvp() {
        return inviteStatus;
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

    public void setInviteStatus(InviteStatus inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    // ------------
    // Constructors
    // ------------

    public Match() {

        // default constructor required by JPA

    }

    public Match(UUID userID, UUID groupID, InviteStatus inviteStatus) {

        this.userID = userID;
        this.groupID = groupID;
        this.inviteStatus = inviteStatus;

    }

    // ------------
    // Others
    // ------------


}
