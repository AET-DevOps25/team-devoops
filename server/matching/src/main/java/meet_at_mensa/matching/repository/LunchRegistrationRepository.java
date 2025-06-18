package meet_at_mensa.matching.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import meet_at_mensa.matching.entity.LunchRegistration;

public interface LunchRegistrationRepository extends JpaRepository<LunchRegistration, Long> {
    List<LunchRegistration> findByTimeSlotAndMatchedFalse(LocalTime timeSlot);
}
