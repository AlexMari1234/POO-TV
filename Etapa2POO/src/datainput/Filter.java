package datainput;
import filtersactions.FilterMovies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Filter {
    private Sort sort;
    private Contains contains;

    /**
     * return sort
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * set sort
     */
    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    /**
     * return contains
     */
    public Contains getContains() {
        return contains;
    }

    /**
     * apply strategy design pattern for filters
     */
    public ArrayList<Movies> applyFilterStrategy(final FilterMovies filterStrategy,
                                                 final ArrayList<Movies> movies,
                                                 final ArrayList<String> data) {
        return filterStrategy.searchMovies(movies, data);
    }

    /**
     * Sorts a list of Movies objects by rating and duration.
     * @param filter specifies how the sorting should be done
     * @param currentMovies the list of Movies objects to be sorted
     */
    public static void sortByRatingAndDuration(final Filter filter,
                                               final ArrayList<Movies> currentMovies) {
        if (filter.getSort().getDuration().equals("increasing")) {
            if (filter.getSort().getRating().equals("increasing")) {
                Collections.sort(currentMovies, new Comparator<Movies>() {
                    @Override
                    public int compare(final Movies o1, final Movies o2) {
                        if (o1.getDuration() == o2.getDuration()) {
                            return Double.compare(o1.getRating(), o2.getRating());
                        } else {
                            return Integer.compare(o1.getDuration(), o2.getDuration());
                        }
                    }
                });
            } else {
                Collections.sort(currentMovies, new Comparator<Movies>() {
                    @Override
                    public int compare(final Movies o1, final Movies o2) {
                        if (o1.getDuration() == o2.getDuration()) {
                            return Double.compare(o2.getRating(), o1.getRating());
                        } else {
                            return Integer.compare(o1.getDuration(), o2.getDuration());
                        }
                    }
                });
            }
        } else {
            if (filter.getSort().getRating().equals("increasing")) {
                Collections.sort(currentMovies, new Comparator<Movies>() {
                    @Override
                    public int compare(final Movies o1, final Movies o2) {
                        if (o1.getDuration() == o2.getDuration()) {
                            return Double.compare(o1.getRating(), o2.getRating());
                        } else {
                            return Integer.compare(o2.getDuration(), o1.getDuration());
                        }
                    }
                });
            } else {
                Collections.sort(currentMovies, new Comparator<Movies>() {
                    @Override
                    public int compare(final Movies o1, final Movies o2) {
                        if (o1.getDuration() == o2.getDuration()) {
                            return Double.compare(o2.getRating(), o1.getRating());
                        } else {
                            return Integer.compare(o2.getDuration(), o1.getDuration());
                        }
                    }
                });
            }
        }
    }
}
