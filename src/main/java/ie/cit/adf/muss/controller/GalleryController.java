package ie.cit.adf.muss.controller;

import java.util.*;
import javax.validation.Valid;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ie.cit.adf.muss.domain.ChObject;
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

    @Autowired
    APIService apiService;

    @RequestMapping(value = {"/gallery", "/gallery/p", "/gallery/p/{page}", "/gallery/p/{page}/tag/{tagName}", "gallery/p/{page}/tag"}, method = RequestMethod.GET)
    public String index(Model model, @RequestParam(value = "s", required = false) String search, @PathVariable(value = "page") Optional<Integer> page, @PathVariable(value = "tagName") Optional<String> tagName) {

        // Load page number
        int pageNumber = page.isPresent() ? page.get() : 1;
        if (pageNumber < 1)
            pageNumber = 1;

        // Set search
        if (search != null)
            model.addAttribute("search", search);

        // Set tags
        model.addAttribute("tags", tagService.findDistinctTagNames());

        // Load objects
        List<ChObject> objects;
        if (tagName.isPresent()) {
            model.addAttribute("selectedTag", tagName.get());
            if (search != null)
                objects = objectService.findByTitleOrDescriptionAndTagName(search, tagName.get());
            else
                objects = objectService.findByTagName(tagName.get());
        } else {
            if (search != null)
                objects = objectService.findByTitleOrDescription(search);
            else
                objects = objectService.findAll();
        }

        // Pages
        int lastPage = objectService.numberOfPages(objects);
        List<Integer> pages = new ArrayList<>();
        for (int i = pageNumber; i > 0  &&  i > pageNumber - 5; i--)
            pages.add(i);
        for (int i = pageNumber + 1; i <= lastPage  &&  i < pageNumber + 5; i++)
            pages.add(i);
        Collections.sort(pages);
        model.addAttribute("pages", pages);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("previousPage", pageNumber == 1 ? 1 : pageNumber - 1);
        model.addAttribute("nextPage", pageNumber == pages.size() ? pages.size() : pageNumber + 1);

        // Objects acording to the pagination
        System.out.println("PAGE " + pageNumber);
        objects = objectService.paginate(objects, pageNumber);
        model.addAttribute("chObjects", objects);

        return "gallery";
    }

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

        User user = authService.getPrincipal();
        Date date = new Date();

        model.addAttribute("object", object);
        model.addAttribute("tagForm", new TagForm());
        model.addAttribute("reviewForm", new ReviewForm());
        model.addAttribute("isReviewedByUser", reviewService.hasReviewBy(object, authService.getPrincipal()));
        model.addAttribute("user", user);
        model.addAttribute("time", date.getTime());
        model.addAttribute("HMAC", user == null ? "" : apiService.getHMAC(user, date));

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

}
