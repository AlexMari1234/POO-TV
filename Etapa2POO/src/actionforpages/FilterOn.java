package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Actions;
import datainput.Filter;
import datainput.Movies;
import datainput.Sort;
import filtersactions.FilterByActors;
import filtersactions.FilterByCountry;
import filtersactions.FilterByGenre;
import outputformat.StandardError;
import outputformat.StandardOutput;
import sortactions.SortByDuration;
import sortactions.SortByRating;

import java.util.ArrayList;

public class FilterOn extends Command {

    private CurrentPage currentPage;
    private DataBase inputData;
    private Actions action;

    public FilterOn(final CurrentPage currentPage, final DataBase inputData,
                    final Actions action) {
        this.currentPage = currentPage;
        this.inputData = inputData;
        this.action = action;
    }

    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("movies")) {
            Filter filter = action.getFilters();
            currentPage.setMoviesList(FilterByCountry.acceptedCountries(
                    inputData.getMovies(), currentPage.getCurrentUser().getUser()));

            if (filter.getContains() != null) {
                if (filter.getContains().getActors() != null) {
                    FilterByActors filterByActors = new FilterByActors();
                    Filter newFilter = new Filter();
                    ArrayList<String> currentData = new ArrayList<String>(
                            filter.getContains().getActors());
                    ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(
                            filterByActors, currentPage.getMoviesList(), currentData);
                    currentPage.setMoviesList(acceptedMovies);
                }

                if (filter.getContains().getGenre() != null) {
                    FilterByGenre filterByGenre = new FilterByGenre();
                    Filter newFilter = new Filter();
                    ArrayList<String> currentData = new ArrayList<String>(
                            filter.getContains().getGenre());
                    ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(filterByGenre,
                            currentPage.getMoviesList(), currentData);
                    currentPage.setMoviesList(acceptedMovies);
                }
            }

            if (filter.getSort() != null) {
                ArrayList<Movies> currentMovies = currentPage.getMoviesList();
                if (filter.getSort().getDuration() != null
                        && filter.getSort().getRating() != null) {
                    Filter.sortByRatingAndDuration(filter, currentMovies);
                } else if (filter.getSort().getDuration() != null) {
                    SortByDuration sortByDuration = new SortByDuration();
                    Sort newSort = new Sort();
                    newSort.applySortStrategy(sortByDuration, currentPage.getMoviesList(),
                            filter.getSort().getDuration());
                } else if (filter.getSort().getRating() != null) {
                    SortByRating sortByRating = new SortByRating();
                    Sort newSort = new Sort();
                    newSort.applySortStrategy(sortByRating, currentPage.getMoviesList(),
                            filter.getSort().getRating());
                }
            }

            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
