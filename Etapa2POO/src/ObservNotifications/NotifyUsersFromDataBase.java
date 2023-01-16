package ObservNotfications;

import database.DataBase;
import datainput.Movies;
import datainput.Users;

import java.util.ArrayList;

public class NotifyUsersFromDataBase {
    private ArrayList<ObservNotfications.NotificationDataBase> users;

    /**
     * this method adds a new user in the list of users
     */
    public void addObserver(final ObservNotfications.NotificationDataBase user) {
        users.add(user);
    }

    /**
     * return users
     */
    public ArrayList<ObservNotfications.NotificationDataBase> getUsers() {
        return users;
    }

    /**
     * set users
     */
    public void setUsers(final ArrayList<ObservNotfications.NotificationDataBase> users) {
        this.users = users;
    }

    /**
     *This method is used to notify the users about a newly added movie.
     *The users are notified only if they are subscribed to the genre of the movie and the
     * country of the user is not in the list of countries banned for the movie.
     *@param currentMovie the movie object that has been added
     *@param movieName the name of the movie that has been added
     *@param message the message that needs to be sent to the users
     */
    public void notifyUsersAboutAddedMovie(final Movies currentMovie, final String movieName,
                                           final String message) {
        for (int i = 0; i < users.size(); i++) {
            ObservNotfications.NotificationForUser notificationForUser =
                    (ObservNotfications.NotificationForUser) users.get(i);
            Users currentUser = notificationForUser.getUser();
            if (currentUser.getSubscribedGenres() != null) {
                int ok = 0;
                for (int j = 0; j < currentUser.getSubscribedGenres().size() && ok == 0; j++) {
                    if (currentMovie.getGenres().contains(currentUser
                            .getSubscribedGenres().get(j))) {
                        ok = 1;
                    }
                }

                if (ok == 1) {
                    if (!currentMovie.getCountriesBanned().contains(currentUser
                            .getCredentials().getCountry())) {
                        notificationForUser.update(movieName, message);
                    }
                }
            }
        }

    }

    /**
     *This method is used to notify the users about a deleted movie.
     *The users are notified only if they have purchased the movie.
     *This method also removes the deleted movie from the user's purchased, watched,
     * liked and rated movie list.
     *@param deletedMovie the name of the movie that has been deleted
     *@param message the message that needs to be sent to the users
     */
    public void notifyUsersAboutDeletedMovie(final String deletedMovie, final String message) {
        for (int i = 0; i < users.size(); i++) {
            ObservNotfications.NotificationForUser notificationForUser =
                    (ObservNotfications.NotificationForUser) users.get(i);
            Users currentUser = notificationForUser.getUser();
            if (DataBase.checkMovieInMovies(deletedMovie, currentUser.getPurchasedMovies()) != -1) {
                currentUser.getPurchasedMovies().remove(DataBase.checkMovieInMovies(deletedMovie,
                        currentUser.getPurchasedMovies()));
                notificationForUser.update(deletedMovie, message);
            }

            if (DataBase.checkMovieInMovies(deletedMovie, currentUser.getWatchedMovies()) != -1) {
                currentUser.getWatchedMovies().remove(DataBase.checkMovieInMovies(deletedMovie,
                        currentUser.getWatchedMovies()));
            }

            if (DataBase.checkMovieInMovies(deletedMovie, currentUser.getLikedMovies()) != -1) {
                currentUser.getLikedMovies().remove(DataBase.checkMovieInMovies(deletedMovie,
                        currentUser.getLikedMovies()));
            }

            if (DataBase.checkMovieInMovies(deletedMovie, currentUser.getRatedMovies()) != -1) {
                currentUser.getRatedMovies().remove(DataBase.checkMovieInMovies(deletedMovie,
                        currentUser.getRatedMovies()));
            }
        }
    }


}
