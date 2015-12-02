package ie.cit.adf.muss.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ie.cit.adf.muss.validation.validator.UsernameAvailableValidator;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameAvailableValidator.class)
@Documented
public @interface UsernameAvailable {

    String message() default "username not available";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
