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

    public Subscribe(final CurrentPage currentPage, final Actions action) {
        this.currentPage = currentPage;
        this.action = action;
    }
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


    public static int checkGenreForMovie(final String subscribedGenre, final Movies currentMovie) {
        for (int i = 0; i < currentMovie.getGenres().size(); i++) {
            if (currentMovie.getGenres().get(i).equals(subscribedGenre)) {
                return 1;
            }
        }

        return 0;
    }

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
