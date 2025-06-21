package meet_at_mensa.user;

// import persistence tags
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import java.time.LocalDate;

// Class User represents a single entry (row) in the userdb/users table
@Entity
@Table(name = "user_data")
public class User {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL userdb/users table)
    // ----------

    @Id // This value is the User's UUID
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userID;
    
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "degree")
    private String degree;

    @Column(name = "birthday")
    private LocalDate birthday;

    // -------
    // Getters
    // -------

    public UUID getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getDegree() {
        return degree;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    // -------
    // Setters
    // -------

    // no function to change used UUID

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    // ------------
    // Constructors
    // ------------

    public User() {

        // default constructor required by JPA

    }

    public User(String email, String name, String gender, String degree, LocalDate birthday) {

        this.email = email;
        this.name = name;
        this.gender = gender;
        this.degree = degree;
        this.birthday = birthday;

    }
}
