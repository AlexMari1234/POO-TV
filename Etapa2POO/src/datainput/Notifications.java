package datainput;

public class Notifications {

    private String movieName;
    private String message;

    public Notifications(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
