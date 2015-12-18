package ie.cit.adf.muss.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.MussNotification;
import ie.cit.adf.muss.services.AuthService;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.MussNotificationService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.TagService;
import ie.cit.adf.muss.services.UserService;

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
