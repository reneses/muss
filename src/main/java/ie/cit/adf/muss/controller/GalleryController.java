package ie.cit.adf.muss.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.TagService;
import ie.cit.adf.muss.services.UserService;
import ie.cit.adf.muss.validation.ReviewForm;
import ie.cit.adf.muss.validation.TagForm;

@Controller
public class GalleryController {

    @Autowired
    ChObjectService objectService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    AuthService authService;

    @Autowired
    TagService tagService;

    @RequestMapping(value = {"/gallery", "/gallery/tag/{tagName}", "gallery/tag"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "s", required = false) String search, @PathVariable(value = "tagName") Optional<String> tagName) {

        if (search != null) {
            // TODO the filter...
        }

        if (tagName.isPresent()) {
            model.addAttribute("chObjects", objectService.findByTagName(tagName.get()));
            model.addAttribute("tags", tagService.findDistinctTagNames());
            model.addAttribute("selectedTag", tagName.get());
        }
        else {
            model.addAttribute("chObjects", objectService.findAll());
            model.addAttribute("tags", tagService.findDistinctTagNames());
        }

        return "gallery";
    }

    /**
     * Filter the objects by the suplied tag
     *
     * @param model
     * @param tagName
     * @return Gallery view
     */
    /*@RequestMapping(value = "/gallery/tag/{tagName}", method = RequestMethod.GET)
    public String filterByTag(Model model, @PathVariable String tagName) {

        model.addAttribute("chObjects", objectService.findByTagName(tagName));
        model.addAttribute("tags", tagService.findDistinctTagNames());
        model.addAttribute("selectedTag", tagName);

        return "gallery";
    }*/

    /**
     * Show an object
     *
     * @param model
     * @param objectID
     * @return Object view
     */
    @RequestMapping(value = "/gallery/{objectID}", method = RequestMethod.GET)
    public String object(Model model, @PathVariable int objectID) {
        ChObject object = objectService.find(objectID);

        model.addAttribute("object", object);
        model.addAttribute("tagForm", new TagForm());
        model.addAttribute("reviewForm", new ReviewForm());
        model.addAttribute("isReviewedByUser", reviewService.hasReviewBy(object, authService.getPrincipal()));
        model.addAttribute("user", authService.getPrincipal());

        return "object";
    }

    /**
     * Add a review of an object
     *
     * @param reviewForm
     * @param bindingResult
     * @param model
     * @param objectID
     * @return Object view
     */
    @RequestMapping(value = "/gallery/{objectID}", method = RequestMethod.POST)
    public String postReview(@Valid ReviewForm reviewForm, BindingResult bindingResult, Model model, @PathVariable int objectID) {

        if (bindingResult.hasErrors()) {
            return object(model, objectID);
        }

        ChObject object = objectService.find(objectID);
        reviewService.addReview(object, reviewForm.getTitle(), reviewForm.getRating(), reviewForm.getContent());

        return object(model, objectID);

    }


    // API

    /**
     * Get a list of all the tags of an object
     *
     * @param objectID
     * @return JSON representation of the tags of the object
     */
    @RequestMapping(value = "/gallery/{objectID}/tags", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getTags(@PathVariable int objectID) {
        ChObject object = objectService.find(objectID);
        if (object == null)
            return new ArrayList<>();
        return object.getTags().stream().map(Tag::getName).collect(Collectors.toList());
    }

    /**
     * Add a tag to an object
     *
     * @param objectID
     * @param request
     * @return "OK" if success
     */
    @RequestMapping(value = "/gallery/{objectID}/tags", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public String postTag(@PathVariable int objectID, HttpServletRequest request) {

        try {

            String name = request.getParameter("tag");
            if (name == null || name.isEmpty())
                throw new IllegalArgumentException();
            int userID = Integer.valueOf(request.getParameter("userID"));
            User user = userService.find(userID);

            ChObject object = objectService.find(objectID);
            object.addTag(tagService.create(name, user));
            objectService.save(object);

            return "OK";

        } catch (Exception e) {
            return "FAIL";
        }
    }

}
