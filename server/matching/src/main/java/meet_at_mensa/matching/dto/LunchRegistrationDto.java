package meet_at_mensa.matching.dto;

import java.util.Set;

public class LunchRegistrationDto {
    public String userId;
    public String email;
    public String timeSlot; // e.g. "11:30"
    public String mensa;
    public String degree;
    public Integer age;
    public Set<String> preferences;
}
