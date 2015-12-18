package ie.cit.adf.muss.domain.notifications;

import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;

public class TagNotification extends MussNotification {

    private static final long serialVersionUID = -55857686305273843L;

    private transient Tag tag;
    private int tagID;

    public TagNotification(Tag tag) {
        super();
        this.tagID = tag.getId();
        this.tag = tag;
    }

    public Tag getTag() {
        if (tag == null) {
            if (tagService == null)
                System.err.println("The tag service must have been initialized");
            else
                tag = tagService.find(tagID);
        }
        return tag;
    }

    public User getUser() {
        return getTag().getUser();
    }

    @Override
    public String toString() {
        return "TagNotification{" +
                "tagID=" + tagID +
                '}';
    }
}
