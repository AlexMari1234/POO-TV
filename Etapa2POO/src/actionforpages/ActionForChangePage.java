package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Actions;
import datainput.Movies;
import datainput.Users;
import filtersactions.FilterByCountry;
import outputformat.StandardError;
import outputformat.StandardOutput;

import java.io.IOException;
import java.util.ArrayList;

public class ActionForChangePage {

    /**
     * Executes a specific action based on the value of the pageChanged variable.
     * @param indexAction an integer representing the index of the action to be executed
     * @param currentPage an object representing the current page
     * @param inputData an object containing input data for the action
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     * @param pages list of pages that a user has navigated on
     * @throws IOException if an error occurs while serializing or deserializing a Java object
     */
    public static void executeAction(final int indexAction, final CurrentPage currentPage,
                                     final DataBase inputData, final ObjectMapper objectMapper,
                                     final ArrayNode output, final ArrayList<CurrentPage> pages)
            throws IOException {
        Actions currentAction = inputData.getActions().get(indexAction);
        String pageChanged = currentAction.getPage();
        switch (pageChanged) {
            case "login":
                login(pageChanged, currentPage, objectMapper, output);
                break;
            case "logout":
                logout(currentPage, objectMapper, output, pages);
                break;
            case "register":
                register(pageChanged, currentPage, objectMapper, output);
                break;
            case "movies":
                movies(pageChanged, currentPage, inputData, objectMapper, output, pages);
                break;
            case "see details":
                seedetails(pageChanged, currentAction.getMovie(), currentPage,
                        objectMapper, output, pages);
                break;
            case "upgrades":
                upgrades(pageChanged, currentPage, objectMapper, output, pages);
                break;
            default:
                break;
        }
    }

    /**
     * Handles the login action by changing the current page and clearing the movies list
     * and current movie if the user is not logged in and on the homepage.
     * @param pageChanged a string representing the page to be changed to
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void login(final String pageChanged, final CurrentPage currentPage,
                             final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("homepage")
                && currentPage.getCurrentUser().getUser() == null) {
            currentPage.setName(pageChanged);
            currentPage.setMoviesList((ArrayList<Movies>) null);
            currentPage.setCurrentMovie((Movies) null);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Handles the logout action by changing the current page to the homepage and clearing
     * the current user, movies list, and current movie if the user is logged in.
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     * @param pages list of pages that a user has navigated on
     */
    public static void logout(final CurrentPage currentPage,
                              final ObjectMapper objectMapper, final ArrayNode output,
                              final ArrayList<CurrentPage> pages) {
        if (currentPage.getCurrentUser().getUser() != null) {
            currentPage.setName("homepage");
            currentPage.getCurrentUser().setUser((Users) null);
            currentPage.setMoviesList((ArrayList<Movies>) null);
            currentPage.setCurrentMovie((Movies) null);
            if (pages != null) {
                pages.removeAll(pages);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Handles the register action by changing the current page and clearing the movies list and
     * current movie if the user is not logged in and on the homepage.
     * @param pageChanged a string representing the page to be changed to
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void register(final String pageChanged, final CurrentPage currentPage,
                                final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("homepage")
                && currentPage.getCurrentUser().getUser() == null) {
            currentPage.setName(pageChanged);
            currentPage.setMoviesList((ArrayList<Movies>) null);
            currentPage.setCurrentMovie((Movies) null);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Handles the movies action by changing the current page, clearing the current movie, and
     * filtering the movies list based on the user's country if the user is logged in.
     * @param pageChanged a string representing the page to be changed to
     * @param currentPage an object representing the current page
     * @param inputData an object containing input data for the action
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     * @param pages list of pages that a user has navigated on
     */
    public static void movies(final String pageChanged, final CurrentPage currentPage,
                              final DataBase inputData, final ObjectMapper objectMapper,
                              final ArrayNode output, final ArrayList<CurrentPage> pages) {
        if (currentPage.getCurrentUser().getUser() != null) {
            currentPage.setName(pageChanged);
            ArrayList<Movies> acceptedMovies = FilterByCountry.acceptedCountries(
                    inputData.getMovies(), currentPage.getCurrentUser().getUser());
            currentPage.setMoviesList(acceptedMovies);
            currentPage.setCurrentMovie((Movies) null);
            pages.add(new CurrentPage(currentPage));
            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Handles the "see details" action by changing the current page and outputting the details
     * of a movie with the specified name if the current page is the movies page and the movie
     * can be found in the movies list.
     * @param pageChanged a string representing the page to be changed to
     * @param searchedMovie a string representing the name of the movie to search for
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to
     * and from JSON
     * @param output an array node representing the output of the action
     * @param pages list of pages that a user has navigated on
     */
    public static void seedetails(final String pageChanged,  final String searchedMovie,
                                  final CurrentPage currentPage, final ObjectMapper objectMapper,
                                  final ArrayNode output, final ArrayList<CurrentPage> pages) {
        if (currentPage.getName().equals("movies")) {
            Movies findMovie = Movies.searchMovie(currentPage.getMoviesList(), searchedMovie);
            if (findMovie != null) {
                currentPage.setName(pageChanged);
                currentPage.setCurrentMovie(findMovie);
                pages.add(new CurrentPage(currentPage));
                Movies.printAtOutputMovie(currentPage, objectMapper, output);
            } else {
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Handles the upgrades action by changing the current page and clearing the movies list and
     * current movie if the user is logged in.
     * @param pageChanged a string representing the page to be changed to
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     * @param pages list of pages that a user has navigated on
     */
    public static void upgrades(final String pageChanged, final CurrentPage currentPage,
                                final ObjectMapper objectMapper, final ArrayNode output,
                                final ArrayList<CurrentPage> pages) {
        if (currentPage.getCurrentUser().getUser() != null) {
            currentPage.setName(pageChanged);
            currentPage.setMoviesList((ArrayList<Movies>) null);
            currentPage.setCurrentMovie((Movies) null);
            pages.add(new CurrentPage(currentPage));
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
