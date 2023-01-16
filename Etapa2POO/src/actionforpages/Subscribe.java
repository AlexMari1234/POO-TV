package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Actions;
import datainput.Movies;
import datainput.Users;
import outputformat.StandardError;

public class Subscribe extends Command {

    private CurrentPage currentPage;
    private Actions action;

    /**
     * copy current page and action
     */
    public Subscribe(final CurrentPage currentPage, final Actions action) {
        this.currentPage = currentPage;
        this.action = action;
    }

    /**
     * This method is used to execute a command to subscribe a user to a genre of a movie.
     * @param objectMapper the object mapper used to handle JSON data
     * @param output the ArrayNode used to store the output data
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        String subscribedGenre = action.getSubscribedGenre();
        if (currentPage.getName().equals("see details")) {
            if (checkGenreForMovie(subscribedGenre, currentPage.getCurrentMovie()) == 1) {
                if (checkSubscribedGenreForUser(subscribedGenre,
                        currentPage.getCurrentUser().getUser()) == 1) {
                    currentPage.getCurrentUser().getUser()
                            .getSubscribedGenres().add(subscribedGenre);
                } else {
                    StandardError.printerror(objectMapper, output);
                }
            } else {
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     *This method is used to check if the subscribed genre of a user is present in the genres
     * of a movie.
     * @param subscribedGenre the genre that the user has subscribed to
     * @param currentMovie the movie object that needs to be checked
     * @return 1 if the subscribed genre is present in the movie genres, 0 otherwise
     */
    public static int checkGenreForMovie(final String subscribedGenre, final Movies currentMovie) {
        for (int i = 0; i < currentMovie.getGenres().size(); i++) {
            if (currentMovie.getGenres().get(i).equals(subscribedGenre)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * This method is used to check if the subscribed genre of a user is already present
     * in the list of subscribed genres of the user.
     * @param subscribedGenre the genre that the user has subscribed to
     * @param currentUser the user object whose subscribed genres needs to be checked
     * @return 0 if the subscribed genre is already present in the subscribed genres of
     * the user, 1 otherwise
     */
    public static int checkSubscribedGenreForUser(final String subscribedGenre,
                                                  final Users currentUser) {
        for (int i = 0; i < currentUser.getSubscribedGenres().size(); i++) {
            if (currentUser.getSubscribedGenres().get(i).equals(subscribedGenre)) {
                return 0;
            }
        }

        return 1;
    }
}
