package datainput;

import java.util.ArrayList;

public class Actions {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private String startsWith;
    private Filter filters;
    private String objectType;
    private ArrayList<Movies> movies;
    private int count;
    private String movie;
    private int rate;

    private Movies addedMovie;

    private String deletedMovie;

    private String subscribedGenre;

    /**
     * default constructor
     */
    public Actions() {

    }

    /**
     * return type
     */
    public String getType() {
        return type;
    }

    /**
     * set type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * return page
     */
    public String getPage() {
        return page;
    }

    /**
     * set page
     */
    public void setPage(final String page) {
        this.page = page;
    }

    /**
     * return credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * set credentials
     */
    public void setCredentials(final Credentials credentials) {

        this.credentials = credentials;
    }

    /**
     * return feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * set feature
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }

    /**
     * return startswith
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * return filters
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     * return movies
     */
    public ArrayList<Movies> getMovies() {
        return movies;
    }

    /**
     * set movies
     */
    public void setMovies(final ArrayList<Movies> movies) {
        this.movies = movies;
    }

    /**
     * return count
     */
    public int getCount() {
        return count;
    }

    /**
     * return movie
     */
    public String getMovie() {
        return movie;
    }

    /**
     * return rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * return deleted movie
     */
    public String getDeletedMovie() {
        return deletedMovie;
    }

    /**
     * set deleted movie
     */
    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    /**
     * return added movie
     */
    public Movies getAddedMovie() {
        return addedMovie;
    }

    /**
     * set added movie
     */
    public void setAddedMovie(final Movies addedMovie) {
        this.addedMovie = addedMovie;
    }

    /**
     * return subscribed genres
     */
    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    /**
     * set subscribed genres
     */
    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }
}
