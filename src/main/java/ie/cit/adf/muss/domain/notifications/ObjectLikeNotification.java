package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.User;

public class ObjectLikeNotification extends MussNotification {

    private static final long serialVersionUID = -55857686305273843L;

    private int objectID;
    private int userID;
    private transient ChObject object;
    private transient User user;

    public ObjectLikeNotification(ChObject object, User user) {
        this.objectID = object.getId();
        this.userID = user.getId();
        this.object = object;
        this.user = user;
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

    public ChObject getObject() {
        if (object == null) {
            if (objectService == null)
                System.err.println("The object service must have been initialized");
            else
                object = objectService.find(objectID);
        }
        return object;
    }

    @Override
    public String toString() {
        return "ObjectLikeNotification{" +
                "objectID=" + objectID +
                ", userID=" + userID +
                '}';
    }
}
