package backend;

import db.*;
import gui.OperationFrame;

import java.util.List;

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
        this.day = db.DatabasePortal.getInstance().restoreSession(); // restore initial start day. In default it is 0
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
     * save system states (day, accounts, users) to DB when system exits
     */
    public void saveStateToDB() {
        DatabasePortal myDB = DatabasePortal.getInstance();
        // add customers
        for (Customer customer : getBank().getCustomers()) {
            myDB.addCustomer(customer);
        }
        // add managers
        for (Manager manager : getBank().getManagers()) {
            myDB.addManager(manager);
        }
        myDB.storeSession(getDay());
    }
    /**
     * open a new account (CK or SAV)
     *
     * @param bankID
     * @param userID
     * @param accountType
     */
    public String openAccount(String bankID, String userID, String accountType) {
        AccountOpen accountOpen = new AccountOpen(userID, this.day, accountType);
        String result = accountOpen.startTransaction();
        BankLogger.getInstance().addTransaction(accountOpen);
        return result;
    }

    /**
     * open a new security account. This is an overloaded method that requires an extra param: savAccountID
     *
     * @param bankID
     * @param userID
     * @param accountType
     * @param savAccountID The savings account ID with which the security account is going to interact.
     * @return String indicates the transaction status
     */
    public String openAccount(String bankID, String userID, String accountType, String savAccountID) {
        AccountOpen accountOpen = new AccountOpen(userID, this.day, accountType, savAccountID);
        String result = accountOpen.startTransaction();
        BankLogger.getInstance().addTransaction(accountOpen);
        return result;
    }

    /**
     * close a savings/checking account. Security account can be toggled invalid but it won't be closed.
     *
     * @param userID
     * @param accountID
     * @param accountType
     * @return String indicates the transaction status
     */
    public String closeAccount(String userID, String accountID, String accountType) {
        AccountClose accountClose = new AccountClose(userID, this.day, accountID, accountType);
        String result = accountClose.startTransaction();
        BankLogger.getInstance().addTransaction(accountClose);
        return result;
    }

    /**
     * make a deposit in backend
     *
     * @param userID
     * @param accountID
     * @param amount
     * @param selectedCurrency
     */
    public String deposit(String userID, String accountID, double amount, String selectedCurrency) {
        Deposit deposit = new Deposit(accountID, userID, this.day, selectedCurrency, amount);
        String result = deposit.startTransaction();
        BankLogger.getInstance().addTransaction(deposit);
        return result;
    }

    /**
     * make a withdraw in backend
     *
     * @param userID
     * @param accountID
     * @param amount
     * @param selectedCurrency
     * @return String indicates the transaction status
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
     * @return String indicates the transaction status
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
     * @return String indicates the transaction status
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
     * @return String indicates the transaction status
     */
    public String payoffLoan(String loanID) {
        LoanPayOff loanPayOff = new LoanPayOff(userID, this.day, loanID);
        String result = loanPayOff.startTransaction();
        BankLogger.getInstance().addTransaction(loanPayOff);
        return result;
    }

    /**
     * buy stock
     *
     * @param stockID
     * @param secAccountID
     * @param unit
     * @return String indicates the transaction status
     */
    public String buyStock(String stockID, String secAccountID, int unit) {
        StockPurchase stockPurchase = new StockPurchase(userID, this.day, secAccountID, stockID, unit,
                StockMarket.getInstance().getStockCompany(stockID), StockMarket.getInstance().getStockPrice(stockID));
        String result = stockPurchase.startTransaction();
        BankLogger.getInstance().addTransaction(stockPurchase);
        return result;
    }

    /**
     * sell stock given
     *
     * @param stockID
     * @param secAccountID
     * @param unit
     * @return String indicates the transaction status
     */
    public String sellStock(String stockID, String secAccountID, int unit) {
        StockSell stockSell = new StockSell(userID, this.day, secAccountID, stockID, unit,
                StockMarket.getInstance().getStockCompany(stockID), StockMarket.getInstance().getStockPrice(stockID));
        String result = stockSell.startTransaction();
        BankLogger.getInstance().addTransaction(stockSell);
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
            DatabasePortal.getInstance().updateStockPrice(stockID, price);
            return SharedConstants.SUCCESS_UPDATE_STOCK_PRICE;
        } else {
            return SharedConstants.ERR_PERMISSION_DENIED;
        }
    }

    public List<Stock> getStocks() {
        return StockMarket.getInstance().getAllStocks();
    }

    /**
     * Get all stock IDs
     *
     * @return String array of IDs
     */
    public String[] getAllStockID() {
        return StockMarket.getInstance().getAllStockID();
    }

    /**
     * get all customer ID
     *
     * @return String Array
     */
    public String[] getCustomerList() {
        return getBank().getCustomerList();
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
     * when new day comes, add the day counter and calculate interests
     */
    public void nextDay() {
        this.day++;
        BankLogger.getInstance().nextDay();
        bank.computeInterest();
    }

    /**
     * get stocks owned by a specific sec account
     *
     * @param secAccount
     * @return
     */
    public List<Stock> getStockbySecAccount(String secAccount) {
        return getBank().getSecurityAccount(secAccount).getStockList();
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
        int SavCount = this.bank.getAccountNumber(userID, SharedConstants.SAV);
        int SecCount = this.bank.getAccountNumber(userID, SharedConstants.SAV);
        displayContent += "Name: " + this.bank.getUserName(userID, SharedConstants.CUSTOMER) + "\n";
        displayContent += "ID: " + userID + "\n";
        displayContent += "Total Accounts: " + (CKCount + SavCount + SecCount) + "\n";
        displayContent += "Operation fee: " + SharedConstants.OPERATION_FEE + " units for all currencies\n";

        if (CKCount == 0) {
            displayContent += "You have not opened any checking account yet!\n";
        }
        if (SavCount == 0) {
            displayContent += "You have not opened any savings account yet!\n";
        }
        if (SecCount == 0) {
            displayContent += "You have not opened any security account yet!\n";
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
        for (SecurityAccount account : this.bank.getSecurities()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nSEC AccountID: " + account.getAccountID();
                displayContent += "\nStocks: " + account.getStockList();
            }
        }
        return displayContent;
    }

    public int getDay() {
        return this.day;
    }

    /**
     * Method: getReportByDay.
     * Function: A manager can get a report on a specific day.
     *
     * @param requestDay: integer represents a day that the manager is interested in
     * @return String
     */
    public String getReportByDay(int requestDay) {
        Report requestReport = BankLogger.getInstance().generateReportByDay(requestDay);
        return requestReport.getContent();
    }

    /**
     * Method: getReport.
     * Function: A manager can get a report update since from the last time he ran the report.
     *
     * @return String
     */
    public String getReport() {
        return BankLogger.getInstance().generateReport();
    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
