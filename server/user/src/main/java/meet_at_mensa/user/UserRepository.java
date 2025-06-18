package meet_at_mensa.user;

// import CRUD repository (Create/Read/Update/Delete)
import org.springframework.data.repository.CrudRepository;

// Interface UserRepository represents the userdb table
public interface UserRepository extends CrudRepository<User, Integer> {
    
    // This is auto-implemented by springboot into a userRepository Bean

}