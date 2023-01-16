package datainput;

import database.DataBase;

import java.util.ArrayList;

public class Users {
    static final int FIFTEEN = 15;
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = FIFTEEN;
    private ArrayList<Movies> purchasedMovies;
    private ArrayList<Movies> watchedMovies;
    private ArrayList<Movies> likedMovies;
    private ArrayList<Movies> ratedMovies;

    private ArrayList<Notifications> notifications;

    private ArrayList<String> subscribedGenres;

    /**
     * default constructor
     */
    public Users() {
    }

    /**
     * copy credentials for user
     */
    public Users(final String name, final String password, final String accountType,
                 final String country, final String balance) {
        this.credentials = new Credentials(name, password, accountType, country, balance);
    }

    /**
     * reset all data from fields
     */
    public void resetUser() {
        this.purchasedMovies = new ArrayList<Movies>();
        this.watchedMovies = new ArrayList<Movies>();
        this.likedMovies = new ArrayList<Movies>();
        this.ratedMovies = new ArrayList<Movies>();
        this.notifications = new ArrayList<Notifications>();
        this.subscribedGenres = new ArrayList<String>();
        this.tokensCount = 0;
        this.numFreePremiumMovies = FIFTEEN;
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
     * return tokens count
     */
    public int getTokensCount() {
        return tokensCount;
    }

    /**
     * set tokens count
     */
    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    /**
     * return number of premium movies
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /**
     * set number of premium movies
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * return purchased movies
     */
    public ArrayList<Movies> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * set purchased movies
     */
    public void setPurchasedMovies(final ArrayList<Movies> purchasedMovies) {

        this.purchasedMovies = purchasedMovies;
    }

    /**
     * return watched movies
     */
    public ArrayList<Movies> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * set watched movies
     */
    public void setWatchedMovies(final ArrayList<Movies> watchedMovies) {

        this.watchedMovies = watchedMovies;
    }

    /**
     * return liked movies
     */
    public ArrayList<Movies> getLikedMovies() {
        return likedMovies;
    }

    /**
     * set liked movies
     */
    public void setLikedMovies(final ArrayList<Movies> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * return rated movies
     */
    public ArrayList<Movies> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * set rated movies
     */
    public void setRatedMovies(final ArrayList<Movies> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public void setSubscribedGenres(final ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }

    /**
     * decrement number of premium movies
     */
    public void decrementNumFreePremiumMovies() {
        --this.numFreePremiumMovies;
    }

    /**
     * decrement tokens count
     */
    public void decrementTokensCount(final int numTokens) {
        this.tokensCount -= numTokens;
    }

    /**
     * Checks if a user with the given credentials exists in the input data.
     * @param actionCredentials the credentials to check
     * @param inputData the data to search for the user
     * @return the user with the matching credentials, or null if no such user is found
     */
    public static Users checkUserInDataBase(final Credentials actionCredentials,
                                            final DataBase inputData) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            if (currentUser.getCredentials().getName().equals(
                    actionCredentials.getName())) {
                if (currentUser.getCredentials().getPassword().equals(
                        actionCredentials.getPassword())) {
                    return currentUser;
                }
            }
        }

        return (Users) null;
    }
}
