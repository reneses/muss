package ie.cit.adf.muss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ie.cit.adf.muss.services.UserService;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MussApplication {

    public static void main(String[] args) {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("aspects-config.xml");

    	UserService customer = (UserService) appContext.getBean("customerBo");
    	customer.follow();
    	
        SpringApplication.run(MussApplication.class, args);
    }
}
