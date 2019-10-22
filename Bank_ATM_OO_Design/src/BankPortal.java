/**
 * Class that serves as a session at the system runtime.
 * You can imagine this class as a centralized Bank System Manager.
 * It is a single point of failure, so we can even distribute it onto multiple machines.
 */
public class BankPortal {
    private int sessionID;
    private String userID;
    private int day;
    private WelcomePanel welcomePanel;
    private static BankPortal bankPortal = null;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0; // initial sessionID
    }

    public static BankPortal getInstance() {
        if (bankPortal == null) {
            bankPortal = new BankPortal();
        }
        return bankPortal;
    }

    public void run() {
        welcomePanel = new WelcomePanel();
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void updateSessionID() {
        this.sessionID++;
    }

    public void userLogin(String userID) {
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
        BankLogger.getInstance().addChecking(newAccount);
        return newAccount;
    }

    public SavingsAccount openSavingsAccount(String bankID, String userID) {
        Integer accountCount = Bank.getInstance().getUserSavingsCount(userID);
        SavingsAccount newAccount = new SavingsAccount(bankID, userID, Integer.toString(accountCount));
        Bank.getInstance().openSavingsAccount(newAccount);
        BankLogger.getInstance().addSavings(newAccount);
        return newAccount;
    }

    public String closeCheckingAccount(String userID, String accountID) {
        return Bank.getInstance().closeCheckingAccount(userID, accountID);
    }

    public String closeSavingsAccount(String userID, String accountID) {
        return Bank.getInstance().closeSavingsAccount(userID, accountID);
    }

    public void deposit(String userID, String accountID, double amount, String selectedCurrency) {
        Deposit deposit = new Deposit(accountID, userID, this.day, selectedCurrency, amount);
        deposit.startTransaction();
        BankLogger.getInstance().addDeposit(deposit);
    }

    public String withdraw(String userID, String accountID, double amount, String selectedCurrency) {
        Withdraw withdraw = new Withdraw(accountID, userID, this.day, selectedCurrency, amount);
        String result = withdraw.startTransaction();
        BankLogger.getInstance().addWithdraw(withdraw);
        return result;
    }

    public String transfer(String userID, String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        Transfer transfer = new Transfer(sourceAccountID, targetAccountID, userID, this.day, selectedCurrency, amount);
        String result = transfer.startTransaction();
        BankLogger.getInstance().addTransfer(transfer);
        return result;
    }

    public String takeLoan(String userID, double amount, String selectedCurrency) {
            return Bank.getInstance().takeLoan(userID, amount, selectedCurrency);
    }

    public void payoffLoan(String loanID) {
        Bank.getInstance().payoffLoan(userID, loanID);
    }

    public int getCustomerCollateral() {
        return Bank.getInstance().getCustomerCollateral(userID);
    }

    public void nextDay() {
        this.day++;
        BankLogger.getInstance().nextDay();
    }

    public String getAccountInfo(String userID) {
        String displayContent = "";
        int CKCount = Bank.getInstance().getUserCheckingCount(userID);
        int SAVCount = Bank.getInstance().getUserSavingsCount(userID);
        displayContent += "Name: " + Bank.getInstance().getCustomerName(userID) + "\n";
        displayContent += "ID: " + userID + "\n";
        displayContent += "Total Accounts: " + (CKCount + SAVCount) + "\n";
        displayContent += "Operation fee: 5 units for all currency\n";

        if (Bank.getInstance().getUserCheckingCount(userID) == 0) {
            displayContent += "You have not opened any Checking account yet!\n";
        }
        if (Bank.getInstance().getUserSavingsCount(userID) == 0) {
            displayContent += "You have not opened any Savings account yet!\n";
        }
        for (CheckingAccount account : Bank.getInstance().getCheckings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nCK AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD:" + account.getBalance("USD") + " CNY: " +
                        account.getBalance("CNY") + " YEN: " + account.getBalance("YEN") + "\n";
            }
        }
        for (SavingsAccount account : Bank.getInstance().getSavings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nSAV AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD:" + account.getBalance("USD") + " CNY: " +
                        account.getBalance("CNY") + " YEN: " + account.getBalance("YEN") + "\n";
            }
        }

        return displayContent;
    }

    public int getDay() {
        return this.day;
    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
