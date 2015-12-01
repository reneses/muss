package ie.cit.adf.muss.loaders;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.utility.FileFinder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * ChObject
 */
public abstract class AbstractChObjectLoader {

    private String extension, objectsDirectory;


    /**
     * Abstract loader constructor
     *
     * @param extension
     * @param objectsDirectory
     */
    public AbstractChObjectLoader(String extension, String objectsDirectory) {
        this.extension = extension;
        this.objectsDirectory = objectsDirectory;
    }


    /**
     * Load the paths of the ch object files
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException 
     */
    protected List<Path> loadFiles() throws IOException, URISyntaxException {
        String objectsDirectory = AbstractChObjectLoader.class.getResource('/' + this.objectsDirectory).getPath();
        objectsDirectory = URLDecoder.decode(objectsDirectory, "utf-8");
        return FileFinder.getFileList(objectsDirectory, "*." + this.extension);
    }


    /**
     * Map a file to an object
     *
     * @param path
     * @return
     */
    protected abstract ChObject mapFile(Path path);


    /**
     * Load all the objects from the storage
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException 
     */
    public List<ChObject> loadChObjects() throws IOException, URISyntaxException {
        List<ChObject> objects = loadFiles().stream().map(this::mapFile).collect(Collectors.toList());
        addRandomTags(objects);
        return objects;
    }

    private void addRandomTags(List<ChObject> objects) {
        Random random = new Random();
        String[] randomTags = {"Impressive", "Red", "Blue", "Spanish", "American", "Unique", "Expensive", "Glamorous", "Food", "Cultural", "CIT", "Best"};
        objects.forEach(object -> {
            List<Tag> tags = new ArrayList<>();
            int numberOfTags = random.nextInt(3) + 2;
            for (int i=0; i<numberOfTags; i++) {
                int idx = random.nextInt(randomTags.length);
                Tag tag = new Tag();
                tag.setName(randomTags[idx]);
                tags.add(tag);
            }
            object.setTags(tags);
        });
    }

}
