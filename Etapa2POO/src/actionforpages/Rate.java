package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Actions;
import datainput.Movies;
import outputformat.StandardError;

public class Rate extends Command {

    private CurrentPage currentPage;
    private DataBase inputData;
    private Actions action;
    public Rate(final CurrentPage currentPage, final DataBase inputData, final Actions action) {
        this.currentPage = currentPage;
        this.inputData = inputData;
        this.action = action;
    }
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.rateMovie(action.getRate(), currentPage, currentPage.getCurrentMovie(),
                    inputData, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
