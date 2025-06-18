package meet_at_mensa.matching.scheduler;

import meet_at_mensa.matching.service.MatchingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MatchingScheduler {

    private final MatchingService matchingService;

    public MatchingScheduler(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000) // every 30 minutes
    public void scheduleMatching() {
        matchingService.matchRegistrations();
    }
}
