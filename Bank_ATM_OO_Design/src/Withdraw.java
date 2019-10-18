public class Withdraw extends Transaction{
    private double withdrawAmount;

    Withdraw(int accountID, int userID, int creationDay, Currency currency, double operationFee, double withdrawAmount) {
        super(accountID, userID, creationDay, currency, operationFee);
        this.withdrawAmount = withdrawAmount;
    }

    public double getWithdrawAmount() {
        return this.withdrawAmount;
    }
}
