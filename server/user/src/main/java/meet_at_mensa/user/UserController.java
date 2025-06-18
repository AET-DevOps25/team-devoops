package meet_at_mensa.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserController {
    
    // Object representing the userdb database
    @Autowired // This is auto-implemented by spring
    private UserRepository userRepository;

    // Responds to get requests on /users/all with a list of all users
    @GetMapping(path="/debug/users/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create a new user when provided with json formatted data
    @PostMapping(path="/api/users/new")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

}
