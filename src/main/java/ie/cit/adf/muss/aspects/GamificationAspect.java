package ie.cit.adf.muss.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class GamificationAspect {

	@After("execution(* ie.cit.adf.muss.services.UserService.follow*(..))")
	public void logAfter(JoinPoint joinPoint) {
		System.out.println("Test");
	}
	
//	@After("execution(* addLike(*))")
//	public void pointAssigner(JoinPoint joinPoint) {
//		System.out.println("Test");
//	}
}
