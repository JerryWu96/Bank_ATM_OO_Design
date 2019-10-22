public abstract class Transaction {
    private String transactionID;
    private String accountID;
    private String userID;
    private String status;
    private int creationDay;
    private String selectedCurrency;
    private double operationFee;

    Transaction(String accountID, String userID, int creationDay, String selectedCurrency, double operationFee) {
        this.transactionID = accountID + "_" + userID;
        this.accountID = accountID;
        this.userID = userID;
        this.creationDay = creationDay;
        this.selectedCurrency = selectedCurrency;
        this.operationFee = operationFee;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public String getSelectedCurrency() {
        return this.selectedCurrency;
    }

    public abstract void startTransaction();

}
