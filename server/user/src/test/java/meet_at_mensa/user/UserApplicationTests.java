package meet_at_mensa.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
// UserApplicationTests is a test class for the user application
class UserApplicationTests {

	@Container
    static MySQLContainer<?> userdb = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("userdb")
        .withInitScript("init_userdb_test.sql")
        .withUsername("root")
        .withPassword("root");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", userdb::getJdbcUrl);
        registry.add("spring.datasource.username", userdb::getUsername);
        registry.add("spring.datasource.password", userdb::getPassword);
    }

	@Test
	void contextLoads() {
	}

}
