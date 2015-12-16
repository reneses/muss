package ie.cit.adf.muss.loaders;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.domain.Tag;
import ie.cit.adf.muss.domain.User;
import ie.cit.adf.muss.services.UserService;
import ie.cit.adf.muss.utility.FileFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ChObject
 */
@EnableAspectJAutoProxy
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
    	try {
    		String objectsDirectory = AbstractChObjectLoader.class.getResource('/' + this.objectsDirectory).getPath();
            objectsDirectory = URLDecoder.decode(objectsDirectory, "utf-8");
            return FileFinder.getFileList(objectsDirectory, "*." + this.extension);
    	} catch (Exception e) {
    		String objectsDirectory = AbstractChObjectLoader.class.getResource('/' + this.objectsDirectory).toURI().toString().replace("file:/", "");
            objectsDirectory = URLDecoder.decode(objectsDirectory, "utf-8");
            return FileFinder.getFileList(objectsDirectory, "*." + this.extension);
    	}    
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
        return objects;
    }

}
