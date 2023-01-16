package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Movies;
import outputformat.StandardError;

public class Watch extends Command {

    private CurrentPage currentPage;

    public Watch(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.watchMovie(currentPage, currentPage.getCurrentMovie(),
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
