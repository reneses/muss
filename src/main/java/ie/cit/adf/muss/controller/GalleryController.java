package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.validation.ReviewForm;
import ie.cit.adf.muss.validation.TagForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.TagService;

import javax.validation.Valid;

@Controller
public class GalleryController {

	@Autowired
	ChObjectService objectService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	AuthService authService;

	@Autowired
	TagService tagService;

	@RequestMapping(value="/gallery", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(value="s", required=false) String search) {
		
		if (search != null) {
			// TODO the filter...
		}
		
		model.addAttribute("chObjects", objectService.findAll());
		model.addAttribute("tags", tagService.findDistinctTagNames());
		
		return "gallery";
	}

	@RequestMapping(value="/gallery/tag/{tagName}", method = RequestMethod.GET)
	public String galleryByTag(Model model, @PathVariable String tagName) {

		model.addAttribute("chObjects", objectService.findByTagName(tagName));
		model.addAttribute("tags", tagService.findDistinctTagNames());
		model.addAttribute("selectedTag", tagName);

		return "gallery";
	}

	@RequestMapping(value={"/gallery/{objectID}", "/gallery/{objectID}/tag", "/gallery/{objectID}/review"}, method = RequestMethod.GET)
	public String object(Model model, @PathVariable int objectID) {
		ChObject object = objectService.find(objectID);
		model.addAttribute("object", object);
		model.addAttribute("tagForm", new TagForm());
		model.addAttribute("reviewForm", new ReviewForm());
		model.addAttribute("isReviewedByUser", reviewService.hasReviewBy(object, authService.getPrincipal()));
		return "object";
	}

	// TODO: check that the tag is not repeated
	@RequestMapping(value="/gallery/{objectID}/tag", method=RequestMethod.POST)
	public String addTagToObject(@Valid TagForm tagForm, BindingResult bindingResult, Model model, @PathVariable int objectID) {

		if (bindingResult.hasErrors()) {
			return object(model, objectID);
		}

		Tag tag = new Tag();
		tag.setName(tagForm.getName().replace("/", "-"));
		tag.setUser(authService.getPrincipal());

		ChObject object = objectService.find(objectID);
		object.addTag(tag);
		objectService.save(object);

		return object(model, objectID);
	}


	// TODO: check that the tag is not repeated
	@RequestMapping(value="/gallery/{objectID}/review", method=RequestMethod.POST)
	public String addReviewToObject(@Valid ReviewForm reviewForm, BindingResult bindingResult, Model model, @PathVariable int objectID) {

		if (bindingResult.hasErrors()) {
			return object(model, objectID);
		}

		ChObject object = objectService.find(objectID);
		reviewService.addReview(object, reviewForm.getTitle(), reviewForm.getRating(), reviewForm.getContent());

		return object(model, objectID);

	}

}
