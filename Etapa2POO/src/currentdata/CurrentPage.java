package currentdata;

import datainput.Movies;

import java.util.ArrayList;

public class CurrentPage {
    private String name;
    private ArrayList<Movies> moviesList;
    private Movies currentMovie;
    private CurrentUser currentUser;

    /**
     * copy the name
     */
    public CurrentPage(final String name) {
        this.name = name;
        this.currentUser = CurrentUser.getInstance();
    }

    public CurrentPage(CurrentPage currentPage) {
        this.name = currentPage.getName();
        this.moviesList = new ArrayList<Movies>(currentPage.getMoviesList());
        if (currentPage.getCurrentMovie() != null) {
            this.currentMovie = new Movies(currentPage.getCurrentMovie());
        }
        this.currentUser = CurrentUser.getInstance();
        this.currentUser.setUser(currentPage.getCurrentUser().getUser());
    }

    /**
     * return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * return movies list
     */
    public ArrayList<Movies> getMoviesList() {
        return moviesList;
    }

    /**
     * set movies list
     */
    public void setMoviesList(final ArrayList<Movies> moviesList) {
        if (moviesList == null) {
            this.moviesList = new ArrayList<Movies>();
        } else {
            this.moviesList = new ArrayList<Movies>(moviesList);
        }
    }

    /**
     * return current movie
     */
    public Movies getCurrentMovie() {
        return currentMovie;
    }

    /**
     * set current movie
     */
    public void setCurrentMovie(final Movies currentMovie) {

        this.currentMovie = currentMovie;
    }

    /**
     * return current user
     */
    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    /**
     * set current user
     */
    public void setCurrentUser(final CurrentUser currentUser) {

        this.currentUser = currentUser;
    }
}
