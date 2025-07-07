package meet_at_mensa.user.model;

// import persistence tags
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

// Class User represents a single entry (row) in the userdb/users table
@Entity
@Table(name = "user_identity")
public class IdentityEntity {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL userdb/interests table)
    // ----------

    @Id // This value is the unique table ID
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "pair_id")
    private UUID pair_id;

    @Column(name = "user_id")
    private UUID userID;
    
    @Column(name = "auth_id")
    private String authID;

    // -------
    // Getters
    // -------

    public UUID getUserID() {
        return userID;
    }

    public String getAuthID() {
        return authID;
    }

    // ------------
    // Constructors
    // ------------

    public IdentityEntity() {

        // default constructor required by JPA

    }

    public IdentityEntity(UUID userID, String authID) {

        this.userID = userID;
        this.authID = authID;

    }
}

