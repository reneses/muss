package ie.cit.adf.muss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//	Services --------------------------------------------------------------

	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	
	//	Profile ---------------------------------------------------------------
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String profile(Model model) {	
		
		User user = authService.getPrincipal();
		
		model.addAttribute("user", user);
		model.addAttribute("principal", user);
		
		return "user/profile";
	}
	
	@RequestMapping(value="/profile/{userID}", method = RequestMethod.GET)
	public String profile(@PathVariable int userID, Model model) {
		
		User user = userService.find(userID);
		User principal = authService.getPrincipal();

		model.addAttribute("user", user);
		model.addAttribute("principal", principal);
		
		return "user/profile";
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
