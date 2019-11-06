package backend;
/**
 * Base class of a account. If there are more account types to be added, we can inherited from this class.
 */
public class Account {
    private String accountID;
    private String bankID;
    private String userID;
    private String accountType;

    Account(String accountID, String bankID, String userID, String accountType) {
        this.accountID = accountID;
        this.bankID = bankID;
        this.userID = userID;
        this.accountType = accountType;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public String getBankID() {
        return this.bankID;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getAccountType() {
        return this.accountType;
    }
}
