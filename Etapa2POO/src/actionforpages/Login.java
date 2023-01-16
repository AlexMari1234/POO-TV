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

    public Login(final CurrentPage currentPage, final DataBase inputData,
                 final Actions action, final ArrayList<CurrentPage> pages) {
        this.currentPage = currentPage;
        this.inputData = inputData;
        this.action = action;
        this.pages = pages;
    }
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
