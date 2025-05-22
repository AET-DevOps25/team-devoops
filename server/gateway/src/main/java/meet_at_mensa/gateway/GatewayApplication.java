package meet_at_mensa.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
// GatewayApplication is the main class for the gateway application
public class GatewayApplication {

    // The main method is the entry point of the gateway service
    // It starts the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

    @RestController
    // HelloController is a REST controller that handles HTTP GET requests
    // It returns a greeting message in plain text at root path
    static class HelloController {
        @GetMapping("/")
        public String hello() {
            return "Hello World! Welcome to the Gateway Service!";
        }
    }
}
