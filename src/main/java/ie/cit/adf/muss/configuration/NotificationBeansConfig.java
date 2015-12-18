package ie.cit.adf.muss.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.FollowNotification;
import ie.cit.adf.muss.domain.notifications.ObjectLikeNotification;
import ie.cit.adf.muss.domain.notifications.ReviewLikeNotification;
import ie.cit.adf.muss.domain.notifications.ReviewNotification;
import ie.cit.adf.muss.domain.notifications.TagNotification;

@Configuration
public class NotificationBeansConfig {
	
	@Bean
	@Scope("prototype")
	public FollowNotification followNotification(User user, User following) {
	    return new FollowNotification(user, following);
	}
	
	@Bean
	@Scope("prototype")
	public ObjectLikeNotification objectLikeNotification(ChObject object, User user) {
	    return new ObjectLikeNotification(object, user);
	}
	
	@Bean
	@Scope("prototype")
	public ReviewLikeNotification reviewLikeNotification(Review review, User user) {
	    return new ReviewLikeNotification(review, user);
	}
	
	@Bean
	@Scope("prototype")
	public ReviewNotification reviewNotification(Review review) {
	    return new ReviewNotification(review);
	}
	
	@Bean
	@Scope("prototype")
	public TagNotification tagNotification(Tag tag) {
	    return new TagNotification(tag);
	}

}
