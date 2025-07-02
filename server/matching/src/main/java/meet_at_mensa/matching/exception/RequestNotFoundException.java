package meet_at_mensa.matching.exception;

public class RequestNotFoundException extends RuntimeException {
    
    public RequestNotFoundException() {
        super("Match Request not found");
    }

    public RequestNotFoundException(String message) {
        super(message);
    }

    public RequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
