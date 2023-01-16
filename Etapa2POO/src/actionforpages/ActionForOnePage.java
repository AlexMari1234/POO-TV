package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.*;
import filtersactions.FilterByActors;
import filtersactions.FilterByCountry;
import filtersactions.FilterByGenre;
import filtersactions.FilterByName;
import outputformat.StandardError;
import outputformat.StandardOutput;
import sortactions.SortByDuration;
import sortactions.SortByRating;

import java.io.IOException;
import java.util.*;

public class ActionForOnePage {
    static final int TEN = 10;
    /**
     * Executes an action based on the specified feature.
     * @param indexAction an integer representing the index of the action to be executed
     * @param currentPage an object representing the current page
     * @param inputData an object containing input data for the action
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     * @throws IOException if an I/O exception occurs
     */
    public static void executeAction(final int indexAction, final CurrentPage currentPage,
                                     final DataBase inputData, final ObjectMapper objectMapper,
                                     final ArrayNode output, final ArrayList<CurrentPage> pages)
            throws IOException {
        Actions currentAction = inputData.getActions().get(indexAction);
        String currentFeature = currentAction.getFeature();
        solveMethod(currentFeature, currentPage, inputData,
                currentAction, pages).executeCommand(objectMapper, output);

    }

    public static Command solveMethod(final String currentFeature, final CurrentPage currentPage,
                                      final DataBase inputData, final Actions currentAction,
                                      final ArrayList<CurrentPage> pages) {
        switch (currentFeature) {
            case "login":
                return new Login(currentPage, inputData, currentAction, pages);
            case "register":
                return new Register(currentPage, inputData, currentAction, pages);
            case "search":
                return new Search(currentPage, currentAction);
            case "filter":
                return new FilterOn(currentPage, inputData, currentAction);
            case "buy premium account":
                return new BuyPremiumAccount(currentPage);
            case "buy tokens":
                return new BuyTokens(currentPage, currentAction);
            case "purchase":
                return new Purchase(currentPage);
            case "watch":
                return new Watch(currentPage);
            case "like":
                return new Like(currentPage, inputData);
            case "rate":
                return new Rate(currentPage, inputData, currentAction);
            case "subscribe":
                return new Subscribe(currentPage, currentAction);
        }

        throw new IllegalArgumentException("Command not recognized");
    }

