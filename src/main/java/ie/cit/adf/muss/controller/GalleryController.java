package ie.cit.adf.muss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.services.ChObjectService;

@Controller
public class GalleryController {

	@Autowired
	ChObjectService objectService;
	
	@RequestMapping(value="/gallery", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("chObjects", objectService.findAll());

		return "gallery";
	}

	@RequestMapping(value="/gallery/{objectID}", method = RequestMethod.GET)
	public String object(Model model, @PathVariable int objectID) {

		ChObject object = objectService.find(objectID);

		model.addAttribute("object", object);
		return "object";
	}
	
}
