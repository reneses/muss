package ie.cit.adf.muss.loaders;

import ie.cit.adf.muss.domain.Badge;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.BadgeService;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@EnableAspectJAutoProxy
@Component
public class ApplicationLoader {

    @Autowired
    ChObjectService objectService;
    @Autowired
    AbstractChObjectLoader objectLoader;
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    BadgeService badgeService;

    // List of random tags that can be generated
    private static final String[] RANDOM_TAGS = {
            "Impressive", "Red", "Blue", "Spanish", "American",
            "Unique", "Expensive", "Glamorous", "Food", "Cultural",
            "CIT", "Best"
    };

    // Titles and contents for the reviews
    private static final String[] RANDOM_TITLES = {
            "WOW!", "BLEH", "I kno' what Im sayin'", "Awesome piece", "Lorem Impsum", "Colder than winter",
            "Magestic piece", "Not bad", "Worthwhile", "Completely useless", "I'm an expert", "Different",
            "Yes!"
    };
    private static final String[] RANDOM_CONTENT = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    };

    // Default password (root)
    private static final String DEFAULT_PASSWORD = "$2a$10$gu1JC2c601.w2x/F3HzGbectUkkvb2ZmSE0hEbYcwco1TbXYFjWGu";


    /**
     * Load the application status
     */
    public void load() {
        try {

            // Users
            addTestUsers();

            // Objects & tags
            objectService.save(objectLoader.loadChObjects());
            addRandomTags();
            addRandomLikes();
            addRandomReviews();
            objectService.findAll().forEach(System.out::println);
            
            // Badges
            addTestBadges();

        } catch (DuplicateKeyException e) {
            System.err.println("The files were already imported!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the application for first time
     */
    public void loadIfNeeded() {
        if (objectService.count() == 0) {
            load();
        }
    }

    /**
     * Create users
     */
    private void addTestUsers() {

        User user = new User();
        user.setName("Álvaro Reneses");
        user.setEmail("reneses@muss.ie");
        user.setPassword(DEFAULT_PASSWORD);
        user.setUsername("reneses");
        try {
            user.setPicture(IOUtils.toByteArray(ApplicationLoader.class.getResourceAsStream("/sample/reneses.jpg")));
        } catch (IOException ignored) {}
        userService.save(user);

        User user2 = new User();
        user2.setName("Raúl León");
        user2.setEmail("raul@muss.ie");
        user2.setPassword(DEFAULT_PASSWORD);
        user2.setUsername("raul");
        userService.save(user2);

        User user3 = new User();
        user3.setName("David Macías");
        user3.setEmail("david@muss.ie");
        user3.setPassword(DEFAULT_PASSWORD);
        user3.setUsername("david99");
        userService.save(user3);

        User user4 = new User();
        user4.setName("Adrian PC");
        user4.setEmail("adrian@muss.ie");
        user4.setPassword(DEFAULT_PASSWORD);
        user4.setUsername("adri");
        userService.save(user4);

        User user5 = new User();
        user5.setName("Larkin");
        user5.setEmail("larkin@muss.ie");
        user5.setPassword(DEFAULT_PASSWORD);
        user5.setUsername("larkin");
        userService.save(user4);

    }

    /**
     * Add random tags to the objects
     */
    private void addRandomTags() {
        Random random = new Random();
        List<User> users = userService.findAll();
        objectService.findAll().forEach(object -> {
            List<String> tags = new ArrayList<>(Arrays.asList(RANDOM_TAGS));
            int numberOfTags = random.nextInt(3) + 2;
            for (int i = 0; i < numberOfTags; i++) {
                Tag tagObj = new Tag();
                String tag = tags.get(random.nextInt(tags.size()));
                tagObj.setName(tag);
                tags.remove(tag);
                tagObj.setUser(users.get(random.nextInt(users.size())));
                object.addTag(tagObj);
            }
            objectService.save(object);
        });
    }


    /**
     * Add random likes to the objects
     */
    private void addRandomLikes() {
        Random random = new Random();
        objectService.findAll().forEach(object -> {
            List<User> users = userService.findAll();
            int numberOfLikes = random.nextInt(2) + 1;
            for (int i = 0; i < numberOfLikes; i++) {
                User user = users.get(random.nextInt(users.size()));
                users.remove(user);
                object.addLike(user);
            }
            objectService.save(object);
        });
    }


    /**
     * Add random reviews to the objects
     */
    private void addRandomReviews() {

        Random random = new Random();
        objectService.findAll().forEach(object -> {
            List<User> usersToUse = userService.findAll();
            int numberOfReviews = random.nextInt(2) + 1;
            for (int i = 0; i < numberOfReviews; i++) {

                // Create review
                User user = usersToUse.get(random.nextInt(usersToUse.size()));
                usersToUse.remove(user);
                Review review = new Review();
                review.setRating(random.nextInt(5) + 1);
                review.setTitle(RANDOM_TITLES[random.nextInt(RANDOM_TITLES.length)]);
                review.setContent(RANDOM_CONTENT[random.nextInt(RANDOM_CONTENT.length)]);
                review.setUser(user);
                object.addReview(review);

            }
            objectService.save(object);

        });

        // Add likes to reviews
        reviewService.findAll().forEach(review -> {
            List<User> usersToLike = userService.findAll();
            int numberOfLikes = random.nextInt(2) + 1;
            for (int j = 0; j < numberOfLikes; j++) {
                User toLike = usersToLike.get(random.nextInt(usersToLike.size()));
                usersToLike.remove(toLike);
                review.addLike(toLike);
            }
            reviewService.save(review);
        });
    }

    private void addTestBadges() {

        Badge badge1 = new Badge();
        badge1.setName("Illuminaty");
        badge1.setQuantity(10);
        badge1.setType(Badge.FOLLOWERS);
        badge1.setImage("http://imageshack.com/a/img907/6/UcnCHw.png");
        badgeService.save(badge1);

        Badge badge2 = new Badge();
        badge2.setName("Professor");
        badge2.setQuantity(5);
        badge2.setType(Badge.FOLLOWERS);
        badge2.setImage("http://imageshack.com/a/img911/9399/mFGoyW.png");
        badgeService.save(badge2);
        
        Badge badge3 = new Badge();
        badge3.setName("Giver");
        badge3.setQuantity(1);
        badge3.setType(Badge.FOLLOWERS);
        badge3.setImage("http://imageshack.com/a/img903/4733/yQy9mr.png");
        badgeService.save(badge3);
        
        Badge badge4 = new Badge();
        badge4.setName("Lunatic");
        badge4.setQuantity(10);
        badge4.setType(Badge.FOLLOWING);
        badge4.setImage("http://imageshack.com/a/img905/1288/vW6UxR.png");
        badgeService.save(badge4);

        Badge badge5 = new Badge();
        badge5.setName("Student");
        badge5.setQuantity(5);
        badge5.setType(Badge.FOLLOWING);
        badge5.setImage("http://imageshack.com/a/img910/7429/OIDHpk.png");
        badgeService.save(badge5);
        
        Badge badge6 = new Badge();
        badge6.setName("Baby");
        badge6.setQuantity(1);
        badge6.setType(Badge.FOLLOWING);
        badge6.setImage("http://imageshack.com/a/img903/6681/0k2gTB.png");
        badgeService.save(badge6);
        
        Badge badge7 = new Badge();
        badge7.setName("Winning");
        badge7.setQuantity(100);
        badge7.setType(Badge.POINTS);
        badge7.setImage("http://imageshack.com/a/img903/4746/lRWN9y.png");
        badgeService.save(badge7);

        Badge badge8 = new Badge();
        badge8.setName("Earner");
        badge8.setQuantity(50);
        badge8.setType(Badge.POINTS);
        badge8.setImage("http://imageshack.com/a/img911/385/n5DeKB.png");
        badgeService.save(badge8);
        
        Badge badge9 = new Badge();
        badge9.setName("Collector");
        badge9.setQuantity(10);
        badge9.setType(Badge.POINTS);
        badge9.setImage("http://imageshack.com/a/img903/2699/cKbODb.png");
        badgeService.save(badge9);

        Badge badge10 = new Badge();
        badge10.setName("Profiler");
        badge10.setQuantity(1);
        badge10.setType(Badge.COMPLETED);
        badge10.setImage("http://imageshack.com/a/img903/3626/QNgj4O.png");
        badgeService.save(badge10);
    }
}
