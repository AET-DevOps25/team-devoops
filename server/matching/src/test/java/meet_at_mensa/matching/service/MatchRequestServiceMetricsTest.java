package meet_at_mensa.matching.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.MatchRequestNew;
import org.openapitools.model.MatchPreferences;
import org.openapitools.model.Location;

import meet_at_mensa.matching.repository.MatchRequestRepository;
import meet_at_mensa.matching.exception.RequestOverlapException;
import meet_at_mensa.matching.model.MatchRequestEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchRequestServiceMetricsTest {

    @Mock
    private MatchRequestRepository requestRepository;

    @Mock
    private TimeslotService timeslotService;

    private MeterRegistry meterRegistry;
    private MatchRequestService matchRequestService;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        matchRequestService = new MatchRequestService(meterRegistry);
        
        // Inject mocked repositories using reflection for testing
        try {
            java.lang.reflect.Field requestRepoField = MatchRequestService.class.getDeclaredField("requestRepository");
            requestRepoField.setAccessible(true);
            requestRepoField.set(matchRequestService, requestRepository);

            java.lang.reflect.Field timeslotServiceField = MatchRequestService.class.getDeclaredField("timeslotService");
            timeslotServiceField.setAccessible(true);
            timeslotServiceField.set(matchRequestService, timeslotService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocked repositories", e);
        }
    }

    @Test
    void testMatchRequestsCreatedCounterIncrements() {
        // Given
        MatchRequestNew requestNew = createValidMatchRequestNew();
        MatchRequestEntity mockEntity = createMockMatchRequestEntity();
        when(requestRepository.save(any())).thenReturn(mockEntity);
        when(requestRepository.findByDateAndUserID(any(), any())).thenReturn(new ArrayList<>());
        when(requestRepository.findById(any())).thenReturn(java.util.Optional.of(mockEntity));

        // When
        double initialCount = matchRequestService.getMatchRequestsCreatedCount();
        matchRequestService.registerRequest(requestNew);
        double finalCount = matchRequestService.getMatchRequestsCreatedCount();

        // Then
        assertEquals(1.0, finalCount - initialCount, "Match request creation counter should increment by 1");
    }

    @Test
    void testMultipleMatchRequestsIncrement() {
        // Given
        MatchRequestNew requestNew1 = createValidMatchRequestNew();
        MatchRequestNew requestNew2 = createValidMatchRequestNew();
        MatchRequestEntity mockEntity1 = createMockMatchRequestEntity();
        MatchRequestEntity mockEntity2 = createMockMatchRequestEntity();
        
        when(requestRepository.save(any())).thenReturn(mockEntity1, mockEntity2);
        when(requestRepository.findById(any())).thenReturn(java.util.Optional.of(mockEntity1));
        when(requestRepository.findByDateAndUserID(any(), any())).thenReturn(new ArrayList<>());

        // When
        double initialCount = matchRequestService.getMatchRequestsCreatedCount();
        matchRequestService.registerRequest(requestNew1);
        matchRequestService.registerRequest(requestNew2);
        double finalCount = matchRequestService.getMatchRequestsCreatedCount();

        // Then
        assertEquals(2.0, finalCount - initialCount, "Match request counter should increment by 2");
    }

    @Test
    void testMetricsAreRegistered() {
        // Verify that the metrics are properly registered in the meter registry
        assertNotNull(meterRegistry.find("match_requests_created_total").counter());
    }

    private MatchRequestNew createValidMatchRequestNew() {
        MatchRequestNew requestNew = new MatchRequestNew();
        requestNew.setUserID(UUID.randomUUID());
        requestNew.setDate(LocalDate.now().plusDays(1));
        requestNew.setLocation(Location.GARCHING);
        requestNew.setTimeslot(Arrays.asList(1, 2, 3));
        
        MatchPreferences preferences = new MatchPreferences();
        preferences.setDegreePref(true);
        preferences.setAgePref(true);
        preferences.setGenderPref(false);
        requestNew.setPreferences(preferences);
        
        return requestNew;
    }

    private MatchRequestEntity createMockMatchRequestEntity() {
        MatchRequestEntity entity = new MatchRequestEntity();
        // Set required fields using reflection since they might be final
        try {
            java.lang.reflect.Field idField = MatchRequestEntity.class.getDeclaredField("requestID");
            idField.setAccessible(true);
            idField.set(entity, UUID.randomUUID());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set request ID", e);
        }
        return entity;
    }
} 