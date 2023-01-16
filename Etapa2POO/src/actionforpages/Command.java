package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public abstract class Command {
    abstract void executeCommand(ObjectMapper objectMapper, ArrayNode output);
}
