package datainput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import outputformat.StandardError;
import outputformat.StandardOutput;
import java.util.ArrayList;

public class Movies {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;

    private ArrayList<Rating> ratingList;

    public Movies() {

    }

    /**
     * copy constructor for movies
     */
    public Movies(final Movies movies) {
        this.name = movies.name;
        this.rating = movies.rating;
        this.numRatings = movies.numRatings;
        this.year = movies.year;
        this.numLikes = movies.numLikes;
        this.duration = movies.duration;
        this.genres = new ArrayList<String>(movies.genres);
        this.actors = new ArrayList<String>(movies.actors);
        this.countriesBanned = new ArrayList<String>(movies.countriesBanned);
    }

    /**
     * reset likes, rating and the number of ratings
     */
    public void resetMovies() {
        this.numLikes = 0;
        this.numRatings = 0;
        this.rating = 0;
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
     * return year
     */
    public String getYear() {
        return year;
    }

    /**
     * sets year
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * set duration
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * return genres
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * set genres
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
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
     * return countries banned
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * set countries banned
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    /**
     * return number of likes
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * set number of likes
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * set rating
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     * return number of ratings
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * set number of ratings
     */
    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * increase number of likes
     */
    public void incrementNumLikes() {
        ++this.numLikes;
    }

    /**
     * increase number of ratings
     */
    public void incrementNumRatings() {
        ++this.numRatings;
    }

    /**
     * return rating list
     */
    public ArrayList<Rating> getRatingList() {
        return ratingList;
    }

    /**
     * set rating list
     */
    public void setRatingList(final ArrayList<Rating> ratingList) {

        this.ratingList = ratingList;
    }

    /**
     * prints at output the current movie by actualising the current list of movies
     * @param currentPage the current page object
     * @param objectMapper the Jackson object mapper
     * @param output the Jackson array node to store the output data
     */
    public static void printAtOutputMovie(final CurrentPage currentPage,
                                          final ObjectMapper objectMapper,
                                          final ArrayNode output) {
        if (currentPage.getCurrentMovie() != null) {
            ArrayList<Movies> singleMovie = new ArrayList<Movies>();
            singleMovie.add(currentPage.getCurrentMovie());
            currentPage.setMoviesList(singleMovie);
            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Purchase a movie for the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to purchase
     * @param objectMapper an object mapper for serializing output
     * @param output an ArrayNode to hold the serialized output
     */
    public static void purchaseMovie(final CurrentPage currentPage, final Movies movie,
                                     final ObjectMapper objectMapper, final ArrayNode output) {
        if (checkPurchasedMovie(currentPage, movie) == 0) {
            StandardError.printerror(objectMapper, output);
        } else if (currentPage.getCurrentUser().getUser().getCredentials()
                .getAccountType().equals("premium") && currentPage.getCurrentUser()
                .getUser().getNumFreePremiumMovies() > 0) {

            currentPage.getCurrentUser().getUser().decrementNumFreePremiumMovies();
            if (currentPage.getCurrentUser().getUser().getPurchasedMovies() == null) {
                currentPage.getCurrentUser().getUser().setPurchasedMovies(new ArrayList<Movies>());
            }

            currentPage.getCurrentUser().getUser().getPurchasedMovies().add(new Movies(movie));
            printAtOutputMovie(currentPage, objectMapper, output);
        } else if (currentPage.getCurrentUser().getUser().getTokensCount() < 2) {
            StandardError.printerror(objectMapper, output);
        } else {
            currentPage.getCurrentUser().getUser().decrementTokensCount(2);
            if (currentPage.getCurrentUser().getUser().getPurchasedMovies() == null) {
                currentPage.getCurrentUser().getUser().setPurchasedMovies(new ArrayList<Movies>());
            }

            currentPage.getCurrentUser().getUser().getPurchasedMovies().add(new Movies(movie));
            printAtOutputMovie(currentPage, objectMapper, output);
        }
    }

    /**
     * Watch a movie for the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to purchase
     * @param objectMapper an object mapper for serializing output
     * @param output an ArrayNode to hold the serialized output
     */
    public static void watchMovie(final CurrentPage currentPage, final Movies movie,
                                  final ObjectMapper objectMapper, final ArrayNode output) {
        /*if (checkWatchedMovie(currentPage, movie) == 1) {
            if (currentPage.getCurrentUser().getUser().getWatchedMovies() == null) {
                currentPage.getCurrentUser().getUser().setWatchedMovies(new ArrayList<Movies>());
            }

            currentPage.getCurrentUser().getUser().getWatchedMovies().add(new Movies(movie));
            printAtOutputMovie(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }*/

        if (checkPurchasedMovie(currentPage, movie) == 0) {
            if (checkWatchedMovie(currentPage, movie) == 0) {
                if (currentPage.getCurrentUser().getUser().getWatchedMovies() == null) {
                    currentPage.getCurrentUser().getUser().setWatchedMovies(
                            new ArrayList<Movies>());
                }

                currentPage.getCurrentUser().getUser().getWatchedMovies().add(new Movies(movie));
                printAtOutputMovie(currentPage, objectMapper, output);
            } else {
                printAtOutputMovie(currentPage, objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Like a movie for the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to purchase
     * @param objectMapper an object mapper for serializing output
     * @param output an ArrayNode to hold the serialized output
     */
    public static void likeMovie(final CurrentPage currentPage, final Movies movie,
                                 final DataBase inputData, final ObjectMapper objectMapper,
                                 final ArrayNode output) {
        if (checkWatchedMovie(currentPage, movie) == 1
                && checkLikedMovie(currentPage, movie) == 1) {
            movie.incrementNumLikes();

            DataBase.modifyDataBasePurchasedMovie(inputData, movie);
            DataBase.modifyDataBaseWatchedMovie(inputData, movie);
            DataBase.modifyDataBaseLikedMovie(inputData, movie);
            DataBase.modifyDataBaseRatedMovie(inputData, movie);

            if (currentPage.getCurrentUser().getUser().getLikedMovies() == null) {
                currentPage.getCurrentUser().getUser().setLikedMovies(new ArrayList<Movies>());
            }

            currentPage.getCurrentUser().getUser().getLikedMovies().add(new Movies(movie));
            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Rate a movie for the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to purchase
     * @param objectMapper an object mapper for serializing output
     * @param output an ArrayNode to hold the serialized output
     */
    public static void rateMovie(final int rating, final CurrentPage currentPage,
                                 final Movies movie, final DataBase inputData,
                                 final ObjectMapper objectMapper, final ArrayNode output) {
        if (checkWatchedMovie(currentPage, movie) == 1
                && rating >= 1 && rating <= 5) {
            if (checkRatedMovie(currentPage, movie) == 1) {
                movie.incrementNumRatings();
                if (movie.getRatingList() == null) {
                    movie.setRatingList(new ArrayList<Rating>());
                }

                Rating newRating = new Rating(rating,
                        currentPage.getCurrentUser().getUser().getCredentials().getName());

                movie.getRatingList().add(newRating);
                double sum = 0.00;
                for (int i = 0; i < movie.getRatingList().size(); i++) {
                    sum += movie.getRatingList().get(i).getRating();
                }

                movie.setRating(sum / movie.getNumRatings());

                DataBase.modifyDataBasePurchasedMovie(inputData, movie);
                DataBase.modifyDataBaseWatchedMovie(inputData, movie);
                DataBase.modifyDataBaseLikedMovie(inputData, movie);
                DataBase.modifyDataBaseRatedMovie(inputData, movie);

                if (currentPage.getCurrentUser().getUser().getRatedMovies() == null) {
                    currentPage.getCurrentUser().getUser().setRatedMovies(new ArrayList<Movies>());
                }

                currentPage.getCurrentUser().getUser().getRatedMovies().add(new Movies(movie));
                StandardOutput.printoutput(currentPage, objectMapper, output);
            } else {
                for (int i = 0; i < movie.getRatingList().size(); i++) {
                    if (movie.getRatingList().get(i).getMovie().equals(
                            currentPage.getCurrentUser().getUser().getCredentials().getName())) {
                        movie.getRatingList().get(i).setRating(rating);
                        break;
                    }
                }

                double sum = 0.00;
                for (int i = 0; i < movie.getRatingList().size(); i++) {
                    sum += movie.getRatingList().get(i).getRating();
                }

                movie.setRating(sum / movie.getNumRatings());

                DataBase.modifyDataBasePurchasedMovie(inputData, movie);
                DataBase.modifyDataBaseWatchedMovie(inputData, movie);
                DataBase.modifyDataBaseLikedMovie(inputData, movie);
                DataBase.modifyDataBaseRatedMovie(inputData, movie);

                StandardOutput.printoutput(currentPage, objectMapper, output);

            }

        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Check if a movie has already been purchased by the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to check for purchase
     * @return 0 if the movie has been purchased, 1 otherwise
     */
    public static int checkPurchasedMovie(final CurrentPage currentPage, final Movies movie) {
        if (currentPage.getCurrentUser().getUser().getPurchasedMovies() == null) {
            return 1;
        }

        for (int i = 0; i < currentPage.getCurrentUser().getUser()
                .getPurchasedMovies().size(); i++) {
            if (currentPage.getCurrentUser().getUser().getPurchasedMovies().get(i)
                    .getName().equals(movie.getName())) {
                return 0;
            }
        }

        return 1;
    }


    /**
     * Check if a movie has not already been watched by the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to check for purchase
     * @return 0 if the movie has been purchased, 1 otherwise
     */
    public static int checkWatchedMovie(final CurrentPage currentPage, final Movies movie) {
        if (currentPage.getCurrentUser().getUser().getWatchedMovies() == null) {
            return 0;
        }

        for (int i = 0; i < currentPage.getCurrentUser().getUser().getWatchedMovies().size(); i++) {
            if (currentPage.getCurrentUser().getUser().getWatchedMovies().get(i).getName()
                    .equals(movie.getName())) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Check if a movie has already been liked by the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to check for purchase
     * @return 0 if the movie has been purchased, 1 otherwise
     */
    public static int checkLikedMovie(final CurrentPage currentPage, final Movies movie) {
        if (currentPage.getCurrentUser().getUser().getLikedMovies() == null) {
            return 1;
        }

        for (int i = 0; i < currentPage.getCurrentUser().getUser().getLikedMovies().size(); i++) {
            if (currentPage.getCurrentUser().getUser().getLikedMovies().get(i).getName()
                    .equals(movie.getName())) {
                return 0;
            }
        }

        return 1;
    }

    /**
     * Check if a movie has already been rated by the current user.
     * @param currentPage the current page of the application
     * @param movie the movie to check for purchase
     * @return 0 if the movie has been purchased, 1 otherwise
     */
    public static int checkRatedMovie(final CurrentPage currentPage, final Movies movie) {
        if (currentPage.getCurrentUser().getUser().getRatedMovies() == null) {
            return 1;
        }

        for (int i = 0; i < currentPage.getCurrentUser().getUser().getRatedMovies().size(); i++) {
            if (currentPage.getCurrentUser().getUser().getRatedMovies().get(i).getName()
                    .equals(movie.getName())) {
                return 0;
            }
        }

        return 1;
    }


    public static Movies searchMovie(final ArrayList<Movies> currentMovies,
                                     final String searchedMovie) {
        for (int i = 0; i < currentMovies.size(); i++) {
            if (currentMovies.get(i).getName().equals(searchedMovie)) {
                return currentMovies.get(i);
            }
        }

        return (Movies) null;
    }

}
