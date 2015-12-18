package ie.cit.adf.muss.loaders;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.utility.FileFinder;

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
        return loadFiles().stream().map(this::mapFile).filter(o -> o != null).collect(Collectors.toList());
    }

}
