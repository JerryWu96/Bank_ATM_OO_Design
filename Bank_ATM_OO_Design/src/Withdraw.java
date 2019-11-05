/**
 * Class represents a withdraw transaction made bt a customer.
 */
public class Withdraw extends Transaction{
    private double withdrawAmount;
    private static final double OPERATION_FEE = 5;

    Withdraw(String accountID, String userID, int creationDay, String selectedCurrency, double withdrawAmount) {
        super(accountID, userID, creationDay, selectedCurrency, "Withdraw");
        this.withdrawAmount = withdrawAmount;
    }

    public double getWithdrawAmount() {
        return this.withdrawAmount;
    }

    public String startTransaction() {
        return BankPortal.getInstance().getBank().withdraw(getAccountID(), getWithdrawAmount(), getSelectedCurrency());
    }
}
