package datainput;

public class Recommendation {

    private String genre;
    private int likes;

    public Recommendation(final String genre, final Integer likes) {
        this.genre = genre;
        this.likes = likes;
    }

    /**
     * return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * set genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * return likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * set likes
     */
    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
