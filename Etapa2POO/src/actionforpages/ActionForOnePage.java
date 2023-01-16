package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import database.DataBase;
import datainput.*;

import java.io.IOException;
import java.util.*;

public class ActionForOnePage {
    static final int TEN = 10;
    /**
     * Executes an action based on the specified feature by using factory as desgin pattern
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

    /**
     *This method is used to determine which command needs to be executed based
     * on the feature specified.
     *@param currentFeature the feature that needs to be executed
     *@param currentPage the current page object
     *@param inputData the database object
     *@param currentAction the action object
     *@param pages the list of current pages
     *@return the command object that needs to be executed
     */
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

}
