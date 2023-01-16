package filtersactions;

import datainput.Movies;

import java.util.ArrayList;

public class FilterByGenre implements FilterMovies {
    /**
     * Searches for movies in a given list based on a list of names of genres
     * @param inputMovies the list of movies to search
     * @param conditionData the list of conditions to use for the search
     * @return a list of movies that match the search conditions
     */
    @Override
    public ArrayList<Movies> searchMovies(final ArrayList<Movies> inputMovies,
                                          final ArrayList<String> conditionData) {
        ArrayList<Movies> acceptedMovies = new ArrayList<Movies>();
        for (int i = 0; i < inputMovies.size(); i++) {
            if (inputMovies.get(i).getGenres() != null) {
                for (int j = 0; j < conditionData.size(); j++) {
                    if (inputMovies.get(i).getGenres().contains(conditionData.get(j))) {
                        acceptedMovies.add(inputMovies.get(i));
                        break;
                    }
                }
            }
        }

        return acceptedMovies;
    }
}
