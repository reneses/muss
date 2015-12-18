package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.User;

public class ReviewNotification extends MussNotification {

    private static final long serialVersionUID = -55857686305273843L;

    private transient Review review;
    private int reviewID;

    public ReviewNotification(Review review) {
        super();
        this.reviewID = review.getId();
        this.review = review;
    }

    public Review getReview() {
        if (review == null) {
            if (tagService == null)
                System.err.println("The tag service must have been initialized");
            else
                review = reviewService.find(reviewID);
        }
        return review;
    }

    public User getUser() {
        return getReview().getUser();
    }

    @Override
    public String toString() {
        return "ReviewNotification{" +
                "reviewID=" + reviewID +
                '}';
    }
}
