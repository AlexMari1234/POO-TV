package filtersactions;
import datainput.Movies;

import java.util.ArrayList;

public class FilterByName implements FilterMovies {
    /**
     * Searches for movies in a given list based on a list of names of names
     * @param inputMovies the list of movies to search
     * @param conditionData the list of conditions to use for the search
     * @return a list of movies that match the search conditions
     */
    @Override
    public ArrayList<Movies> searchMovies(final ArrayList<Movies> inputMovies,
                                          final ArrayList<String> conditionData) {
        ArrayList<Movies> acceptedMovies = new ArrayList<Movies>();
        for (int i = 0; i < inputMovies.size(); i++) {
            String currentMovieName = inputMovies.get(i).getName();
            if (currentMovieName.length() < conditionData.get(0).length()) {
                continue;
            }

            if (currentMovieName.substring(0, conditionData.get(0).length())
                    .equals(conditionData.get(0))) {
                acceptedMovies.add(inputMovies.get(i));
            }
        }

        return acceptedMovies;
    }
}
