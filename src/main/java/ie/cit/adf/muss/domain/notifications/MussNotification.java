package ie.cit.adf.muss.domain.notifications;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.TagService;
import ie.cit.adf.muss.services.UserService;

public abstract class MussNotification implements Serializable {

    private static final long serialVersionUID = -55857686305273843L;

    protected static ChObjectService objectService;
    protected static TagService tagService;
    protected static UserService userService;
    protected static ReviewService reviewService;

    @Autowired
    public void setObjectService(ChObjectService objectService) {
        MussNotification.objectService = objectService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        MussNotification.tagService = tagService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        MussNotification.userService = userService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        MussNotification.reviewService = reviewService;
    }

    public abstract User getUser();
}
