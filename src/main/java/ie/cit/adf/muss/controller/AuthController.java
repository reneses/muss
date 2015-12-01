package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.validation.UserForm;
import ie.cit.adf.muss.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String postRegistration(@Valid User userForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        
        userForm = userService.registerUser(userForm);
        
        model.addAttribute("user", userForm);
        return "profile";
    }

}
