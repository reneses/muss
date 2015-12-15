package ie.cit.adf.muss.loaders;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Review;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.ReviewService;
import ie.cit.adf.muss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.*;

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

}
