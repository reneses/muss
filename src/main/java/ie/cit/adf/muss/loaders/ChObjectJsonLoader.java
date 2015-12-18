package ie.cit.adf.muss.loaders;

import ie.cit.adf.muss.domain.ChObject;
import ie.cit.adf.muss.utility.JsonMapper;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

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
    protected ChObject mapFile(Path path) {
        return JsonMapper.mapToClass(path.toFile(), ChObject.class);
    }

}
