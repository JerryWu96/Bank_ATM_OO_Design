public class SavingsAccount extends Account {
    private double withdrawLimit;
    private double operationFee;

    SavingsAccount(double balance, int bankID, int userID, double withdrawLimit, double operationFee) {
        super(balance, bankID, userID);
        this.withdrawLimit = withdrawLimit;
        this.operationFee = operationFee;
    }

    public double getWithdrawLimit() {
        return this.getWithdrawLimit();
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
