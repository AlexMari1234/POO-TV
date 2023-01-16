package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Actions;
import datainput.Notifications;
import datainput.Users;
import outputformat.StandardError;
import outputformat.StandardOutput;

import java.util.ArrayList;

public class Login extends Command {

    private CurrentPage currentPage;
    private DataBase inputData;
    private Actions action;
    private ArrayList<CurrentPage> pages;

    /**
     * copy current page, input data, action and pages
     */
    public Login(final CurrentPage currentPage, final DataBase inputData,
                 final Actions action, final ArrayList<CurrentPage> pages) {
        this.currentPage = currentPage;
        this.inputData = inputData;
        this.action = action;
        this.pages = pages;
    }

    /**
     * Handles the login action by checking if the credentials provided in the action match a user
     * in the input data and setting the current user in the current page accordingly.
     * If the conditions for login are not met or the credentials do not match a user,
     * it outputs an error.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
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
}
