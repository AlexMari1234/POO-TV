package datainput;

public class Recommendation {

    private String genre;
    private int likes;

    public Recommendation(final String genre, final Integer likes) {
        this.genre = genre;
        this.likes = likes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }
}
