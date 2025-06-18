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
@Table(name = "user_data")
public class User {
    
    // ----------
    // Attributes
    // (Each attribute here corresponds to one column in the MySQL userdb/users table)
    // ----------

    @Id // This value is the User's UUID
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;
    
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "degree")
    private String degree;

    @Column(name = "age")
    private Integer age;

    // -------
    // Getters
    // -------

    public UUID getId() {
        return id;
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

    public Integer getAge() {
        return age;
    }

    // ------------
    // Constructors
    // ------------

    public User() {

        // default constructor required by JPA

    }

    public User(String email, String name, String gender, String degree, Integer age) {

        this.email = email;
        this.name = name;
        this.gender = gender;
        this.degree = degree;
        this.age = age;

    }
}
