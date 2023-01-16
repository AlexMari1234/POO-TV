package ObservNotfications;

import datainput.Notifications;
import datainput.Users;

public class NotificationForUser implements ObservNotfications.NotificationDataBase {
    private Users user;

    /**
     * this method adds at the end of notifications for one user a new one
     * @param movieName represents the movie that we want to add to notification
     * @param message represents the message that we want to add to notification
     */
    public void update(final String movieName, final String message) {
        user.getNotifications().add(new Notifications(movieName, message));
    }

    /**
     * return user
     */
    public Users getUser() {
        return user;
    }

    /**
     * set user
     */
    public void setUser(final Users user) {
        this.user = user;
    }
}
