package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.Actions;
import datainput.Credentials;
import datainput.Notifications;
import datainput.Users;
import outputformat.StandardError;
import outputformat.StandardOutput;

import java.util.ArrayList;

public class Register extends Command {

    private CurrentPage currentPage;
    private DataBase inputData;
    private Actions action;
    private ArrayList<CurrentPage> pages;

    /**
     * copy current page, input data, action and pages
     * @param currentPage
     * @param inputData
     * @param action
     * @param pages
     */
    public Register(final CurrentPage currentPage, final DataBase inputData,
                    final Actions action, final ArrayList<CurrentPage> pages) {
        this.currentPage = currentPage;
        this.inputData = inputData;
        this.action = action;
        this.pages = pages;
    }

    /**
     * Handles the register action by checking if the credentials provided in the action are
     * available for registration, creating a new user with the provided credentials if they are,
     * and setting the current user in the current page to the new user. If the conditions for
     * registration are not met or the credentials are not available, it outputs an error.
     * Regardless of the outcome of the action, the name of the current page is set to "homepage".
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
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
}
