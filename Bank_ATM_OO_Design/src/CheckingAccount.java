public class CheckingAccount extends Account {
    // Current system has not implemented debit/credit card systems. This is more of a design showcase.
    private int debitCardNumber;
    private double operationFee;

    CheckingAccount(double balance, int bankID, int userID, double withdrawLimit, double operationFee) {
        super(balance, bankID, userID);
        this.debitCardNumber = 0;
        this.operationFee = operationFee;
    }

    public int getDebitCardNumber() {
        return this.debitCardNumber;
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
