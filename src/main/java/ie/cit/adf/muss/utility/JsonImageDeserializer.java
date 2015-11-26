package ie.cit.adf.muss.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import ie.cit.adf.muss.domain.Image;
import ie.cit.adf.muss.domain.ImageSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Custom JSON deserializer for Image entities
 */
public class JsonImageDeserializer extends JsonDeserializer<Image> {

    @Override
    public Image deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);

        Image image = new Image();

        // Go through all the sizes
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {

            // Unwrap the entities
            Map.Entry<String, JsonNode> entry = fields.next();
            String sizeLabel = entry.getKey();
            JsonNode sizeNode = entry.getValue();

            // If it is the first size, initialize the common properties
            if (image.getSizes().isEmpty()) {
                image.setPrimary(
                        sizeNode.get("is_primary").textValue().equals("1")
                );
                image.setOriginalId(
                        Integer.parseInt(sizeNode.get("image_id").textValue())
                );
            }

            // Create and store the size
            ImageSize size = new ImageSize();
            size.setLabel(sizeLabel);
            size.setWidth(
                    sizeNode.get("width").intValue()
            );
            size.setHeight(
                    sizeNode.get("height").intValue()
            );
            size.setUrl(
                    sizeNode.get("url").textValue()
            );
            image.getSizes().add(size);

        }

        return image;
    }
}