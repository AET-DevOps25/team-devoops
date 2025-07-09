package meet_at_mensa.matching.exception;

public class MatchNotFoundException extends RuntimeException {
    
    public MatchNotFoundException() {
        super("Match not found");
    }

    public MatchNotFoundException(String message) {
        super(message);
    }

    public MatchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    

}