    /**
     * Handles the login action by checking if the credentials provided in the action match a user
     * in the input data and setting the current user in the current page accordingly.
     * If the conditions for login are not met or the credentials do not match a user,
     * it outputs an error.
     * @param action an object representing the action to be executed
     * @param inputData an object containing input data for the action
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void login(final Actions action, final DataBase inputData,
                             final CurrentPage currentPage, final ObjectMapper objectMapper,
                             final ArrayNode output, final ArrayList<CurrentPage> pages) {
        if (currentPage.getName().equals("login")
                && currentPage.getCurrentUser().getUser() == null) {
            if (Users.checkUserInDataBase(action.getCredentials(), inputData) != null) {
                currentPage.getCurrentUser().setUser(Users.checkUserInDataBase(
                        action.getCredentials(), inputData));
                currentPage.setName("homepage");

                if (currentPage.getCurrentUser().getUser().getNotifications() == null) {
                    currentPage.getCurrentUser().getUser().setNotifications(
                            new ArrayList<Notifications>());
                }

                if (currentPage.getCurrentUser().getUser().getSubscribedGenres() == null) {
                    currentPage.getCurrentUser().getUser().setSubscribedGenres(
                            new ArrayList<String>());
                }

                pages.add(new CurrentPage(currentPage));
                StandardOutput.printoutput(currentPage, objectMapper, output);
            } else {
                currentPage.getCurrentUser().setUser(Users.checkUserInDataBase(
                        action.getCredentials(), inputData));
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }

        currentPage.setName("homepage");
    }

    /**
     * Handles the register action by checking if the credentials provided in the action are
     * available for registration, creating a new user with the provided credentials if they are,
     * and setting the current user in the current page to the new user. If the conditions for
     * registration are not met or the credentials are not available, it outputs an error.
     * Regardless of the outcome of the action, the name of the current page is set to "homepage".
     * @param action an object representing the action to be executed
     * @param inputData an object containing input data for the action
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void register(final Actions action, final DataBase inputData,
                                final CurrentPage currentPage, final ObjectMapper objectMapper,
                                final ArrayNode output, final ArrayList<CurrentPage> pages) {
        if (currentPage.getName().equals("register")
                && currentPage.getCurrentUser().getUser() == null) {
            if (DataBase.checkForRegister(action.getCredentials(), inputData) == 1) {
                Credentials currentCredentials = action.getCredentials();
                Users newUser = new Users(currentCredentials.getName(),
                        currentCredentials.getPassword(), currentCredentials.getAccountType(),
                        currentCredentials.getCountry(), currentCredentials.getBalance());
                inputData.addUser(newUser);
                currentPage.getCurrentUser().setUser(newUser);
                currentPage.setName("homepage");

                if (currentPage.getCurrentUser().getUser().getNotifications() == null) {
                    currentPage.getCurrentUser().getUser().setNotifications(
                            new ArrayList<Notifications>());
                }

                if (currentPage.getCurrentUser().getUser().getSubscribedGenres() == null) {
                    currentPage.getCurrentUser().getUser().setSubscribedGenres(
                            new ArrayList<String>());
                }

                pages.add(new CurrentPage(currentPage));
                StandardOutput.printoutput(currentPage, objectMapper, output);
            } else {
                currentPage.getCurrentUser().setUser((Users) null);
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }

        currentPage.setName("homepage");
    }

    /**
     * Handles the search action by applying a name-based filter to the movies list in the
     * current page and setting the resulting list as the new movies list in the current page.
     * If the conditions for search are not met or the search fails, it outputs an error.
     * @param action an object representing the action to be executed
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void search(final Actions action, final CurrentPage currentPage,
                              final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("movies")
                && currentPage.getCurrentUser().getUser() != null) {
            FilterByName filterByName = new FilterByName();
            Filter newFilter = new Filter();
            ArrayList<String> currentData = new ArrayList<String>();
            currentData.add(action.getStartsWith());
            ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(filterByName,
                    currentPage.getMoviesList(), currentData);
            currentPage.setMoviesList(acceptedMovies);
            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Filters and sorts a list of movies based on the specified criteria.
     * @param action the action object containing the filters and sort criteria
     * @param inputData the input data object containing the list of all movies
     * @param currentPage the current page object containing the current user and the list of
     * movies to filter and sort
     * @param objectMapper the object mapper object used to serialize objects to JSON
     * @param output the output array node used to store the filtered and sorted list of movies
     */
    public static void filter(final Actions action, final DataBase inputData,
                              final CurrentPage currentPage, final ObjectMapper objectMapper,
                              final ArrayNode output) {
        if (currentPage.getName().equals("movies")) {
            Filter filter = action.getFilters();
            currentPage.setMoviesList(FilterByCountry.acceptedCountries(
                    inputData.getMovies(), currentPage.getCurrentUser().getUser()));

            if (filter.getContains() != null) {
                if (filter.getContains().getActors() != null) {
                    FilterByActors filterByActors = new FilterByActors();
                    Filter newFilter = new Filter();
                    ArrayList<String> currentData = new ArrayList<String>(
                            filter.getContains().getActors());
                    ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(
                            filterByActors, currentPage.getMoviesList(), currentData);
                    currentPage.setMoviesList(acceptedMovies);
                }

                if (filter.getContains().getGenre() != null) {
                    FilterByGenre filterByGenre = new FilterByGenre();
                    Filter newFilter = new Filter();
                    ArrayList<String> currentData = new ArrayList<String>(
                            filter.getContains().getGenre());
                    ArrayList<Movies> acceptedMovies = newFilter.applyFilterStrategy(filterByGenre,
                            currentPage.getMoviesList(), currentData);
                    currentPage.setMoviesList(acceptedMovies);
                }
            }

            if (filter.getSort() != null) {
                ArrayList<Movies> currentMovies = currentPage.getMoviesList();
                if (filter.getSort().getDuration() != null
                        && filter.getSort().getRating() != null) {
                    Filter.sortByRatingAndDuration(filter, currentMovies);
                } else if (filter.getSort().getDuration() != null) {
                    SortByDuration sortByDuration = new SortByDuration();
                    Sort newSort = new Sort();
                    newSort.applySortStrategy(sortByDuration, currentPage.getMoviesList(),
                            filter.getSort().getDuration());
                } else if (filter.getSort().getRating() != null) {
                    SortByRating sortByRating = new SortByRating();
                    Sort newSort = new Sort();
                    newSort.applySortStrategy(sortByRating, currentPage.getMoviesList(),
                            filter.getSort().getRating());
                }
            }

            StandardOutput.printoutput(currentPage, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }


    /**
     * Makes a purchase of a movie.
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void purchase(final CurrentPage currentPage,
                                final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.purchaseMovie(currentPage, currentPage.getCurrentMovie(),
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Makes a watch of a movie.
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void watch(final CurrentPage currentPage, final ObjectMapper objectMapper,
                             final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.watchMovie(currentPage, currentPage.getCurrentMovie(),
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Makes a like of a movie.
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void like(final CurrentPage currentPage, final DataBase inputData,
                            final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.likeMovie(currentPage, currentPage.getCurrentMovie(), inputData,
                    objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }

    /**
     * Makes a rate of a movie.
     * @param currentPage an object representing the current page
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public static void rate(final Actions action, final CurrentPage currentPage,
                            final DataBase inputData, final ObjectMapper objectMapper,
                            final ArrayNode output) {
        if (currentPage.getName().equals("see details")) {
            Movies.rateMovie(action.getRate(), currentPage, currentPage.getCurrentMovie(),
                    inputData, objectMapper, output);
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }


}
