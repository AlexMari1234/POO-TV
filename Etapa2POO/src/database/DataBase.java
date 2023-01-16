package database;

import java.util.ArrayList;

import ObservNotfications.NotificationDataBase;
import ObservNotfications.NotificationForUser;
import ObservNotfications.NotifyUsersFromDataBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.*;
import outputformat.StandardError;

public final class DataBase {
    private ArrayList<Actions> actions;
    private ArrayList<Movies> movies;
    private ArrayList<Users> users;

    private static DataBase instance = null;

    private DataBase() {

    }

    /**
     * return the instance of the class
     */
    public static DataBase  getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }

        return instance;
    }

    /**
     * return list of actions
     */
    public ArrayList<Actions> getActions() {
        return actions;
    }

    /**
     * set list of actions
     */
    public void setActions(final ArrayList<Actions> actions) {

        this.actions = actions;
    }

    /**
     * return movies
     */
    public ArrayList<Movies> getMovies() {
        return movies;
    }

    /**
     * set movies
     */
    public void setMovies(final ArrayList<Movies> movies) {
        this.movies = movies;
    }

    /**
     * return users
     */
    public ArrayList<Users> getUsers() {
        return users;
    }

    /**
     * set users
     */
    public void setUsers(final ArrayList<Users> users) {
        this.users = users;
    }

    /**
     * add user to database
     */
    public void addUser(final Users user) {
        this.users.add(user);
    }

    /**
     This method checks if the given credentials are already registered in the database.
     @param actionCredentials the credentials provided by the user to be registered
     @param inputData the database
     @return 1 if the credentials are not registered, 0 if the credentials are already registered
     */
    public static int checkForRegister(final Credentials actionCredentials,
                                       final DataBase inputData) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            if (inputData.getUsers().get(i).getCredentials().getName()
                    .equals(actionCredentials.getName())) {
                return 0;
            }
        }

        return 1;
    }

    /**
     * Modify the purchased movie data in the inputData object for all users who
     * have purchased the movie.
     * @param inputData the database
     * @param movie the movie to be modified in the database for all users
     */
    public static void  modifyDataBasePurchasedMovie(final DataBase inputData, final Movies movie) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            if (currentUser.getPurchasedMovies() != null) {
                for (int j = 0; j < currentUser.getPurchasedMovies().size(); j++) {
                    if (currentUser.getPurchasedMovies().get(j).getName().equals(movie.getName())) {
                        currentUser.getPurchasedMovies().set(j, new Movies(movie));
                    }
                }
            }
        }
    }

    /**
     * Modify the purchased movie data in the inputData object for all users who
     * have liked the movie.
     * @param inputData the database
     * @param movie the movie to be modified in the database for all users
     */
    public static void  modifyDataBaseLikedMovie(final DataBase inputData, final Movies movie) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            if (currentUser.getLikedMovies() != null) {
                for (int j = 0; j < currentUser.getLikedMovies() .size(); j++) {
                    if (currentUser.getLikedMovies() .get(j).getName().equals(movie.getName())) {
                        currentUser.getLikedMovies() .set(j, new Movies(movie));
                    }
                }
            }
        }
    }

    /**
     * Modify the purchased movie data in the inputData object for all users who
     * have watched the movie.
     * @param inputData the database
     * @param movie the movie to be modified in the database for all users
     */
    public static void modifyDataBaseWatchedMovie(final DataBase inputData, final Movies movie) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            if (currentUser.getWatchedMovies() != null) {
                for (int j = 0; j < currentUser.getWatchedMovies().size(); j++) {
                    if (currentUser.getWatchedMovies().get(j).getName().equals(movie.getName())) {
                        currentUser.getWatchedMovies().set(j, new Movies(movie));
                    }
                }
            }
        }
    }

    /**
     * Modify the purchased movie data in the inputData object for all users who
     * have rated the movie.
     * @param inputData the database
     * @param movie the movie to be modified in the database for all users
     */
    public static void  modifyDataBaseRatedMovie(final DataBase inputData, final Movies movie) {
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            if (currentUser.getRatedMovies() != null) {
                for (int j = 0; j < currentUser.getRatedMovies().size(); j++) {
                    if (currentUser.getRatedMovies().get(j).getName().equals(movie.getName())) {
                        currentUser.getRatedMovies().set(j, new Movies(movie));
                    }
                }
            }
        }
    }

    public static void executeAction(final int indexAction, final CurrentPage currentPage,
                                     final DataBase inputData, final ObjectMapper objectMapper,
                                     final ArrayNode output, ArrayList<CurrentPage> pages) {
        Actions currentAction = inputData.getActions().get(indexAction);
        String currentFeature = currentAction.getFeature();
        Movies currentMovie = currentAction.getAddedMovie();
        String deletedMovie = currentAction.getDeletedMovie();
        switch (currentFeature) {
            case "delete":
                deleteMovie(deletedMovie, currentPage, inputData, objectMapper, output);
                break;
            case "add":
                addMovie(currentMovie, currentPage, inputData, objectMapper, output);
                break;
            default:
                break;
        }
    }

    public static void deleteMovie(final String deletedMovie, final CurrentPage currentPage,
                                   final DataBase inputData, final ObjectMapper objectMapper,
                                   final ArrayNode output) {
        if (checkMovieInMovies(deletedMovie, inputData.getMovies()) != -1) {
            inputData.getMovies().remove(checkMovieInMovies(deletedMovie, inputData.getMovies()));

            NotifyUsersFromDataBase notifyUsersFromDataBase =
                    DataBase.addUsersFromDataBase(inputData);

            notifyUsersFromDataBase.notifyUsersAboutDeletedMovie(deletedMovie, "DELETE");


        } else {
            StandardError.printerror(objectMapper, output);
        }

    }

    public static void addMovie(final Movies currentMovie, final CurrentPage currentPage,
                                final DataBase inputData, final ObjectMapper objectMapper,
                                final ArrayNode output) {
        if (checkMovieInMovies(currentMovie.getName(), inputData.getMovies()) == -1) {
            currentMovie.setRating(0.00);
            currentMovie.setNumLikes(0);
            currentMovie.setNumRatings(0);
            currentMovie.setRatingList(new ArrayList<Rating>());
            inputData.getMovies().add(new Movies(currentMovie));

            NotifyUsersFromDataBase notifyUsersFromDataBase =
                    DataBase.addUsersFromDataBase(inputData);
            notifyUsersFromDataBase.notifyUsersAboutAddedMovie(currentMovie,
                    currentMovie.getName(), "ADD");
        } else {
            StandardError.printerror(objectMapper, output);
        }

    }


    public static int checkMovieInMovies(final String currentMovie,
                                         final ArrayList<Movies> movies) {
        if (movies == null) {
            return -1;
        }

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().equals(currentMovie)) {
                return i;
            }
        }

        return -1;
    }

    public static NotifyUsersFromDataBase addUsersFromDataBase(final DataBase inputData) {
        NotifyUsersFromDataBase notifyUsersFromDataBase = new NotifyUsersFromDataBase();
        notifyUsersFromDataBase.setUsers(new ArrayList<NotificationDataBase>());
        for (int i = 0; i < inputData.getUsers().size(); i++) {
            Users currentUser = inputData.getUsers().get(i);
            NotificationForUser notificationForUser = new NotificationForUser();
            notificationForUser.setUser(currentUser);
            notifyUsersFromDataBase.addObserver(notificationForUser);
        }

        return notifyUsersFromDataBase;
    }
}
