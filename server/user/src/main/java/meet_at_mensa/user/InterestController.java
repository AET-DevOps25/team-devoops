package meet_at_mensa.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.UUID;
import java.util.Map;

@RestController
public class InterestController {
    
    // Object representing the userdb database
    @Autowired // This is auto-implemented by spring
    private InterestRepository interestRepository;

    // debug function to show entire database
    @GetMapping(path="/debug/users/interests/all_entries")
    public Iterable<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    // Get all interests for a given user
    @PostMapping(path="/api/users/interests/by_user_id")
    public Iterable<Interest> getUserInterests(@RequestBody Map<String, String> payload) {
        UUID userID = UUID.fromString(payload.get("userID"));
        return interestRepository.findByUserID(userID);
    }

    // Awaits an interest .json {userID: UUID userID, interest: String interest}
    @PostMapping(path="/api/users/interests/add")
    public Interest addInterest(@RequestBody Interest interest) {
        return interestRepository.save(interest);
    }
    // Example:
        // curl -H "Content-Type: application/json" --request POST -d '{"userID": "946f3f46-28de-4b6a-9010-1c5c1ed56af3", "interest": "cats"}' 127.0.0.1:8083/api/users/interests/add

}
