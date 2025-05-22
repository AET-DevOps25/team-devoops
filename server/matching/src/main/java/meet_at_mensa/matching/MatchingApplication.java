package meet_at_mensa.matching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
// MatchingApplication is the main class for the matching application
public class MatchingApplication {

    // The main method is the entry point of the matching application
    // It starts the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(MatchingApplication.class, args);
	}

	@RestController
    // HelloController is a REST controller that handles HTTP GET requests
    // It returns a greeting message in plain text
    class HelloController {
        @GetMapping("/")
        public String hello() {
            return "Hello World! Welcome to the Matching Service!";
        }
    }
}
