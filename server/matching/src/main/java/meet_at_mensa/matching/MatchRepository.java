package meet_at_mensa.matching;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

// Interface MatchRepository represents the matchdb/matches table
public interface MatchRepository extends CrudRepository<Match, UUID> {
    
    // This is auto-implemented by springboot into a Repository Bean

    // In order to be able to get all values for a given user ID, we define this custom query
    // Custom Queries as described here: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // Find by userID
    Iterable<Match> findByUserID(UUID userID);

    // Find by groupID
    Iterable<Match> findByGroupID(UUID groupID);

    // Find by RSVP status
    Iterable<Match> findByRsvp(Boolean rsvp);
}