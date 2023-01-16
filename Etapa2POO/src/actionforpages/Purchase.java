package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Movies;
import outputformat.StandardError;

public class Purchase extends Command {
    private CurrentPage currentPage;

    /**
     * copy current page
     */
    public Purchase(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Makes a purchase of a movie.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.purchaseMovie(currentPage, currentPage.getCurrentMovie(),
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
