package outputformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
public class StandardError {
    /**
     * prints a standard error message at output
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void printerror(final ObjectMapper objectMapper, final ArrayNode output) {
        ObjectNode currentObject = objectMapper.createObjectNode();
        currentObject.put("error", "Error");
        ArrayNode moviesList = objectMapper.createArrayNode();
        currentObject.set("currentMoviesList", moviesList);
        currentObject.set("currentUser", null);
        output.add(currentObject);
    }
}
