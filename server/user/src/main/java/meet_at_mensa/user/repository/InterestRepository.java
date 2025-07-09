package meet_at_mensa.user.repository;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;

import meet_at_mensa.user.model.InterestEntity;

import java.util.UUID;

// Interface UserRepository represents the userdb table
public interface InterestRepository extends CrudRepository<InterestEntity, UUID> {
    
    // This is auto-implemented by springboot into a userRepository Bean

    // In order to be able to get all values for a given user ID, we define this custom query
    // Custom Queries as described here: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Iterable<InterestEntity> findByUserID(UUID userID);

}