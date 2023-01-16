package datainput;

public class Rating {

    private int rating;
    private String movie;

    public Rating(final int rating, final String movie) {
        this.rating = rating;
        this.movie = movie;
    }

    /**
     * return reting
     */
    public int getRating() {
        return rating;
    }

    /**
     * set rating
     */
    public void setRating(final int rating) {
        this.rating = rating;
    }

    /**
     * return movie
     */
    public String getMovie() {
        return movie;
    }

    /**
     * set movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }
}
