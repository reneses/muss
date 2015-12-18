package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.TagService;
import ie.cit.adf.muss.services.UserService;

import java.io.Serializable;

public abstract class MussNotification implements Serializable {

    private static final long serialVersionUID = -55857686305273843L;

    // TODO fix autowired is not working
    protected static ChObjectService objectService;
    protected static TagService tagService;
    protected static UserService userService;
    protected static ReviewService reviewService;

    public static void setObjectService(ChObjectService objectService) {
        MussNotification.objectService = objectService;
    }

    public static void setTagService(TagService tagService) {
        MussNotification.tagService = tagService;
    }

    public static void setUserService(UserService userService) {
        MussNotification.userService = userService;
    }

    public static void setReviewService(ReviewService reviewService) {
        MussNotification.reviewService = reviewService;
    }

    public abstract User getUser();
}
