public class Deposit extends Transaction {
    private double depositAmount;

    Deposit(int accountID, int userID, int creationDay, Currency currency, double operationFee, double depositAmount) {
        super(accountID, userID, creationDay, currency, operationFee);
        this.depositAmount = depositAmount;
    }

    public double getDepositAmount() {
        return this.depositAmount;
    }
}
