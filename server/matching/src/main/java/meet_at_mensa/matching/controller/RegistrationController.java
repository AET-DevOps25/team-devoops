package meet_at_mensa.matching.controller;

import meet_at_mensa.matching.dto.LunchRegistrationDto;
import meet_at_mensa.matching.entity.LunchRegistration;
import meet_at_mensa.matching.repository.LunchRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final LunchRegistrationRepository repository;

    public RegistrationController(LunchRegistrationRepository repository) {
        this.repository = repository;
    }

    private static final Set<String> ALLOWED_TIMES = Set.of(
        "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00"
    );

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LunchRegistrationDto dto) {
        if (!ALLOWED_TIMES.contains(dto.timeSlot)) {
            return ResponseEntity.badRequest().body("Invalid time slot");
        }

        LunchRegistration reg = new LunchRegistration();
        reg.setUserId(dto.userId);
        reg.setEmail(dto.email);
        reg.setTimeSlot(LocalTime.parse(dto.timeSlot));
        reg.setMensa(dto.mensa);
        reg.setDegree(dto.degree);
        reg.setAge(dto.age);
        reg.setPreferences(dto.preferences);

        repository.save(reg);
        return ResponseEntity.ok("Registered successfully");
    }
}
