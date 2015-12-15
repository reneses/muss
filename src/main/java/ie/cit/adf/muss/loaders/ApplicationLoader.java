package ie.cit.adf.muss.loaders;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.ChObjectService;
import ie.cit.adf.muss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class ApplicationLoader {

    @Autowired
    ChObjectService objectService;

    @Autowired
    AbstractChObjectLoader objectLoader;

    @Autowired
    UserService userService;

    // List of random tags that can be generated
    private static final String[] randomTags = {
            "Impressive", "Red", "Blue", "Spanish", "American",
            "Unique", "Expensive", "Glamorous", "Food", "Cultural",
            "CIT", "Best"
    };


    /**
     * Load the application status
     */
    public void load() {
        try {

            // Objects & tags
            List<ChObject> objects = objectLoader.loadChObjects();
            addRandomTags(objects);
            objectService.save(objects);
            objectService.findAll().forEach(System.out::println);

            // Users
            addTestUsers();

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
     * user@password: reneses@reneses
     */
    private void addTestUsers() {
        User user = new User();
        user.setName("Álvaro Reneses");
        user.setEmail("me@reneses.io");
        user.setPassword("$2a$10$RMcliTmE5pcyYm8XLyeNG.AohlqrJtKSmNuBrmJ4sSGt5udmNSeD.");
        user.setUsername("reneses");
        userService.save(user);
    }

    /**
     * Add random tags to the objects
     *
     * @param objects
     */
    private void addRandomTags(List<ChObject> objects) {
        Random random = new Random();
        objects.forEach(object -> {
            Set<String> tags = new HashSet<>();
            int numberOfTags = random.nextInt(3) + 2;
            for (int i=0; i<numberOfTags; i++) {
                int idx = random.nextInt(randomTags.length);
                String tag = randomTags[idx];
                tags.add(tag);
            }
            for (String tag : tags) {
                Tag tagObj = new Tag();
                tagObj.setName(tag);
                object.addTag(tagObj);
            }
        });
    }

}
