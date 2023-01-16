package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import outputformat.StandardError;

public class BuyPremiumAccount extends Command {
    static final int TEN = 10;
    private CurrentPage currentPage;

    public BuyPremiumAccount(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }
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
