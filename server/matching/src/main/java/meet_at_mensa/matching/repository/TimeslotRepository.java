package meet_at_mensa.matching.repository;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;

import meet_at_mensa.matching.model.TimeslotEntity;

import java.util.UUID;

// Interface TimeslotRepository represents the matchdb/match_timeslots table
public interface TimeslotRepository extends CrudRepository<TimeslotEntity, UUID> {
    
    // This is auto-implemented by springboot into a Repository Bean

    // In order to be able to get all values for a given user ID, we define this custom query
    // Custom Queries as described here: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // Find all matches on the same date
    Iterable<TimeslotEntity> findByRequestID(UUID requestID);
}
