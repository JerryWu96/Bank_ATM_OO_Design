package backend;

import gui.OperationFrame;

/**
 * Class that serves as a centralized bank management interface that processes requests sent from GUI,
 * executed corresponding methods and decouples some operations from Bank class, which
 * makes it easier to add more banks in the future.
 */
public class BankPortal {
    private int day;

    private String userID;
    private Bank bank; // Currently we only support one bank

    private static BankPortal bankPortal = null;

    BankPortal() {
        this.day = 0; // initial start day
        this.bank = new Bank(); // set up our Bank
    }

    /**
     * Singleton design pattern is applied so BankPortal serves as a unified interface between backend logic and frontend GUI.
     *
     * @return instance of BankPortal
     */
    public static BankPortal getInstance() {
        if (bankPortal == null) {
            bankPortal = new BankPortal();
        }
        return bankPortal;
    }

    /**
     * run GUI
     */
    public void run() {
        OperationFrame operationFrame = OperationFrame.getInstance();
        operationFrame.run();
    }

    public Bank getBank() {
        return this.bank;
    }

    /**
     * bind the user who's interacting with GUI
     *
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void userLogin(String userID) {
        this.setUserID(userID);
    }

    /**
     * open a new account in backend
     *
     * @param bankID
     * @param userID
     * @param accountType
     */
    public void openAccount(String bankID, String userID, String accountType) {
        String newAccountID = this.bank.openAccount(userID, accountType);
        BankLogger.getInstance().addAccount(newAccountID);
    }

    /**
     * open a new Security account in backend
     *
     * @param bankID
     * @param userID
     * @param accountType
     * @param savAccountID
     */
    public void openAccount(String bankID, String userID, String accountType, String savAccountID) {
        String newAccountID = this.bank.openAccount(userID, accountType, savAccountID);
        BankLogger.getInstance().addAccount(newAccountID);
    }

    /**
     * close a savings/checking account. Security account can be toggled invalid but it won't be closed.
     *
     * @param userID
     * @param accountID
     * @param accountType
     * @return message about success or failure
     */
    public String closeAccount(String userID, String accountID, String accountType) {
        return this.bank.closeAccount(userID, accountID, accountType);
    }

    /**
     * make a deposit in backend
     *
     * @param userID
     * @param accountID
     * @param amount
     * @param selectedCurrency
     */
    public void deposit(String userID, String accountID, double amount, String selectedCurrency) {
        Deposit deposit = new Deposit(accountID, userID, this.day, selectedCurrency, amount);
        deposit.startTransaction();
        BankLogger.getInstance().addTransaction(deposit);
    }

    /**
     * make a withdraw in backend
     *
     * @param userID
     * @param accountID
     * @param amount
     * @param selectedCurrency
     * @return message about success or failure
     */
    public String withdraw(String userID, String accountID, double amount, String selectedCurrency) {
        Withdraw withdraw = new Withdraw(accountID, userID, this.day, selectedCurrency, amount);
        String result = withdraw.startTransaction();
        BankLogger.getInstance().addTransaction(withdraw);
        return result;
    }

    /**
     * make a transfer within backend
     *
     * @param userID
     * @param sourceAccountID
     * @param targetAccountID
     * @param amount
     * @param selectedCurrency
     * @return message about success or failure
     */
    public String transfer(String userID, String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        Transfer transfer = new Transfer(sourceAccountID, targetAccountID, userID, this.day, selectedCurrency, amount);
        String result = transfer.startTransaction();
        BankLogger.getInstance().addTransaction(transfer);
        return result;
    }

    /**
     * request loan
     *
     * @param userID
     * @param amount
     * @param selectedCurrency
     * @return message about success or failure
     */
    public String takeLoan(String userID, double amount, String selectedCurrency) {
        LoanCreate loanCreate = new LoanCreate(userID, this.day, selectedCurrency, amount);
        String result = loanCreate.startTransaction();
        BankLogger.getInstance().addTransaction(loanCreate);
        return result;
    }

    /**
     * pay off loan
     *
     * @param loanID
     * @return message about success or failure
     */
    public String payoffLoan(String loanID) {
        LoanPayOff loanPayOff = new LoanPayOff(userID, this.day, loanID);
        String result = loanPayOff.startTransaction();
        BankLogger.getInstance().addTransaction(loanPayOff);
        return result;
    }

    /**
     * update the stock price if the user has the permission.
     *
     * @param userID
     * @param stockID
     * @param price
     * @return String representing the update result.
     */
    public String updateStockPrice(String userID, String stockID, double price) {
        if (checkPermission(userID).equals(SharedConstants.MANAGER)) {
            StockMarket.getInstance().updateStockPrice(stockID, price);
            return SharedConstants.SUCCESS_UPDATE_STOCK_PRICE;
        } else {
            return SharedConstants.ERR_PERMISSION_DENIED;
        }
    }

    public String checkPermission(String userID) {
        if (getBank().isManager(userID)) {
            return SharedConstants.MANAGER;
        } else {
            return SharedConstants.CUSTOMER;
        }
    }

    public int getCustomerCollateral() {
        return this.bank.getCustomerCollateral(userID);
    }

    /**
     * when new day comes, add the counter and calculate interests
     */
    public void nextDay() {
        this.day++;
        BankLogger.getInstance().nextDay();
        bank.computeInterest();
    }

    /**
     * return info of a user
     *
     * @param userID
     * @return String that contains user's info
     */
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
