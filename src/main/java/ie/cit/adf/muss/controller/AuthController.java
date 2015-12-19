package ie.cit.adf.muss.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.UserService;
import ie.cit.adf.muss.validation.UserForm;

@Controller
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String getRegistration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String postRegistration(@Valid UserForm userForm, BindingResult bindingResult) {
        
    	if (bindingResult.hasErrors()) {
            return "register";
        }
        
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        
        user = userService.registerUser(user);
        
        return "redirect:/";
    }

}
