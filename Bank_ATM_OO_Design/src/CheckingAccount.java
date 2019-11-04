/**
 * A checking account.
 */
public class CheckingAccount extends Account {
    // Current system has not implemented debit/credit card systems. This is more of a design showcase.
    private int debitCardNumber; // NOT REQUIRED YET
    private double operationFee;
    private USD usd;
    private CNY cny;
    private YEN yen;

    CheckingAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + "_" + userID + "_CK_" + postfix, bankID, userID, accountType);
        this.usd = new USD();
        this.cny = new CNY();
        this.yen = new YEN();
        this.debitCardNumber = -1; // NOT REQUIRED YET
        this.operationFee = 5;
        this.setBalance(-operationFee, SharedConstants.USD);
        this.setBalance(-operationFee, SharedConstants.CNY);
        this.setBalance(-operationFee, SharedConstants.YEN);
    }

    public void setBalance(double amount, String currency) {
        switch (currency) {
            case SharedConstants.USD:
                this.usd.addBalance(amount);
                break;
            case SharedConstants.CNY:
                this.cny.addBalance(amount);
                break;
            case SharedConstants.YEN:
                this.yen.addBalance(amount);
                break;
        }
    }

    public double getBalance(String currency) {
        switch (currency) {
            case SharedConstants.USD:
                return this.usd.getBalance();
            case SharedConstants.CNY:
                return this.cny.getBalance();
            case SharedConstants.YEN:
                return this.yen.getBalance();
        }
        return -1;
    }

    public int getDebitCardNumber() {
        return this.debitCardNumber;
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
