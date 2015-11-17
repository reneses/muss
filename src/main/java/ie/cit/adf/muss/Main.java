package ie.cit.adf.muss;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.services.ChObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Profile("default")
public class Main implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ChObjectService objectService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("MUSS HAS BEING EXECUTED");
        try {
            List<ChObject> objects = objectService.loadFromFolder();
            //objects = objectService.findAll();
            objects.forEach(System.out::println);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
