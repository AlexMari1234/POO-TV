package sortactions;

import datainput.Movies;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByLikes implements SortMovies {
    @Override
    public void sort(final ArrayList<Movies> movies, final String order) {
        if (order.equals("increasing")) {
            movies.sort(Comparator.comparingInt(Movies::getNumLikes));
        } else {
            movies.sort(Comparator.comparingInt(Movies::getNumLikes).reversed());
        }
    }
}
