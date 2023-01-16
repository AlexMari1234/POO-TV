package datainput;

import java.util.ArrayList;

public class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;
    private ArrayList<String> country;

    /**
     * default constructor
     */
    public Contains() {

    }

    /**
     * return actors
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * set actors
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * return genre
     */
    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * set genre
     */
    public ArrayList<String> getCountry() {
        return country;
    }

    /**
     * set country
     */
    public void setCountry(final ArrayList<String> country) {
        this.country = country;
    }
}
