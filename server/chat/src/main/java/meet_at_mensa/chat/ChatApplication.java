package meet_at_mensa.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
// ChatApplication is the main class for the chat application
public class ChatApplication {

    // The main method is the entry point of the chat service
    // It starts the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@RestController
    // HelloController is a REST controller that handles HTTP GET requests
    // It returns a greeting message in plain text
    class HelloController {
        @GetMapping({"/", "/chat"})
        public String hello() {
            return "Hello World! Welcome to the Chat Service!";
        }
    }
}
