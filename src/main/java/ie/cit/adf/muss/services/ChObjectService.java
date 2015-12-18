package ie.cit.adf.muss.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Gamification;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.domain.notifications.NotificationFactory;
import ie.cit.adf.muss.domain.notifications.ObjectLikeNotification;
import ie.cit.adf.muss.domain.notifications.ReviewNotification;
import ie.cit.adf.muss.domain.notifications.TagNotification;
import ie.cit.adf.muss.repositories.ChObjectRepository;

@Service
public class ChObjectService extends CrudService<ChObject> {

    private static final int DEFAULT_ITEMS_PER_PAGE = 9;

    // ------------------- Managed repository --------------------

    @Autowired
    ChObjectRepository objectRepository;

    // ------------------- Supporting services -------------------

    @Autowired
    ParticipationService participationService;

    @Autowired
    TagService tagService;

    @Autowired
    MussNotificationService notificationService;
    
    @Autowired
    NotificationFactory notificationFactory;

    @Autowired
    GamificationService gamificationService;

    // ----------------------- Constructor -----------------------

    // ------------------- Simple CRUD methods -------------------

    /**
     * Retrieve a model given its original id
     *
     * @param id ID of the role
     * @return Model with the given id, null if it does not exist
     * @throws IllegalArgumentException if the id is not a positive integer
     */
    public ChObject findOneByOriginalId(int id) {
        return objectRepository.findOneByOriginalId(id);
    }


    @Override
    public ChObject save(ChObject model) {
        if (model == null)
            throw new IllegalArgumentException("The object cannot be null");
        return super.save(model);
    }

    // ----------------- Other business methods ------------------

    // REPOSITORY:

    public long count() {
        return objectRepository.count();
    }

    public List<ChObject> findByTagName(String name) {
        return objectRepository.findByTagName(name);
    }

    public List<ChObject> findByTitleOrDescription(String search) {
        return objectRepository.findByTitleOrDescription(search);
    }

    public List<ChObject> findByTitleOrDescriptionAndTagName(String search, String tagName) {
        return objectRepository.findByTitleOrDescriptionAndTagName(search, tagName);
    }

    // USE CASES:

    public boolean isLikedBy(ChObject object, User user) {
        if (object == null || user == null)
            return false;
        for (ChObject liked : user.getChObjectLikes())
            if (liked.equals(object))
                return true;
        return false;
    }

    public void addTag(ChObject object, String tagName, User user) {
        Tag tag = tagService.create(tagName, user);
        object.addTag(tag);
        save(object);
        tag = find(object.getId()).getTagByName(tagName); // Obtain the new ID
        TagNotification notification = notificationFactory.getTagNotification(tag);
        notificationService.notificateFollowers(notification, user);
        try {
            gamificationService.assignPoints(Gamification.TAG, user);
        } catch (NullPointerException e) {
            //TODO fix;
        }
    }

    public void addLike(ChObject object, User user) {
        boolean real = object.addLike(user);
        save(object);
        ObjectLikeNotification notification = notificationFactory.getObjectLikeNotification(object, user);
        notificationService.notificateFollowers(notification, user);
        try {
            if (real)
                gamificationService.assignPoints(Gamification.LIKEGIVEN, user);
        } catch (NullPointerException e) {
            // TODO fix

        }
    }

    public void removeLike(ChObject object, User user) {
        boolean real = object.removeLike(user);
        save(object);

        if (real)
            gamificationService.removePoints(Gamification.LIKEGIVEN, user);
    }

    public void addReview(ChObject object, Review review) {
        object.addReview(review);
        save(object);
        review = find(object.getId()).getReviewByUser(review.getUser()); // Reload the ID
        ReviewNotification notification = new ReviewNotification(review);
        notificationService.notificateFollowers(notification, review.getUser());
    }

    public List<ChObject> paginate(List<ChObject> objects, int currentPage) {
        return paginate(objects, currentPage, DEFAULT_ITEMS_PER_PAGE);
    }

    public List<ChObject> paginate(List<ChObject> objects, int currentPage, int itemsPerPage) {
        int elementsToSkip = currentPage * itemsPerPage;
        return objects.stream()
                .skip(elementsToSkip)
                .limit(itemsPerPage)
                .collect(Collectors.toList());
    }

    public int numberOfPages(List<ChObject> objects, int itemsPerPage) {
        float result = (float) objects.size() / (float) itemsPerPage;
        if (result % 1 > 0)
            return Math.round(result);
        return Math.round(result) - 1;
    }

    public int numberOfPages(List<ChObject> objects) {
        return numberOfPages(objects, DEFAULT_ITEMS_PER_PAGE);
    }

}