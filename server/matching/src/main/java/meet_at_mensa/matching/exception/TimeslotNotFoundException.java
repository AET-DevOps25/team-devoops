package meet_at_mensa.matching.exception;

public class TimeslotNotFoundException extends RuntimeException {
    
    public TimeslotNotFoundException() {
        super("Match Request not found");
    }

    public TimeslotNotFoundException(String message) {
        super(message);
    }

    public TimeslotNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
