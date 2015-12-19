package ie.cit.adf.muss.loaders;

import java.nio.file.Path;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.utility.JsonMapper;

/**
 * Json implementation of the Object loader
 */
@EnableAspectJAutoProxy
@Component
public class ChObjectJsonLoader extends AbstractChObjectLoader {

    private static final String EXTENSION = "json";
    private static final String OBJECTS_DIRECTORY = "objects";


    /**
     * Json object loader constructor
     */
    public ChObjectJsonLoader() {
        super(EXTENSION, OBJECTS_DIRECTORY);
    }


    /**
     * Map a json file to a chobject instance
     *
     * @param path
     * @return
     */
    protected ChObject mapFile(String path) {
        ChObject object = JsonMapper.mapToClass(path, ChObject.class);
        if (object.getImages().isEmpty())
            return null;
        return object;
    }

}
