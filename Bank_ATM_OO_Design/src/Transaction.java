/**
 * Transaction base class. Different transactions (deposit, withdraw, transfer) are created based on this class.
 */
public abstract class Transaction {
    private String transactionID;
    private String accountID;
    private String userID;
    private String status;
    private int day;
    private String selectedCurrency;

    Transaction(String accountID, String userID, int day, String selectedCurrency) {
        this.transactionID = accountID + "_" + userID;
        this.accountID = accountID;
        this.userID = userID;
        this.day = day;
        this.selectedCurrency = selectedCurrency;
    }

    public String getAccountID() {
        return this.accountID;
    }
    public String getTransactionID() {
        return this.transactionID;
    }

    public String getSelectedCurrency() {
        return this.selectedCurrency;
    }
}
