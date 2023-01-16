package datainput;

public class Rating {

    private int rating;
    private String movie;

    public Rating(final int rating, final String movie) {
        this.rating = rating;
        this.movie = movie;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }
}
