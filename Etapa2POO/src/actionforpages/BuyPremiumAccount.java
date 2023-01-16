package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import outputformat.StandardError;

public class BuyPremiumAccount extends Command {
    static final int TEN = 10;
    private CurrentPage currentPage;

    /**
     * copy current page
     * @param currentPage
     */
    public BuyPremiumAccount(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Handles the purchase of a premium account by checking if the user has enough tokens and
     * updating their account type and token count accordingly. If the conditions for the purchase
     * not met or the purchase fails, it outputs an error.
     * @param objectMapper an object used for serializing and deserializing Java objects to and
     * from JSON
     * @param output an array node representing the output of the action
     */
    public void executeCommand(final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getName().equals("upgrades")) {
            if (currentPage.getCurrentUser().getUser().getTokensCount() >= TEN) {
                currentPage.getCurrentUser().getUser().getCredentials().setAccountType("premium");
                currentPage.getCurrentUser().getUser().setTokensCount(currentPage
                        .getCurrentUser().getUser().getTokensCount() - TEN);
            } else {
                StandardError.printerror(objectMapper, output);
            }
        } else {
            StandardError.printerror(objectMapper, output);
        }
    }
}
