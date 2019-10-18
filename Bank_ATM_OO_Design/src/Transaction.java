public class Transaction {
    private String transactionID;
    private int accountID;
    private int userID;
    private String status;
    private int creationDay;
    private Currency currency;
    private double operationFee;

    Transaction(int accountID, int userID, int creationDay, Currency currency, double operationFee) {
        this.transactionID = accountID + "_" + userID;
        this.accountID = accountID;
        this.userID = userID;
        this.creationDay = creationDay;
        this.currency = currency;
        this.operationFee = operationFee;
    }

    public void startTransaction() {

    }


}
