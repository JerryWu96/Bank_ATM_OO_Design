/**
 * A checking account.
 */
public class CheckingAccount extends Account {
    // Current system has not implemented debit/credit card systems. This is more of a design showcase.
    private int debitCardNumber;
    private double operationFee;
    private USD usd;
    private CNY cny;
    private YEN yen;

    CheckingAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + "_" + userID + "_CK_" + postfix, bankID, userID, accountType);
        this.usd = new USD();
        this.cny = new CNY();
        this.yen = new YEN();
        this.debitCardNumber = 0;
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

    public int getDebitCardNumber() {
        return this.debitCardNumber;
    }

    public double getOperationFee() {
        return this.operationFee;
    }

}
