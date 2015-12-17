package ie.cit.adf.muss.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jvnet.hk2.config.Configured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import ie.cit.adf.muss.configuration.SpringApplicationContextHolder;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.GamificationService;

@DependsOn("springApplicationContextHolder")
@Aspect
@Configuration
//@Configurable
//@Configured
public class GamificationAspect {
	
	@Autowired
	GamificationService gamificationService;
	
	public GamificationService aspectOf() {
		return SpringApplicationContextHolder.getApplicationContext().getBean(GamificationService.class);
    }
	
//	private Integer temporalIN, temporalOUT;
	
	@Before("execution(* follow*(..))")
	public void beforeFriends() {
		System.out.println("beforeMethod IN");

		System.out.println("gamificationServic Autowire SUCCESS: " + gamificationService);
		gamificationService.test();
		
		System.out.println("beforeMethod OUT");
	}
	
	@After("execution(* follow*(..))")
	public void afterFriends() {
		System.out.println("afterMethod IN");
		System.out.println("afterMethod OUT");
	}
	
	
	
	//	@After(execution(* addLike(*))")
//	public void pointAssigner(JoinPoint joinPoint) {
//		System.out.println("Test");
//	}
}
