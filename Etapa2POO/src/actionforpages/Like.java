package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Movies;
import outputformat.StandardError;

public class Like extends Command {

    private CurrentPage currentPage;
    private DataBase inputData;

    /**
     * copy currentPage and input data
     */
    public Like(final CurrentPage currentPage, final DataBase inputData) {
        this.currentPage = currentPage;
        this.inputData = inputData;
    }

    /**
     * Makes a like of a movie.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.likeMovie(currentPage, currentPage.getCurrentMovie(), inputData,
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
