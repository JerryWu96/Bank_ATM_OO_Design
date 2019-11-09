package backend;
/**
 * A Transaction class that encapsulates a deposit made by customers
 */
public class Deposit extends Transaction {
    private double depositAmount;

    /**
     * create transaction: deposit
     * @param accountID
     * @param userID
     * @param creationDay
     * @param selectedCurrency
     * @param depositAmount
     */
    Deposit(String accountID, String userID, int creationDay, String selectedCurrency, double depositAmount) {
        super(accountID, userID, creationDay, selectedCurrency, "Deposit");
        this.depositAmount = depositAmount;
    }

    /**
     * execute transaction: deposit
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().deposit(getAccountID(), getDepositAmount(), getSelectedCurrency());
    }

    public double getDepositAmount() {
        return this.depositAmount;
    }
}
