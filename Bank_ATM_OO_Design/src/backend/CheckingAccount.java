package backend;
/**
 * A checking account.
 */
public class CheckingAccount extends Account {
    // Current system has not implemented debit/credit card systems. This is more of a design showcase.
    private int debitCardNumber; // NOT REQUIRED YET
    private double operationFee;
    private USD usd;

    CheckingAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + "_" + userID + "_CK_" + postfix, bankID, userID, accountType);
        this.operationFee = SharedConstants.OPERATION_FEE;
        this.usd = new USD(-operationFee);
        this.debitCardNumber = -1; // NOT REQUIRED YET
    }

    public void setBalance(Currency currency) {
        this.usd.addBalance(currency.convertToUSD());
    }

    public double getBalance() {
      return this.usd.getBalance();
    }

    public int getDebitCardNumber() {
        return this.debitCardNumber;
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
