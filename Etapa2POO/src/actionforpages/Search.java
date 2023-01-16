package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Actions;
import datainput.Filter;
import datainput.Movies;
import filtersactions.FilterByName;
import outputformat.StandardError;
import outputformat.StandardOutput;

import java.util.ArrayList;

public class Search extends Command {
    private CurrentPage currentPage;
    private Actions action;

    /**
     * copy current page and action
     */
    public Search(final CurrentPage currentPage, final Actions action) {
        this.currentPage = currentPage;
        this.action = action;
    }

    /**
     * Handles the search action by applying a name-based filter to the movies list in the
     * current page and setting the resulting list as the new movies list in the current page.
     * If the conditions for search are not met or the search fails, it outputs an error.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("movies")
                && currentPage.getCurrentUser().getUser() != null) {
            FilterByName filterByName = new FilterByName();
            Filter newFilter = new Filter();
            ArrayList<String> currentData = new ArrayList<String>();
            currentData.add(action.getStartsWith());
            ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(filterByName,
                    currentPage.getMoviesList(), currentData);
            currentPage.setMoviesList(acceptedMovies);
            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
