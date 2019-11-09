package backend;
/**
 * A class that encapsulates a cash transfer between different accounts.
 */
public class Transfer extends Transaction {
    private double transferAmount;
    private String sourceAccountID;
    private String targetAccountID;

    /**
     * create transaction: transfer
     * @param sourceAccountID
     * @param targetAccountID
     * @param userID
     * @param creationDay
     * @param selectedCurrency
     * @param transferAmount
     */
    Transfer(String sourceAccountID, String targetAccountID, String userID, int creationDay, String selectedCurrency, double transferAmount) {
        super(sourceAccountID, userID, creationDay, selectedCurrency, "Transfer");
        this.sourceAccountID = sourceAccountID;
        this.targetAccountID = targetAccountID;
        this.transferAmount = transferAmount;
    }

    public double getTransferAmount() {
        return this.transferAmount;
    }

    public String getSourceAccountID() {
        return this.sourceAccountID;
    }

    public String getTargetAccountID() {
        return this.targetAccountID;
    }

    /**
     * execute transaction: transfer
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().transfer(getAccountID(), getTargetAccountID(), getTransferAmount(), getSelectedCurrency());
    }
}
