package meet_at_mensa.matching.service;

import org.springframework.stereotype.Service;

import meet_at_mensa.matching.repository.LunchRegistrationRepository;


@Service
public class MatchingService {

    private final LunchRegistrationRepository repository;

    public MatchingService(LunchRegistrationRepository repository) {
        this.repository = repository;
    }

    public void matchRegistrations() {
        // TODO
    }
}