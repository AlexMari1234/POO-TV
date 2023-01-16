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

    public Like(final CurrentPage currentPage, final DataBase inputData) {
        this.currentPage = currentPage;
        this.inputData = inputData;
    }
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.likeMovie(currentPage, currentPage.getCurrentMovie(), inputData,
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
