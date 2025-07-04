package meet_at_mensa.matching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.RequestNotFoundException;

@Service
public class SchedulingService {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchRequestService requestService;


    /**
     * Sets the status of expired matches
     * 
     * Runs at 11:00, just as the Mensa opens
     *
     * This only sets the status to Expired, and does not delete the entries from the tables
     * 
     */
    @Scheduled(cron = "0 0 15 * * *", zone = "Europe/Berlin")
    public void scheduledExpiry(){

        // Expire all SENT but not responded to matches
        matchingService.expireMatches();

        // Expire all requests for TODAY or before
        requestService.expireRequests();

    }

    /**
     * Removes any expired entries from the database
     * 
     * Runs once a week on Sundays at 00:00
     *
     * 
     */
    @Scheduled(cron = "0 0 0 * * SUN", zone = "Europe/Berlin")
    public void scheduledCleanup(){

        // clean up expired groups and matches
        matchingService.cleanupExpired();

        // clean up expired requests
        requestService.cleanupExpired();

    }



    /**
     * Runs a scheduled round of matching
     * 
     * Runs the day before at 22:00, and the day of at 10:00
     *
     *
     * The 22:00 round is intended to serve as the intended matching run for a given day
     * with the 10:00 round serving to REMATCH any groups that failed a health-check
     * 
     */
    @Scheduled(cron = "0 0 22,10 * * *", zone = "Europe/Berlin")
    public void scheduledMatching(){

        // TODO: Implement

    }



    /**
     * Checks group RSVP status to see if it's dead and needs to be re-mathched
     * 
     * Runs every 10 minutes
     *
     *
     * If a group has enough rejections that it would end up with less than 3 members
     * this function will set the MatchRequestStatuses to REMATCH and dissolve the group
     * 
     */
    @Scheduled(cron = "0 0/10 * * * *", zone = "Europe/Berlin")
    public void groupHealthCheck(){

        // TODO: Implement

    }

}
