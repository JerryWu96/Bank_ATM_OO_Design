/**
 * A class that encapsulates a cash transfer between different accounts.
 */
public class Transfer extends Transaction {
    private double transferAmount;
    private String sourceAccountID;
    private String targetAccountID;

    Transfer(String sourceAccountID, String targetAccountID, String userID, int creationDay, String selectedCurrency, double transferAmount) {
        super(sourceAccountID, userID, creationDay, selectedCurrency);
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

    public String startTransaction() {
        return Bank.getInstance().transfer(getAccountID(), getTargetAccountID(), getTransferAmount(), getSelectedCurrency());
    }
}
