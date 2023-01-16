package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import outputformat.StandardError;
import outputformat.StandardOutput;

import java.util.ArrayList;

public class ActionForBackPage {
    public static void executeAction(final CurrentPage currentPage, final ObjectMapper objectMapper,
                                     final ArrayNode output, final ArrayList<CurrentPage> pages) {
        if (pages.size() > 1) {
            pages.remove(pages.size() - 1);
            currentPage.setName(pages.get(pages.size() - 1).getName());
            currentPage.getCurrentUser().setUser(pages.get(pages.size() - 1)
                    .getCurrentUser().getUser());
            currentPage.setCurrentMovie(pages.get(pages.size() - 1).getCurrentMovie());
            currentPage.setMoviesList(pages.get(pages.size() - 1).getMoviesList());
            if (currentPage.getName().equals("movies")) {
                StandardOutput.printoutput(currentPage, objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
