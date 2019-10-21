import java.util.List;

public class CheckingAccount extends Account {
    // Current system has not implemented debit/credit card systems. This is more of a design showcase.
    private int debitCardNumber;
    private double operationFee;
    private USD usd;
    private CNY cny;
    private YEN yen;

    CheckingAccount(double balance, String bankID, String userID, String postfix) {
        super(bankID + "_" + userID + "_CK_" + postfix, balance, bankID, userID);
        this.usd = new USD();
        this.cny = new CNY();
        this.yen = new YEN();
        this.debitCardNumber = 0;
        this.operationFee = 5;
        this.setBalance(-operationFee);
    }

    public int getDebitCardNumber() {
        return this.debitCardNumber;
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
