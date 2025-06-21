package meet_at_mensa.matching;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;
import java.time.LocalDate;

// Interface MatchRequsetRepository represents the matchdb/match_requests table
public interface MatchRequestRepository extends CrudRepository<MatchRequest, UUID> {
    
    // This is auto-implemented by springboot into a Repository Bean

    // In order to be able to get all values for a given user ID, we define this custom query
    // Custom Queries as described here: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // Find all matches on the same date
    Iterable<MatchRequest> findByDate(LocalDate date);
}
