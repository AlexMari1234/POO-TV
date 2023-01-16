import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.node.ArrayNode;
import database.DataBase;


public class Main {

    public Main() {

    }

    /**
     *The main method of the program. It takes in two arguments, the input file path and
     * the output file path.
     *The input file is read and deserialized into a DataBase object, and the commands
     * are executed on it.
     *The output is then serialized and written to the specified output file path.
     *@param args command line arguments, the input file path and the output file path
     *@throws IOException if there is an error reading or writing to the input and output files
     */
    public static void main(final String[] args) throws IOException  {
        if (args.length >= 2) {
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
