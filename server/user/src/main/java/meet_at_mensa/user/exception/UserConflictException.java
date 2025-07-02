package meet_at_mensa.user.exception;

public class UserConflictException extends RuntimeException {
    
    public UserConflictException() {
        super("User with this email already exists!");
    }

    public UserConflictException(String message) {
        super(message);
    }

    public UserConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
