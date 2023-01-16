package ObservNotfications;

import datainput.Notifications;
import datainput.Users;

public class NotificationForUser implements ObservNotfications.NotificationDataBase {
    private Users user;
    public void update(final String movieName, final String message) {
        user.getNotifications().add(new Notifications(movieName, message));
    }

    public Users getUser() {
        return user;
    }

    public void setUser(final Users user) {
        this.user = user;
    }
}
