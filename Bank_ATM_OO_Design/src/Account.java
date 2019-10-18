public class Account {
    private String accountID;
    private int bankID;
    private int userID;
    private double balance;
    private boolean status;

    Account(double balance, int bankID, int userID) {
        this.accountID = bankID + "_" + userID;
        this.bankID = bankID;
        this.userID = userID;
        this.balance = balance;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public int getBankID() {
        return this.bankID;
    }

    public int getUserID() {
        return this.userID;
    }

    public double getBalance() {
        return this.balance;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void close() {
        this.status = false;
    }

}
