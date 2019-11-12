package backend;
/**
 * Transaction: pay off loan
 */
public class LoanPayOff extends Transaction {
    private String loanID;

    public LoanPayOff(String userID, int creationDay, String loanID) {
        super(userID, creationDay, SharedConstants.USD, SharedConstants.LOAN_PAY_OFF);
        this.loanID = loanID;
    }

    /**
     * execute transaction: pay off loan
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().payoffLoan(getUserID(), getLoanID());
    }

    public String getLoanID() {
        return this.loanID;
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " paid off a loan " + getLoanID() + "\n";
    }
}

