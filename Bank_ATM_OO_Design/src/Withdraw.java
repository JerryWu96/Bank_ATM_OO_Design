public class Withdraw extends Transaction{
    private double withdrawAmount;

    Withdraw(String accountID, String userID, int creationDay, String selectedCurrency, double operationFee, double withdrawAmount) {
        super(accountID, userID, creationDay, selectedCurrency, operationFee);
        this.withdrawAmount = withdrawAmount;
    }

    public double getWithdrawAmount() {
        return this.withdrawAmount;
    }

    public void startTransaction() {

    }
}
