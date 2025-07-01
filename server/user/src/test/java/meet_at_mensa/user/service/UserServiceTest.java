package meet_at_mensa.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.openapitools.model.User;
import org.openapitools.model.UserNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class UserServiceTest {
    
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

    @Autowired
    UserService userService;

    @Test
    void testRegisterUser() {

    }

}
