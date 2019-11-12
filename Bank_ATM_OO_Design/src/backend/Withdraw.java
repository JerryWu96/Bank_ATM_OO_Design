package backend;
/**
 * Class represents a withdraw transaction made bt a customer.
 */
public class Withdraw extends Transaction{
    private double withdrawAmount;
    private static final double OPERATION_FEE = 5;

    /**
     * create transaction: withdraw
     * @param accountID
     * @param userID
     * @param creationDay
     * @param selectedCurrency
     * @param withdrawAmount
     */
    public Withdraw(String accountID, String userID, int creationDay, String selectedCurrency, double withdrawAmount) {
        super(accountID, userID, creationDay, selectedCurrency, SharedConstants.WITHDRAW);
        this.withdrawAmount = withdrawAmount;
    }

    public double getWithdrawAmount() {
        return this.withdrawAmount;
    }

    public String startTransaction() {
        return BankPortal.getInstance().getBank().withdraw(getAccountID(), getWithdrawAmount(), getSelectedCurrency());
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " withdrew " + getSelectedCurrency() +
                getWithdrawAmount() + " from account: " + getAccountID() + "\n";
    }
}
