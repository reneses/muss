package ie.cit.adf.muss.utility;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Json Mapper
 */
public abstract class JsonMapper {

    /**
     * Map a file to a class
     *
     * @param file
     * @param targetClass
     * @param <T>
     * @return Converted file
     */
    public static <T> T mapToClass(File file, Class<T> targetClass) {
        T object = null;
        try {
            object = new ObjectMapper().readValue(file, targetClass);
        } catch (JsonParseException e) {
            System.out.println("Error parsing the file.");
            e.printStackTrace();
        } catch (JsonMappingException e) {
            System.out.println("Error mapping to Java object.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unknown I/O error.");
        }
        return object;
    }

}
