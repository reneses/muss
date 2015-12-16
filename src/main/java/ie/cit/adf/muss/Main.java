package ie.cit.adf.muss;


import ie.cit.adf.muss.loaders.ApplicationLoader;
import ie.cit.adf.muss.services.ChObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component
@Profile("default")
public class Main implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ChObjectService objectService;

    @Autowired
    ApplicationLoader applicationLoader;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MUSS HAS BEING EXECUTED");
        applicationLoader.loadIfNeeded();
    }

}
