package ie.cit.adf.muss.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class GamificationAspect {

	public void logAfter(JoinPoint joinPoint) {
		System.out.println("Test");
	}
	
//	@After("execution(* addLike(*))")
//	public void pointAssigner(JoinPoint joinPoint) {
//		System.out.println("Test");
//	}
}
