package meet_at_mensa.matching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
// MatchingApplication is the main class for the matching application
public class MatchingApplication {

    // The main method is the entry point of the matching service
    // It starts the Spring Boot application
	public static void main(String[] args) {

        wait_for_database_startup();

		SpringApplication.run(MatchingApplication.class, args);
	}

    // Wait 30s to ensure that databases have started
    private static void wait_for_database_startup() {

        System.out.println("Waiting for databases to start...");

        try {

            TimeUnit.SECONDS.sleep(30);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
            System.out.println("Wait was interrupted");

        }

        System.out.println("Done waiting for databases to start");        
    }

	@RestController
    // HelloController is a REST controller that handles HTTP GET requests
    // It returns a greeting message in plain text
    class HelloController {
        @GetMapping({"/", "/matching"})
        public String hello() {
            return "Hello World! Welcome to the Matching Service!";
        }
    }
}
