package meet_at_mensa.matching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import meet_at_mensa.matching.repository.MatchRequestRepository;
import meet_at_mensa.matching.model.MatchRequest;
import meet_at_mensa.matching.service.RequestService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Random;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired MatchRequestRepository requestRepository;
    
    @GetMapping("/debug/matching/requests/all")
    public Iterable<MatchRequest> getAllMatchRequests() {
        return requestRepository.findAll();
    }

    @GetMapping("/debug/matching/requests/add-dummy")
    public void createDummyRequest() {
        
        Random rand = new Random();

        List<String> locations = Arrays.asList("garching", "arcisstr");

        // data to use
        UUID userID = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);
        String location = locations.get(rand.nextInt(locations.size()));
        Boolean degreePref = rand.nextBoolean();
        Boolean agePref = rand.nextBoolean();
        Boolean genderPref = rand.nextBoolean();

        // create a new match request
        MatchRequest request = new MatchRequest(
            userID,
            date,
            location,
            degreePref,
            agePref,
            genderPref
        );

        // create a list of time slots
        List<Integer> timeslotIntegers = List.of(rand.nextInt(5));

        // create database entries with requestService
        UUID requestId = requestService.submitRequest(request, timeslotIntegers);

    }


}