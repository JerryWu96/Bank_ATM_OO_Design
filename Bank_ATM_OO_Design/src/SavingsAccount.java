public class SavingsAccount extends Account {
    private double withdrawTimesLimit;
    private double operationFee;
    private USD usd;
    private CNY cny;
    private YEN yen;

    SavingsAccount(String bankID, String userID, String postfix) {
        super(bankID + "_" + userID + "_SAV_" + postfix, bankID, userID);
        this.usd = new USD();
        this.cny = new CNY();
        this.yen = new YEN();
        this.withdrawTimesLimit = 5;
        this.operationFee = 5;
        this.setBalance(-operationFee, "USD");
        this.setBalance(-operationFee, "CNY");
        this.setBalance(-operationFee, "YEN");
    }

    public void setBalance(double amount, String currency) {
        switch (currency) {
            case "USD":
                this.usd.setAmount(amount);
                break;
            case "CNY":
                this.cny.setAmount(amount);
                break;
            case "YEN":
                this.yen.setAmount(amount);
                break;
        }
    }

    public double getBalance(String currency) {
        switch (currency) {
            case "USD":
                return this.usd.getBalance();
            case "CNY":
                return this.cny.getBalance();
            case "YEN":
                return this.yen.getBalance();
        }
        return 0;
    }

    public double getWithdrawLimit() {
        return this.getWithdrawLimit();
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
