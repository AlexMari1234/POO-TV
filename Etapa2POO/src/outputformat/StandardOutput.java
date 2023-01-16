package outputformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import currentdata.CurrentPage;
import datainput.Movies;
import datainput.Users;

import java.util.ArrayList;

public class StandardOutput {
    /**
     * Prints the output data for the current page.
     * @param currentPage the current page object
     * @param objectMapper the Jackson object mapper
     * @param output the Jackson array node to store the output data
     */
    public static void printoutput(final CurrentPage currentPage, final ObjectMapper objectMapper,
                                   final ArrayNode output) {
        ObjectNode currentObject = objectMapper.createObjectNode();
        currentObject.set("error", null);

        ArrayNode currentArray = objectMapper.createArrayNode();
        if (currentPage.getMoviesList() != null) {
            for (int i = 0; i < currentPage.getMoviesList().size(); i++) {
                ObjectNode newObject = objectMapper.createObjectNode();
                Movies currentMovie = currentPage.getMoviesList().get(i);
                printMovie(currentMovie, newObject, objectMapper);
                currentArray.add(newObject);
            }
        }

        if (currentPage.getName().equals("Recommendation")) {
            currentObject.set("currentMoviesList", null);
        } else {
            currentObject.set("currentMoviesList", currentArray);
        }

        ObjectNode newObject = objectMapper.createObjectNode();
        printUser(currentPage.getCurrentUser().getUser(), newObject, objectMapper);
        currentObject.set("currentUser", newObject);

        output.add(currentObject);
    }

    /**
     * Prints the output data for the current movie.
     * @param currentMovie the current page object
     * @param objectMapper the Jackson object mapper
     * @param newObject the current object where we set the fields
     */
    public static void printMovie(final Movies currentMovie, final ObjectNode newObject,
                                  final ObjectMapper objectMapper) {
        newObject.put("name", currentMovie.getName());
        newObject.put("year", currentMovie.getYear());
        newObject.put("duration", currentMovie.getDuration());

        ArrayNode genres = objectMapper.createArrayNode();
        if (currentMovie.getGenres() != null) {
            for (int i = 0; i < currentMovie.getGenres().size(); i++) {
                genres.add(currentMovie.getGenres().get(i));
            }
        }
        newObject.set("genres", genres);


        ArrayNode actors = objectMapper.createArrayNode();
        if (currentMovie.getActors() != null) {
            for (int i = 0; i < currentMovie.getActors().size(); i++) {
                actors.add(currentMovie.getActors().get(i));
            }
        }
        newObject.set("actors", actors);

        ArrayNode countriesBanned = objectMapper.createArrayNode();
        if (currentMovie.getCountriesBanned() != null) {
            for (int i = 0; i < currentMovie.getCountriesBanned().size(); i++) {
                countriesBanned.add(currentMovie.getCountriesBanned().get(i));
            }
        }
        newObject.set("countriesBanned", countriesBanned);

        newObject.put("numLikes", currentMovie.getNumLikes());
        newObject.put("rating", currentMovie.getRating());
        newObject.put("numRatings", currentMovie.getNumRatings());
    }

    /**
     * Prints the data for a user to an object node.
     * @param currentUser the user object
     * @param newObject the object node to store the user data
     * @param objectMapper the Jackson object mapper
     */
    public static void printUser(final Users currentUser, final ObjectNode newObject,
                                 final ObjectMapper objectMapper) {
        ObjectNode credentialsObject = objectMapper.createObjectNode();
        credentialsObject.put("name", currentUser.getCredentials().getName());
        credentialsObject.put("password", currentUser.getCredentials().getPassword());
        credentialsObject.put("accountType", currentUser.getCredentials().getAccountType());
        credentialsObject.put("country", currentUser.getCredentials().getCountry());
        credentialsObject.put("balance", currentUser.getCredentials().getBalance());
        newObject.set("credentials", credentialsObject);

        newObject.put("tokensCount", currentUser.getTokensCount());
        newObject.put("numFreePremiumMovies", currentUser.getNumFreePremiumMovies());

        ArrayNode currentArray = objectMapper.createArrayNode();
        addMoviesToArrayNode(currentArray, currentUser.getPurchasedMovies(), objectMapper);
        newObject.set("purchasedMovies", currentArray);

        currentArray = objectMapper.createArrayNode();
        addMoviesToArrayNode(currentArray, currentUser.getWatchedMovies(), objectMapper);
        newObject.set("watchedMovies", currentArray);

        currentArray = objectMapper.createArrayNode();
        addMoviesToArrayNode(currentArray, currentUser.getLikedMovies(), objectMapper);
        newObject.set("likedMovies", currentArray);

        currentArray = objectMapper.createArrayNode();
        addMoviesToArrayNode(currentArray, currentUser.getRatedMovies(), objectMapper);
        newObject.set("ratedMovies", currentArray);

        currentArray = objectMapper.createArrayNode();
        for (int i = 0; i < currentUser.getNotifications().size(); i++) {
            ObjectNode currentObject = objectMapper.createObjectNode();
            currentObject.put("movieName", currentUser.getNotifications().get(i).getMovieName());
            currentObject.put("message", currentUser.getNotifications().get(i).getMessage());
            currentArray.add(currentObject);
        }
        newObject.set("notifications", currentArray);
    }

    /**
     * Adds object representations of movies to an array node.
     * @param currentArray the array node to store the movies
     * @param movies the list of movies
     * @param objectMapper the Jackson object mapper
     */
    public static void addMoviesToArrayNode(final ArrayNode currentArray,
                                            final ArrayList<Movies> movies,
                                            final ObjectMapper objectMapper) {
        if (movies != null) {
            for (int i = 0; i < movies .size(); i++) {
                ObjectNode currenObject = objectMapper.createObjectNode();
                Movies currentMovie = movies.get(i);
                printMovie(currentMovie, currenObject, objectMapper);
                currentArray.add(currenObject);
            }
        }
    }
}
