package meet_at_mensa.matching.exception;

public class RestException extends RuntimeException {
    
    public RestException() {
        super("A call to the REST-api has failed");
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }
}
