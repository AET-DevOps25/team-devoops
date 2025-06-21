package meet_at_mensa.user;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

// Interface UserRepository represents the userdb table
public interface UserRepository extends CrudRepository<User, UUID> {
    
    // This is auto-implemented by springboot into a userRepository Bean

    // We only ever need to get users by the UUID, so no further functions need to be defined here

}