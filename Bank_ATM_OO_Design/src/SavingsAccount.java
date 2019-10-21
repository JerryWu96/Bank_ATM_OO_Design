public class SavingsAccount extends Account {
    private double withdrawTimesLimit;
    private double operationFee;
    private USD usd;
    private CNY cny;
    private YEN yen;

    SavingsAccount(double balance, String bankID, String userID, String postfix) {
        super(bankID + "_" + userID + "_SAV_" + postfix, balance, bankID, userID);
        this.usd = new USD();
        this.cny = new CNY();
        this.yen = new YEN();
        this.withdrawTimesLimit = 5;
        this.operationFee = 5;
        this.setBalance(-operationFee);
    }

    public double getWithdrawLimit() {
        return this.getWithdrawLimit();
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
