package meet_at_mensa.user.exception;

public class UserMalformedException extends RuntimeException {
    
    public UserMalformedException() {
        super("Invalid values given for user");
    }

    public UserMalformedException(String message) {
        super(message);
    }

    public UserMalformedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}