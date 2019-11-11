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
        this.transactionID = day + SharedConstants.DELIMITER + type + SharedConstants.DELIMITER + userID;
        this.accountID = accountID;
        this.userID = userID;
        this.day = day;
        this.selectedCurrency = selectedCurrency;
        this.type = type;
    }


    /**
     * Constructor chaining. This constructor is called when the transaction does not include selectedCurrency
     *
     * @param userID
     * @param day
     * @param accountID
     * @param type
     */
    Transaction(String userID, int day, String accountID, String type) {
        this(accountID, userID, day, null, type);
    }

    /**
     * Constructor chaining. This constructor is called when the transaction includes neither accountID nor currency type
     *
     * @param userID
     * @param day
     * @param type
     */
    Transaction(String userID, int day, String type) {
        this(null, userID, day, null, type);
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

    public String getSelectedCurrency() {
        return this.selectedCurrency;
    }

    public String getType() {
        return this.type;
    }

    public abstract String startTransaction();//must be implemented by all transaction class
}
