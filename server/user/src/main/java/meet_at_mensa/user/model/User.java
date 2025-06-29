package meet_at_mensa.user.model;

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

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "gender")
    private String gender;

    @Column(name = "degree")
    private String degree;

    @Column(name = "degree_start")
    private Integer degreeStart;

    @Column(name = "bio")
    private String bio;


    // -------
    // Getters
    // -------

    public UUID getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getDegree() {
        return degree;
    }

    public Integer getDegreeStart() {
        return degreeStart;
    }

    public String getBio() {
        return bio;
    }

    // -------
    // Setters
    // -------

    // no function to change used UUID

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setDegreeStart(Integer degreeStart) {
        this.degreeStart = degreeStart;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // ------------
    // Constructors
    // ------------

    public User() {

        // default constructor required by JPA

    }

    public User(String email, String firstname, String lastname, LocalDate birthday, String gender, String degree, Integer degreeStart, String bio) {

        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.degree = degree;
        this.degreeStart = degreeStart;
        this.bio = bio;

    }
}
