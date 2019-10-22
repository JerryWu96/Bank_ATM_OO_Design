/**
 * Base class of a account. If there are more account types to be added, we can inherited from this class.
 */
public class Account {
    private String accountID;
    private String bankID;
    private String userID;
    private boolean isActive;

    Account(String accountID, String bankID, String userID) {
        this.accountID = accountID;
        this.bankID = bankID;
        this.userID = userID;
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

    public boolean getStatus() {
        return this.isActive;
    }

    public void close() {
        this.isActive = false;
    }

}
