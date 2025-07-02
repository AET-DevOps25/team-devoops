package meet_at_mensa.matching.exception;

public class GroupNotFoundException extends RuntimeException {
    
    public GroupNotFoundException() {
        super("Group not found");
    }

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
