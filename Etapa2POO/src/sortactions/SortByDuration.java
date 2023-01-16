package sortactions;

import datainput.Movies;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByDuration implements SortMovies {
    /**
     * sort list of movies by duration in one specific order
     */
    @Override
    public void sort(final ArrayList<Movies> movies, final String order) {
        if (order.equals("increasing")) {
            movies.sort(Comparator.comparingInt(Movies::getDuration));
        } else {
            movies.sort(Comparator.comparingInt(Movies::getDuration).reversed());
        }
    }
}
