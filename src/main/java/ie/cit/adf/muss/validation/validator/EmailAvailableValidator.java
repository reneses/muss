package ie.cit.adf.muss.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ie.cit.adf.muss.services.UserService;
import ie.cit.adf.muss.validation.annotation.EmailAvailable;

public class EmailAvailableValidator implements ConstraintValidator<EmailAvailable,String> {
	
	@Autowired
	UserService userService;

	@Override
	public void initialize(EmailAvailable constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return !userService.emailExists(value);
		
	}

}
