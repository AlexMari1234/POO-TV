package sortactions;

import datainput.Movies;

import java.util.ArrayList;

public interface SortMovies {
    /**
     * sort the list of movies in one specific order
     */
    void sort(ArrayList<Movies> movies, String order);
}
