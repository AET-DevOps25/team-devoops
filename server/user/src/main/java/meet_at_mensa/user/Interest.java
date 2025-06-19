package meet_at_mensa.user;

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
@Table(name = "user_interests")
public class Interest {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL userdb/interests table)
    // ----------

    @Id // This value is the User's UUID
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "list_id")
    private UUID listID;

    @Column(name = "user_id")
    private UUID userID;
    
    @Column(name = "interest")
    private String interest;

    // -------
    // Getters
    // -------

    public UUID getListID() {
        return listID;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getInterest() {
        return interest;
    }

    // ------------
    // Constructors
    // ------------

    public Interest() {

        // default constructor required by JPA

    }

    public Interest(UUID userID, String interest) {

        this.userID = userID;
        this.interest = interest;

    }
}
