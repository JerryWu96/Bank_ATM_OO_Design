package backend;
/**
 * Transaction base class. Different transactions (deposit, withdraw, transfer) are created based on this class.
 */
public abstract class Transaction {
    private String transactionID;
    private String accountID;
    private String userID;
    private String type;
    private int day;
    private String selectedCurrency;

    Transaction(String accountID, String userID, int day, String selectedCurrency, String type) {
        this.transactionID = accountID + SharedConstants.DELIMITER + userID;
        this.accountID = accountID;
        this.userID = userID;
        this.day = day;
        this.selectedCurrency = selectedCurrency;
        this.type = type;
    }

    Transaction(String userID, int day, String selectedCurrency, String type) {
        this.transactionID = accountID + SharedConstants.DELIMITER + userID;
        this.accountID = null;
        this.userID = userID;
        this.day = day;
        this.selectedCurrency = selectedCurrency;
        this.type = type;
    }

    public int getDay() {
        return this.day;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getTransactionID() {
        return this.transactionID;
    }

    public String   getSelectedCurrency() {
        return this.selectedCurrency;
    }

    public String getType() {
        return this.type;
    }

    public abstract String startTransaction();
}
