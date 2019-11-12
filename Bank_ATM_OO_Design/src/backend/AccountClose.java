package backend;

public class AccountClose extends Transaction {
    private String accountType;
    /**
     * constructor for a checking/savings account
     *
     * @param userID
     * @param creationDay
     * @param accountType
     */
    public AccountClose(String userID, int creationDay, String accountID, String accountType) {
        super(userID, creationDay, accountID, SharedConstants.ACCOUNT_CLOSE);
        this.accountType = accountType;
    }

    /**
     * execute transaction: open a new account
     *
     * @return Error if the accountType is invalid
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().closeAccount(getUserID(), getAccountID(), accountType);
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " closed a " + this.accountType + " account with Operation Fee: " + SharedConstants.OPERATION_FEE + "\n";
    }
}
