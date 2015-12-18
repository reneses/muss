package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.User;

public class ReviewLikeNotification extends MussNotification {

    private static final long serialVersionUID = -55857686305273843L;

    private int reviewID;
    private int userID;
    private transient Review review;
    private transient User user;

    public ReviewLikeNotification(Review review, User user) {
        this.reviewID = review.getId();
        this.userID = user.getId();
        this.review = review;
        this.user = user;
    }

    public User getUser() {
        if (user == null) {
            if (userService == null)
                System.err.println("The review service must have been initialized");
            else
                user = userService.find(userID);
        }
        return user;
    }

    public Review getReview() {
        if (review == null) {
            if (reviewService == null)
                System.err.println("The object service must have been initialized");
            else
                review = reviewService.find(reviewID);
        }
        return review;
    }

    @Override
    public String toString() {
        return "ReviewLikeNotification{" +
                "reviewID=" + reviewID +
                ", userID=" + userID +
                '}';
    }
}
