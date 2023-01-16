package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class Command {
    /**
     * common function to execute each command
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    abstract void executeCommand(ObjectMapper objectMapper, ArrayNode output);
}
