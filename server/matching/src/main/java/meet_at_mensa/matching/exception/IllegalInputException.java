package meet_at_mensa.matching.exception;

public class IllegalInputException extends RuntimeException {
    
    public IllegalInputException() {
        super("Group not found");
    }

    public IllegalInputException(String message) {
        super(message);
    }

    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
