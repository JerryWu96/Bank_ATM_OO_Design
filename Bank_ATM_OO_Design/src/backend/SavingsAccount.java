package backend;
/**
 * Savings account. We can add withdrawTimesLimit in the future to make it more realistic.
 */
public class SavingsAccount extends Account {
    private double operationFee;
    private USD usd;

    SavingsAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + "_" + userID + "_SAV_" + postfix, bankID, userID, accountType);

        this.operationFee = SharedConstants.OPERATION_FEE;
        this.usd = new USD(-operationFee);
    }

    /**
     * check whether balance is above the threshold where there interests start to be calculated
     * @return
     */
    private boolean doesHitThreshold() {
        return this.getBalance() >= SharedConstants.SAVINGS_AMOUNT_THRESHOLD;
    }

    public void setBalance(Currency currency) {
        this.usd.addBalance(currency.convertToUSD());
    }

    public double getBalance() {
        return this.usd.getBalance();
    }

    /**
     * calculate interests and then apply
     */
    public void computeInterest() {
        if (doesHitThreshold()) {
            double currentBalance = usd.getBalance();
            usd.addBalance(currentBalance * SharedConstants.SAVINGS_INTEREST_RATE);
        }
    }
}
