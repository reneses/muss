package ie.cit.adf.muss.controller;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.MussNotification;
import ie.cit.adf.muss.services.*;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class StreamController {

    @Autowired
    MussNotificationService mussNotificationService;

    @Autowired
    AuthService authService;

    @Autowired
    ChObjectService objectService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = {"/stream"}, method = RequestMethod.GET)
    public String index(Model model) {

        List<MussNotification> notifications = new ArrayList<>();
        User user = authService.getPrincipal();

        // Load the notifications
        if (user != null) {

            // Manually fix the autowired bug
            MussNotification.setObjectService(objectService);
            MussNotification.setReviewService(reviewService);
            MussNotification.setTagService(tagService);
            MussNotification.setUserService(userService);

            // Load the notifications
            notifications = mussNotificationService.getNotifications(user);

        }
        model.addAttribute("notifications", notifications);
        return "stream";
    }

}
