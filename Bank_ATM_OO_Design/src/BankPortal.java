/**
 * Class that serves as a centralized bank management interface that processes requests sent from GUI,
 * executed corresponding methods and decouples some operations from Bank class, which
 * makes it easier to add more banks in the future.
 */
public class BankPortal {
    private int day;

    private String userID;
    private Bank bank; // Currently we only support one bank
    private WelcomePanel welcomePanel;

    private static BankPortal bankPortal = null;

    BankPortal() {
        this.day = 0; // initial start day
        this.bank = new Bank(); // set up our Bank
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

    public Bank getBank() {
        return this.bank;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void userLogin(String userID) {
        this.setUserID(userID);
    }

    public void openAccount(String bankID, String userID, String accountType) {
        String newAccountID = this.bank.openAccount(userID, accountType);
        BankLogger.getInstance().addAccount(newAccountID);
    }

    public String closeAccount(String userID, String accountID, String accountType) {
        return this.bank.closeAccount(userID, accountID, accountType);
    }

    public void deposit(String userID, String accountID, double amount, String selectedCurrency) {
        Deposit deposit = new Deposit(accountID, userID, this.day, selectedCurrency, amount);
        deposit.startTransaction();
        BankLogger.getInstance().addTransaction(deposit);
    }

    public String withdraw(String userID, String accountID, double amount, String selectedCurrency) {
        Withdraw withdraw = new Withdraw(accountID, userID, this.day, selectedCurrency, amount);
        String result = withdraw.startTransaction();
        BankLogger.getInstance().addTransaction(withdraw);
        return result;
    }

    public String transfer(String userID, String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        Transfer transfer = new Transfer(sourceAccountID, targetAccountID, userID, this.day, selectedCurrency, amount);
        String result = transfer.startTransaction();
        BankLogger.getInstance().addTransaction(transfer);
        return result;
    }

    public String takeLoan(String userID, double amount, String selectedCurrency) {
        LoanCreate loanCreate = new LoanCreate(userID, this.day, selectedCurrency, amount);
        String result = loanCreate.startTransaction();
        BankLogger.getInstance().addTransaction(loanCreate);
        return result;
    }

    public String payoffLoan(String loanID) {
        LoanPayOff loanPayOff = new LoanPayOff(userID, this.day, loanID);
        String result = loanPayOff.startTransaction();
        BankLogger.getInstance().addTransaction(loanPayOff);
        return result;
    }

    public int getCustomerCollateral() {
        return this.bank.getCustomerCollateral(userID);
    }

    public void nextDay() {
        this.day++;
        BankLogger.getInstance().nextDay();
        bank.computeInterest();
    }

    public String getUserInfo(String userID) {
        String displayContent = "";
        int CKCount = this.bank.getAccountNumber(userID, SharedConstants.CK);
        int SAVCount = this.bank.getAccountNumber(userID, SharedConstants.SAV);
        displayContent += "Name: " + this.bank.getUserName(userID, SharedConstants.CUSTOMER) + "\n";
        displayContent += "ID: " + userID + "\n";
        displayContent += "Total Accounts: " + (CKCount + SAVCount) + "\n";
        displayContent += "Operation fee: 5 units for all currencies\n";

        if (CKCount == 0) {
            displayContent += "You have not opened any Checking account yet!\n";
        }
        if (SAVCount == 0) {
            displayContent += "You have not opened any Savings account yet!\n";
        }

        for (CheckingAccount account : this.bank.getCheckings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nCK AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD " + account.getBalance();
            }
        }
        for (SavingsAccount account : this.bank.getSavings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nSAV AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD " + account.getBalance();
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
