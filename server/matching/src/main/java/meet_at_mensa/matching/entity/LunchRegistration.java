package meet_at_mensa.matching.entity;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LunchRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String email;
    private LocalTime timeSlot;
    private String mensa;
    private String degree;
    private Integer age;
    private boolean matched = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> preferences = new HashSet<>();

    // Getters, setters, constructor
}
