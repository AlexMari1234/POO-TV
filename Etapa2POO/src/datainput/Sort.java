package datainput;

import sortactions.SortMovies;

import java.util.ArrayList;

public class Sort {
    private String rating;
    private String duration;

    public Sort() {

    }

    /**
     * return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * set rating
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }

    /**
     * return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * rset duration
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }

    /**
     * apply strategy design pattern for sorting by duration or rating
     */
    public void applySortStrategy(final SortMovies sortStrategy,
                                  final ArrayList<Movies> movies, final String order) {
        sortStrategy.sort(movies, order);
    }
}
