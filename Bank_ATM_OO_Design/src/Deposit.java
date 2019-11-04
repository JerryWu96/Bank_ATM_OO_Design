/**
 * A Transaction class that encapsulates a deposit made by customers
 */
public class Deposit extends Transaction {
    private double depositAmount;

    Deposit(String accountID, String userID, int creationDay, String selectedCurrency, double depositAmount) {
        super(accountID, userID, creationDay, selectedCurrency);
        this.depositAmount = depositAmount;
    }

    public void startTransaction() {
        BankPortal.getInstance().getBank().deposit(getAccountID(), getDepositAmount(), getSelectedCurrency());
    }

    public double getDepositAmount() {
        return this.depositAmount;
    }
}
