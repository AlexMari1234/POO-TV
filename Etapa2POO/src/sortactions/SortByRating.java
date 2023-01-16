package sortactions;

import datainput.Movies;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByRating implements SortMovies {
    /**
     * sort list of movies by rating in one specific order
     */
    @Override
    public void sort(final ArrayList<Movies> movies, final String order) {
        if (order.equals("increasing")) {
            movies.sort(Comparator.comparingDouble(Movies::getRating));
        } else {
            movies.sort(Comparator.comparingDouble(Movies::getRating).reversed());
        }
    }
}
