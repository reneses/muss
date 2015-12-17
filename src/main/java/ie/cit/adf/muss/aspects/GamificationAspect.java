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

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.GamificationService;

@DependsOn("springApplicationContextHolder")
@Aspect
@Configuration
//@Configurable
//@Configured
public class GamificationAspect {

//	@Autowired
//	AuthService authService;
	@Autowired
	GamificationService gamificationService;
	
	private Integer temporalIN, temporalOUT;
	
	@Before("execution(* follow*(..))")
	public void beforeFriends() {
		System.out.println("Test1");
//		User principal = authService.getPrincipal();
//		System.out.println(principal.getName());
		
		
		System.out.println(gamificationService);
		gamificationService.test();
	}
	
	@After("execution(* follow*(..))")
	public void afterFriends() {
		System.out.println("Test2");
	}
	
	
	
	//	@After(execution(* addLike(*))")
//	public void pointAssigner(JoinPoint joinPoint) {
//		System.out.println("Test");
//	}
}
