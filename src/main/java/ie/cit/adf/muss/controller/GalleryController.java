package ie.cit.adf.muss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.TagService;

@Controller
public class GalleryController {

	@Autowired
	ChObjectService objectService;
	
	@Autowired
	TagService tagService;
	
	@RequestMapping(value="/gallery", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("chObjects", objectService.findAll());
		model.addAttribute("tags", tagService.findDistinctTagNames());

		return "gallery";
	}
	
	@RequestMapping(value="/gallery/tag/{tagName}", method = RequestMethod.GET)
	public String galleryByTag(Model model, @PathVariable String tagName) {

		model.addAttribute("chObjects", objectService.findByTagName(tagName));
		model.addAttribute("tags", tagService.findDistinctTagNames());

		return "gallery";
	}

	@RequestMapping(value="/gallery/{objectID}", method = RequestMethod.GET)
	public String object(Model model, @PathVariable int objectID) {

		ChObject object = objectService.find(objectID);

		model.addAttribute("object", object);
		return "object";
	}
	
}
