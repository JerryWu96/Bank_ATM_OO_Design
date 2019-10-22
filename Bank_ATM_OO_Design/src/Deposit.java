public class Deposit extends Transaction {
    private double depositAmount;

    Deposit(String accountID, String userID, int creationDay, String selectedCurrency, double depositAmount) {
        super(accountID, userID, creationDay, selectedCurrency, 0);
        this.depositAmount = depositAmount;
    }

    @Override
    public void startTransaction() {
        Bank.getInstance().deposit(getAccountID(), getDepositAmount(), getSelectedCurrency());
    }

    public double getDepositAmount() {
        return this.depositAmount;
    }
}
