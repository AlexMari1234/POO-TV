package currentdata;
import datainput.Users;

public final class CurrentUser {
    private static CurrentUser instance = null;
    private CurrentUser() {
    }

    /**
     * @return the instance of the class
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }

        return instance;
    }

    private Users user;

    /**
     * return current user
     */
    public Users getUser() {
        return user;
    }

    /**
     * set current user
     */
    public void setUser(final Users user) {
        this.user = user;
    }
}
