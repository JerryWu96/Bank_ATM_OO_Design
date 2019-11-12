package backend;

/**
 * Transaction: request loan
 */
public class LoanCreate extends Transaction {
    private double loanAmount;
    private String loanID;

    public LoanCreate(String userID, int creationDay, String selectedCurrency, double loanAmount) {
        super(userID, creationDay, selectedCurrency, SharedConstants.LOAN_CREATE);
        this.loanAmount = loanAmount;
        this.loanID = null;
    }

    /**
     * execute transaction: request loan
     */
    public String startTransaction() {
        String result = BankPortal.getInstance().getBank().takeLoan(getUserID(), getLoanAmount(), getSelectedCurrency());
        if (result.equals(SharedConstants.ERR_INSUFFICIENT_COLLATERAL) || result.equals(SharedConstants.ERR_ACCOUNT_NOT_EXIST)) {
            return result;
        } else {
            this.loanID = result;
            return SharedConstants.SUCCESS_TRANSACTION;
        }
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public String getLoanID() {
        return this.loanID;
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " got a " + getSelectedCurrency() +
                getLoanAmount() + " loan with interest rate: " + SharedConstants.LOAN_INTEREST_RATE + "\n";
    }
}

