public class Account {
    private String accountID;
    private String bankID;
    private String userID;
    private double balance;
    private boolean isActive;

    Account(String accountID, double balance, String bankID, String userID) {
        this.accountID = accountID;
        this.bankID = bankID;
        this.userID = userID;
        this.balance = balance;
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

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double difference) {
        this.balance += difference;
    }

    public boolean getStatus() {
        return this.isActive;
    }

    public void close() {
        this.isActive = false;
    }

}
