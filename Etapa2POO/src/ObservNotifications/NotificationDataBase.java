package ObservNotfications;

public interface NotificationDataBase {
    /**
     * this function updates notifications for users
     * @param movieName represents the movie that we want to add to notification
     * @param message represents the message that we want to add to notification
     */
    void update(String movieName, String message);
}
