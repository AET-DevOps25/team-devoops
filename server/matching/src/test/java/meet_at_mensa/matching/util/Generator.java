package meet_at_mensa.matching.util;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

import org.openapitools.model.User;
import org.openapitools.model.UserNew;

public class Generator {

    private static RandomGenerator random = RandomGenerator.getDefault();
    //private static Random random = new Random();

    public Integer validTimeslot() {

        return random.nextInt(1, 17);

    }
    
}
