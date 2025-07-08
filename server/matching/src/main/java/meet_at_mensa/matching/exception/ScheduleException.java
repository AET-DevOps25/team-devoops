package meet_at_mensa.matching.exception;

public class ScheduleException extends RuntimeException {
    
    public ScheduleException() {
        super("This operation cannot be run at this time!");
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
    
}