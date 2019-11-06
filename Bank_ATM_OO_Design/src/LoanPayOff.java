public class LoanPayOff extends Transaction {
    private String loanID;

    LoanPayOff(String userID, int creationDay, String loanID) {
        super(userID, creationDay, null, "LoanPayOff");
        this.loanID = loanID;
    }

    public String startTransaction() {
        return BankPortal.getInstance().getBank().payoffLoan(getUserID(), getLoanID());
    }

    public String getLoanID() {
        return this.loanID;
    }
}

