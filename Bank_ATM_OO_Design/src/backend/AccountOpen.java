package backend;

public class AccountOpen extends Transaction {
    private String accountType;
    private String savAccountID;
    private String accountID;

    /**
     * constructor for a checking/savings account
     *
     * @param userID
     * @param creationDay
     * @param accountType
     */
    public AccountOpen(String userID, int creationDay, String accountType) {
        super(userID, creationDay, SharedConstants.ACCOUNT_OPEN);
        this.accountType = accountType;
    }

    /**
     * constructor for a security account. Constructor chaining is applied.
     *
     * @param userID
     * @param creationDay
     * @param accountType
     * @param savAccountID security account needs a savings account to work with
     */
    AccountOpen(String userID, int creationDay, String accountType, String savAccountID) {
        this(userID, creationDay, accountType);
        this.savAccountID = savAccountID;
    }

    /**
     * execute transaction: open a new account
     *
     * @return Error if the accountType is invalid
     */
    public String startTransaction() {
        switch (accountType) {
            case SharedConstants.CK:
                this.accountID = BankPortal.getInstance().getBank().openAccount(getUserID(), accountType);
                return this.accountID;
            case SharedConstants.SAV:
                this.accountID = BankPortal.getInstance().getBank().openAccount(getUserID(), accountType);
                return this.accountID;
            case SharedConstants.SEC:
                this.accountID = BankPortal.getInstance().getBank().openAccount(getUserID(), accountType, savAccountID);
                return this.accountID;
        }
        return SharedConstants.ERR_INVALID_TRANSACTION;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public String getAccountType() { return this.accountType; }

    public String getSavAccountID() { return this.savAccountID; }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " opened a " + this.accountType +
                " account with Operation Fee: " + SharedConstants.OPERATION_FEE + "\n";
    }
}
