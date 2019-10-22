import com.sun.tools.javac.comp.Check;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BankPortal {
    private int sessionID;
    private String userID;
    private int day;
    private welcomePanel welcomePanel;
    private boolean isUserLogin;
    private boolean isUserLogout;
    private static BankPortal bankPortal = null;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0; // initial sessionID
        this.isUserLogin = false;
        this.isUserLogout = true;
    }

    public static BankPortal getInstance() {
        if (bankPortal == null) {
            bankPortal = new BankPortal();
        }
        return bankPortal;
    }

    public void run() {
        welcomePanel = new welcomePanel();
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void updateSessionID() {
        this.sessionID++;
    }

    public void userLogin(String userID) {
        System.out.println("User " + userID + " logged in!");
        BankPortal.getInstance().setUserID(userID);
        BankPortal.getInstance().updateSessionID();
    }

    public void userSignout() {
        System.out.println("User " + userID + " signed out!");
    }


    public CheckingAccount openCheckingAccount(String bankID, String userID) {
        Integer accountCount = Bank.getInstance().getUserCheckingCount(userID);
        CheckingAccount newAccount = new CheckingAccount(bankID, userID, Integer.toString(accountCount));
        Bank.getInstance().openCheckingAccount(newAccount);
        return newAccount;
    }

    public SavingsAccount openSavingsAccount(String bankID, String userID) {
        Integer accountCount = Bank.getInstance().getUserSavingsCount(userID);
        SavingsAccount newAccount = new SavingsAccount(bankID, userID, Integer.toString(accountCount));
        Bank.getInstance().openSavingsAccount(newAccount);
        return newAccount;
    }

    public String closeCheckingAccount(String userID, String accountID) {
        return Bank.getInstance().closeCheckingAccount(userID, accountID);
    }

    public String closeSavingsAccount(String userID, String accountID) {
        return Bank.getInstance().closeSavingsAccount(userID, accountID);
    }

    public void deposit(String userID, String accountID, double amount, String selectedCurrency) {
        Deposit depositTransaction = new Deposit(accountID, userID, this.day, selectedCurrency, amount);
        depositTransaction.startTransaction();
        BankLogger.getInstance().addDeposit(depositTransaction);
    }

    public String makeTransaction(String userID, String sourceAccountID, String targetAccountID, double amount, String requestType) {
        return null;
    }

//    public Double balanceInquiry(String accountID, String type) {
//        switch (type) {
//            case "CK":
//                return Bank.getInstance().getCheckingAccount(accountID).getBalance();
//            case "SAV":
//                return Bank.getInstance().getSavingsAccount(accountID).getBalance();
//        }
//        return -1.0;
//    }

    public void takeLoan() {

    }

    public int getDay() {
        return this.day;
    }
    private boolean doesCustomerExist(String userID) {
        return Bank.getInstance().doesCustomerExist(userID);
    }

    private boolean doesManagerExist(String userID) {
        return Bank.getInstance().doesManagerExist(userID);
    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
