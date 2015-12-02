package ie.cit.adf.muss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView profile() {
		ModelAndView result;
		
		User user = authService.getPrincipal();
		
		result = new ModelAndView("user/profile");
		result.addObject("user", user);
		result.addObject("principal", user);
		return result;
	}
	
	@RequestMapping(value="/profile/{userID}", method = RequestMethod.GET)
	public ModelAndView profile(@PathVariable int userID) {
		ModelAndView result;
		
		User user = userService.find(userID);
		User principal = authService.getPrincipal();

		result = new ModelAndView("user/profile");
		result.addObject("user", user);
		result.addObject("principal", principal);
		return result;
	}

	//	Follow & Unfollow -----------------------------------------------------
	
	@RequestMapping(value="/follow/{userID}", method = RequestMethod.GET)
	public ModelAndView follow(@PathVariable int userID) {
		
		try{
			userService.followUser(userID);
		}catch(Throwable oops){
			System.out.println(oops.toString());
		}
		
		return profile();
	}
	
	@RequestMapping(value="/unfollow/{userID}", method = RequestMethod.GET)
	public ModelAndView unfollow(@PathVariable int userID) {
		
		try{
			userService.unFollowUser(userID);
		}catch(Throwable oops){
			System.out.println(oops.toString());
		}
		
		return profile();
	}
	
	//	Listing ---------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){	
		ModelAndView result;
		List<User> users;

		users = userService.findAll();
		User principal = authService.getPrincipal();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		
		return result;
	}

}
