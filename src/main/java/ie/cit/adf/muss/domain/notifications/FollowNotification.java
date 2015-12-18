package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.User;

public class FollowNotification extends MussNotification {

    private static final long serialVersionUID = -55857686305273843L;

    private int userID;
    private int followingID;
    private transient User user;
    private transient User following;

    public FollowNotification(User user, User following) {
        this.userID = user.getId();
        this.followingID = following.getId();
        this.user = user;
        this.following = following;
    }

    public User getUser() {
        if (user == null) {
            if (userService == null)
                System.err.println("The user service must have been initialized");
            else
                user = userService.find(userID);
        }
        return user;
    }

    public User getFollowing() {
        if (following == null) {
            if (userService == null)
                System.err.println("The user service must have been initialized");
            else
                following = userService.find(followingID);
        }
        return following;
    }

    @Override
    public String toString() {
        return "FollowNotification{" +
                "userID=" + userID +
                ", followingID=" + followingID +
                '}';
    }
}
