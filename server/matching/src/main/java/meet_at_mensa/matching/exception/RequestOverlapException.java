package meet_at_mensa.matching.exception;

public class RequestOverlapException extends RuntimeException {
    
    public RequestOverlapException() {
        super("A request already exists for this user on this date");
    }

    public RequestOverlapException(String message) {
        super(message);
    }

    public RequestOverlapException(String message, Throwable cause) {
        super(message, cause);
    }

}
