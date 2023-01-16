import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.DataBase;


public class Main {

    public Main() {

    }
    public static void main(final String[] args) throws IOException  {
        if (args.length >= 2) {
            System.out.println(args[0]);
            ObjectMapper objectMapper = new ObjectMapper();
            DataBase inputData = DataBase.getInstance();
            inputData = objectMapper.readValue(new File(args[0]), DataBase.class);
            ArrayNode output = objectMapper.createArrayNode();
            SolveCommands.commands(inputData, output, objectMapper);
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(new File(args[1]), output);
        }
    }
}
