package ie.cit.adf.muss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class MussApplication {

    public static void main(String[] args) {
    	new ClassPathXmlApplicationContext("aspects-config.xml");

        SpringApplication.run(MussApplication.class, args);
    }
}
