package actionforpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import currentdata.CurrentPage;
import datainput.Actions;
import outputformat.StandardError;

public class BuyTokens extends Command {

    private CurrentPage currentPage;
    private Actions action;

    public BuyTokens(final CurrentPage currentPage, final Actions action) {
        this.currentPage = currentPage;
        this.action = action;
    }
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
