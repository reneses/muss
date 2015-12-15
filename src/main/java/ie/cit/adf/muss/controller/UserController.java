package ie.cit.adf.muss.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.APIService;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.UserService;
import ie.cit.adf.muss.validation.ChangePasswordForm;
import ie.cit.adf.muss.validation.EditProfileForm;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//	Services --------------------------------------------------------------

	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	@Autowired
    APIService apiService;
	
	//	Profile ---------------------------------------------------------------
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String profile(Model model) {	
		
		User user = authService.getPrincipal();
		
		Date date = new Date();
		
		model.addAttribute("user", user);
		model.addAttribute("principal", user);
        model.addAttribute("time", date.getTime());
        model.addAttribute("HMAC", user == null? "" : apiService.getHMAC(user, date));
		
		return "user/profile";
	}
	
	@RequestMapping(value="/profile/{username}", method = RequestMethod.GET)
	public String profile(@PathVariable String username, Model model) {
		
		User user = userService.findByUsername(username);
		User principal = authService.getPrincipal();
		
		Date date = new Date();

		model.addAttribute("user", user);
		model.addAttribute("principal", principal);
        model.addAttribute("time", date.getTime());
        model.addAttribute("HMAC", user == null? "" : apiService.getHMAC(user, date));
		
		return "user/profile";
	}
	
	//	Edit ------------------------------------------------------------------
	
	@RequestMapping(value="/editProfile", method=RequestMethod.GET)
	public String editProfile(Model model) {
		
		User user = authService.getPrincipal();
		
		EditProfileForm form = new EditProfileForm();
		form.setName(user.getName());
		form.setEmail(user.getEmail());
		form.setUsername(user.getUsername());
		
		model.addAttribute("form", form);
		
		return "user/editProfile";
	}
	
	@RequestMapping(value="/editProfile", method=RequestMethod.POST)
	public String editProfilePost(@Valid @ModelAttribute("form") EditProfileForm form, BindingResult bindingResult) {
		
		User user = authService.getPrincipal();
		
		if (!form.getUsername().equals(user.getUsername()) && 
				userService.usernameExists(form.getUsername())) {
			bindingResult.rejectValue("username", "UsernameNotAvailable", "Username not available");	
		}

		if (!form.getEmail().equals(user.getEmail()) && 
				userService.emailExists(form.getEmail())) {
			bindingResult.rejectValue("email", "EmailNotAvailable", "Email not available");	
		}
		
		if (bindingResult.hasErrors()) {
            return "user/editProfile";
        }

		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setUsername(form.getUsername());
		
		user = userService.save(user);
        
        return "redirect:/user/profile";
	}
	
	@RequestMapping(value="/changePicture", method=RequestMethod.GET)
	public String changePicture(Model model) {
		return "user/changePicture";
	}
	
	@RequestMapping(value="/changePicture", method=RequestMethod.POST)
	public String changePicturePost() {
		return "";
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(Model model) {
		
		model.addAttribute("form", new ChangePasswordForm());
		
		return "user/changePassword";
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public String changePasswordPost(@Valid @ModelAttribute("form") ChangePasswordForm form, BindingResult bindingResult) {
		
		User user = authService.getPrincipal();
		
		if (!userService.passwordMatches(user, form.getOldPassword())) {
			bindingResult.rejectValue("oldPassword", "IncorrectPassword", "Incorrect password");	
		}

		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "PasswordNotMatch", "Password don't match");	
		}
		
		if (bindingResult.hasErrors()) {
            return "user/changePassword";
        }
		
		user.setPassword(userService.encodePassword(form.getNewPassword()));
		
		user = userService.save(user);
        
        return "redirect:/user/profile";
	}

	//	Follow & Unfollow -----------------------------------------------------
	
	@RequestMapping(value="/follow/{userID}", method = RequestMethod.GET)
	public String follow(@PathVariable int userID) {
		
		try{
			userService.followUser(userID);
		}catch(Throwable oops){
			System.out.println(oops.toString());
		}
		
		return "redirect:/user/profile";
	}
	
	@RequestMapping(value="/unfollow/{userID}", method = RequestMethod.GET)
	public String unfollow(@PathVariable int userID) {
		
		try{
			userService.unFollowUser(userID);
		}catch(Throwable oops){
			System.out.println(oops.toString());
		}
		
		return "redirect:/user/profile";
	}
	
	//	Listing ---------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model){	
		
		List<User> users;

		users = userService.findAll();
		User principal = authService.getPrincipal();

		model.addAttribute("users", users);
		model.addAttribute("principal", principal);
		
		return "user/list";
	}

}
