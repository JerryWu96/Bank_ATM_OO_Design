package backend;
/**
 * Transaction: pay off loan
 */
public class LoanPayOff extends Transaction {
    private String loanID;

    LoanPayOff(String userID, int creationDay, String loanID) {
        super(userID, creationDay, null, "LoanPayOff");
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
}

