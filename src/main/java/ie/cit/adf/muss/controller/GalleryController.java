package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GalleryController {

	@Autowired
	ImageService imageService;
	
	@RequestMapping(value="/gallery", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("image", imageService.findAll().get(1));
		return "galley";
	}
	
}
