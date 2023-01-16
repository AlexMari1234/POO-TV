package filtersactions;

import datainput.Movies;

import java.util.ArrayList;

public interface FilterMovies {
    /**
     * search movies based on one type of filter
     * @param inputMovies represents the list of movies
     * @param conditionData represents the input data
     * @return
     */
    ArrayList<Movies> searchMovies(ArrayList<Movies> inputMovies,
                                   ArrayList<String> conditionData);
}
