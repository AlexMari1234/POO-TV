package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Actions;
import outputformat.StandardError;

public class BuyTokens extends Command {

    private CurrentPage currentPage;
    private Actions action;

    /**
     * copy current page and action
     */
    public BuyTokens(final CurrentPage currentPage, final Actions action) {
        this.currentPage = currentPage;
        this.action = action;
    }

    /**
     * Handles the purchase of tokens by checking if the user has enough balance and updating
     * their token count and balance accordingly. If the conditions for the purchase are not met
     * or the purchase fails, it outputs an error.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("upgrades")) {
            int userBalance = Integer.parseInt(currentPage.getCurrentUser()
                    .getUser().getCredentials().getBalance());
            if (action.getCount() <= userBalance) {
                currentPage.getCurrentUser().getUser().setTokensCount(
                        currentPage.getCurrentUser().getUser()
                                .getTokensCount() + action.getCount());
                currentPage.getCurrentUser().getUser().getCredentials()
                        .setBalance(Integer.toString(userBalance - action.getCount()));
            } else {
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
