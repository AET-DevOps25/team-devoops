package meet_at_mensa.user.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import meet_at_mensa.user.model.User;
import meet_at_mensa.user.repository.UserRepository;

import org.springframework.http.HttpStatus;

import java.util.UUID;
import java.util.Map;

@RestController
public class UserController {
    
    // Object representing the userdb database
    @Autowired // This is auto-implemented by spring
    private UserRepository userRepository;

    // Show all users in database
    // TODO: Remove in production
    @GetMapping(path="/debug/users/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create a new user when provided with json formatted data
    @PostMapping(path="/api/users/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Return user information
    @GetMapping(path="/api/users/get/{userID}")
    public User getUser(@PathVariable UUID userID) {
        return userRepository.findById(userID)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // Update user information
    @PostMapping(path="api/users/edit/{userID}")
    public User editUser(@PathVariable UUID userID, @RequestBody Map<String, String> update) {
        
        // find user
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Update values if present in payload
        if (update.containsKey("email")) {
            user.setEmail(update.get("email"));
        }

        if (update.containsKey("name")) {
            user.setName(update.get("name"));
        }

        if (update.containsKey("gender")) {
            user.setGender(update.get("gender"));
        }

        if (update.containsKey("degree")) {
            user.setDegree(update.get("degree"));
        }

        if (update.containsKey("birthday")) {
            // implement later
        }

        // Save to Database and Return
        return userRepository.save(user);

    }

}