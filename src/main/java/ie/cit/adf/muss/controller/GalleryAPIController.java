package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GalleryAPIController {

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


    // HTTP responses
    private static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
    private static final ResponseEntity<String> ERROR = new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);


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
            objectService.addTag(
                    objectService.find(objectID),
                    request.getParameter("tag"),
                    userService.find(Integer.valueOf(request.getParameter("userID")))
            );
            return "OK";
        } catch (Exception e) {
            return "FAIL";
        }
    }

    /**
     * Get the likes of an object
     *
     * @param objectID
     * @return JSON representation of the tags of the object
     */
    @RequestMapping(value = "/gallery/{objectID}/likes", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getObjectLikes(@PathVariable int objectID) {
        ChObject object = objectService.find(objectID);
        if (object == null)
            return new ArrayList<>();
        return object.getLikes().stream().map(User::getUsername).collect(Collectors.toList());
    }

    /**
     * Like an object
     *
     * @param objectID
     * @param request
     * @return "OK" if success
     */
    @RequestMapping(value = "/gallery/{objectID}/likes", method = RequestMethod.POST, produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> postObjectLike(@PathVariable int objectID, HttpServletRequest request) {
        try {
            ChObject object = objectService.find(objectID);
            User user = userService.find(Integer.valueOf(request.getParameter("userID")));
            objectService.addLike(object, user);
            return OK;
        } catch (Exception e) {
            return ERROR;
        }
    }

    /**
     * Unlike an object
     *
     * @param objectID
     * @param request
     * @return "OK" if success
     */
    @RequestMapping(value = "/gallery/{objectID}/likes/{userID}", method = RequestMethod.DELETE, produces = "text/plain")
    @ResponseBody
    public ResponseEntity<String> postObjectUnlike(@PathVariable int objectID, @PathVariable int userID, HttpServletRequest request) {
        try {
            objectService.removeLike(objectService.find(objectID), userService.find(userID));
            return OK;
        } catch (Exception e) {
            return ERROR;
        }
    }

}
