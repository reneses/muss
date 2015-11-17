package ie.cit.adf.muss.loaders;


import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.utility.FileFinder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
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
     */
    protected List<Path> loadFiles() throws IOException {
        String objectsDirectory = AbstractChObjectLoader.class.getResource('/' + this.objectsDirectory).getPath();
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
     */
    public List<ChObject> loadChObjects() throws IOException {
        return loadFiles().stream().map(this::mapFile).collect(Collectors.toList());
    }

}
