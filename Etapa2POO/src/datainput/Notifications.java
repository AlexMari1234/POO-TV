package datainput;

public class Notifications {

    private String movieName;
    private String message;

    /**
     * copy movie name and message
     */
    public Notifications(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    /**
     * return movie name
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * set movie name
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }

    /**
     * return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
