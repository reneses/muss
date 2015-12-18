package ie.cit.adf.muss.domain.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;

@Component
public class NotificationFactory {
	
	@Autowired
	ApplicationContext context;
	
	public ApplicationContext getContext() {
		return context;
	}
	
	public FollowNotification getFollowNotification(User user, User following) {
		return context.getBean(FollowNotification.class, user, following);
	}
	
	public ObjectLikeNotification getObjectLikeNotification(ChObject object, User user) {
		return context.getBean(ObjectLikeNotification.class, object, user);
	}
	
	public ReviewLikeNotification getReviewLikeNotification(Review review, User user) {
		return context.getBean(ReviewLikeNotification.class, review, user);
	}
	
	public ReviewNotification getReviewNotification(Review review) {
		return context.getBean(ReviewNotification.class, review);
	}
	
	public TagNotification getTagNotification(Tag tag) {
		return context.getBean(TagNotification.class, tag);
	}

}
