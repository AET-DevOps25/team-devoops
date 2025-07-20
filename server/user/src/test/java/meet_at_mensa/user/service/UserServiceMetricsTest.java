package meet_at_mensa.user.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.UserNew;

import meet_at_mensa.user.repository.UserRepository;
import meet_at_mensa.user.repository.InterestRepository;
import meet_at_mensa.user.repository.IdentityRepository;
import meet_at_mensa.user.exception.UserMalformedException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceMetricsTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private IdentityRepository identityRepository;

    private MeterRegistry meterRegistry;
    private UserService userService;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        userService = new UserService(meterRegistry);
        
        // Inject mocked repositories using reflection for testing
        try {
            java.lang.reflect.Field userRepoField = UserService.class.getDeclaredField("userRepository");
            userRepoField.setAccessible(true);
            userRepoField.set(userService, userRepository);

            java.lang.reflect.Field interestRepoField = UserService.class.getDeclaredField("interestRepository");
            interestRepoField.setAccessible(true);
            interestRepoField.set(userService, interestRepository);

            java.lang.reflect.Field identityRepoField = UserService.class.getDeclaredField("identityRepository");
            identityRepoField.setAccessible(true);
            identityRepoField.set(userService, identityRepository);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocked repositories", e);
        }
    }

    @Test
    void testUsersCreatedCounterIncrements() {
        // Given
        UserNew userNew = createValidUserNew();
        meet_at_mensa.user.model.UserEntity mockUserEntity = createMockUserEntity();
        when(userRepository.save(any())).thenReturn(mockUserEntity);
        when(identityRepository.save(any())).thenReturn(createMockIdentityEntity());
        when(userRepository.findById(mockUserEntity.getUserID())).thenReturn(java.util.Optional.of(mockUserEntity));
        when(interestRepository.findByUserID(mockUserEntity.getUserID())).thenReturn(new ArrayList<>());

        // When
        double initialCount = userService.getUsersCreatedCount();
        userService.registerUser(userNew);
        double finalCount = userService.getUsersCreatedCount();

        // Then
        assertEquals(1.0, finalCount - initialCount, "User creation counter should increment by 1");
    }



    @Test
    void testMetricsAreRegistered() {
        // Verify that the metrics are properly registered in the meter registry
        assertNotNull(meterRegistry.find("users_created_total").counter());
    }

    private UserNew createValidUserNew() {
        UserNew userNew = new UserNew();
        userNew.setEmail("test@example.com");
        userNew.setFirstname("John");
        userNew.setLastname("Doe");
        userNew.setBirthday(LocalDate.of(1990, 1, 1));
        userNew.setGender("MALE");
        userNew.setDegree("Computer Science");
        userNew.setDegreeStart(2020);
        userNew.setBio("Test bio");
        userNew.setInterests(Arrays.asList("Programming", "Reading"));
        userNew.setAuthID("auth0|123456789");
        return userNew;
    }

    private meet_at_mensa.user.model.UserEntity createMockUserEntity() {
        meet_at_mensa.user.model.UserEntity entity = new meet_at_mensa.user.model.UserEntity();
        // Set required fields using reflection since they might be final
        try {
            java.lang.reflect.Field idField = meet_at_mensa.user.model.UserEntity.class.getDeclaredField("userID");
            idField.setAccessible(true);
            idField.set(entity, UUID.randomUUID());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set user ID", e);
        }
        return entity;
    }

    private meet_at_mensa.user.model.IdentityEntity createMockIdentityEntity() {
        return new meet_at_mensa.user.model.IdentityEntity(UUID.randomUUID(), "auth0|123456789");
    }
}