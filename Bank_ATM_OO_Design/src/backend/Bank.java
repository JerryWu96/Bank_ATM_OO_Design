package backend;

import db.DatabasePortal;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Class encapsulates a bank. It stores data related to this bank, such as accounts.
 */
public class Bank {
    private String bankName;
    private String bankID;

    private double operationFee = SharedConstants.OPERATION_FEE;
    private double loanInterestRate = SharedConstants.LOAN_INTEREST_RATE;

    private String[] currencyList;
    private List<Manager> managerList;
    private List<Customer> customerList;
    private List<SavingsAccount> savingsList;
    private List<CheckingAccount> checkingList;
    private List<SecurityAccount> securityList;

    private Map<String, CheckingAccount> checkingMap;
    private Map<String, SavingsAccount> savingsMap;
    private Map<String, SecurityAccount> securityMap;
    private Map<String, Integer> checkingCountMap;
    private Map<String, Integer> savingsCountMap;
    private Map<String, Integer> securityCountMap;

    public Bank() {
        this.bankName = SharedConstants.BANK_NAME;
        this.bankID = SharedConstants.BANK_ID;
        this.savingsList = new ArrayList<>();
        this.checkingList = new ArrayList<>();
        this.securityList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.checkingMap = new HashMap<>();
        this.savingsMap = new HashMap<>();
        this.securityMap = new HashMap<>();
        this.checkingCountMap = new HashMap<>();
        this.savingsCountMap = new HashMap<>();
        this.securityCountMap = new HashMap<>();
        this.currencyList = new String[]{SharedConstants.USD, SharedConstants.YEN, SharedConstants.CNY};

        readStateFromDB();
        // initial customer/manager
        customerList.add(new Customer("a", "a", "a"));
        managerList.add(new Manager("m", "m", "m", SharedConstants.BANK_ID));
    }

    private void readStateFromDB() {
        DatabasePortal myDB = DatabasePortal.getInstance();

        // read Accounts
        List<Account> accountList = myDB.getAccountList();
        for (Account account : accountList)  {
            String userID = account.getUserID();
            String accountID = account.getAccountID();
            switch (account.getAccountType()) {
                case SharedConstants.CK:
                    this.checkingList.add((CheckingAccount)account);
                    this.checkingMap.put(accountID, (CheckingAccount)account);
                    this.checkingCountMap.put(userID, this.checkingCountMap.getOrDefault(userID, 0) + 1);
                case SharedConstants.SAV:
                    this.savingsList.add((SavingsAccount)account);
                    this.savingsMap.put(accountID, (SavingsAccount) account);
                    this.savingsCountMap.put(userID, this.savingsCountMap.getOrDefault(userID, 0) + 1);
                case SharedConstants.SEC:
                    this.securityList.add((SecurityAccount) account);
                    this.securityMap.put(accountID, (SecurityAccount) account);
                    this.securityCountMap.put(userID, this.securityCountMap.getOrDefault(userID, 0) + 1);
            }
        }
        // read customers
        this.customerList.addAll(myDB.getCustomerList());
        // read managers
        this.managerList.addAll(myDB.getManagerList());
    }

    public void saveStateToDB() {
        DatabasePortal myDB = DatabasePortal.getInstance();

    }



    public String getBankName() {
        return this.bankName;
    }

    public String getBankID() {
        return this.bankID;
    }

    public List<SavingsAccount> getSavings() {
        return this.savingsList;
    }

    public List<CheckingAccount> getCheckings() {
        return this.checkingList;
    }

    public List<SecurityAccount> getSecurities() {
        return this.securityList;
    }

    /**
     * get the name of a customer or manager
     *
     * @param userID   (identification of a user)
     * @param identity (whether customer or manager)
     * @return name (user's name)
     */
    public String getUserName(String userID, String identity) {
        switch (identity) {
            case SharedConstants.CUSTOMER:
                for (Customer customer : customerList) {
                    if (customer.getUserID().equals(userID)) {
                        return customer.getName();
                    }
                }
            case SharedConstants.MANAGER:
                for (Manager manager : managerList) {
                    if (manager.getUserID().equals(userID)) {
                        return manager.getName();
                    }
                }
        }
        return "";
    }

    /**
     * create a new user (customer or manager) and add it to the list
     *
     * @param userID   (identification of a user)
     * @param password
     * @param name     (user's name)
     * @param identity (whether customer or manager)
     */
    public void addUser(String userID, String password, String name, String identity) {
        switch (identity) {
            case SharedConstants.CUSTOMER:
                customerList.add(new Customer(name, userID, password));
                break;
            case SharedConstants.MANAGER:
                managerList.add(new Manager(name, userID, password, this.bankID));
                break;
        }
    }

    /**
     * open a new account for a user
     *
     * @param userID      (identification of a user)
     * @param accountType (checking, savings)
     * @return opened account id if succeed, or error message
     */
    public String openAccount(String userID, String accountType) {
        switch (accountType) {
            case SharedConstants.CK:
                CheckingAccount newCheckingAcc = new CheckingAccount(this.bankID, userID, SharedConstants.CK, checkingCountMap.getOrDefault(userID, 0));
                this.checkingList.add(newCheckingAcc);
                this.checkingMap.put(newCheckingAcc.getAccountID(), newCheckingAcc);
                this.checkingCountMap.put(userID, this.checkingCountMap.getOrDefault(userID, 0) + 1);
                return newCheckingAcc.getAccountID();
            case SharedConstants.SAV:
                SavingsAccount newSavingAcc = new SavingsAccount(this.bankID, userID, SharedConstants.SAV, savingsCountMap.getOrDefault(userID, 0));
                this.savingsList.add(newSavingAcc);
                this.savingsMap.put(newSavingAcc.getAccountID(), newSavingAcc);
                this.savingsCountMap.put(userID, this.savingsCountMap.getOrDefault(userID, 0) + 1);
                return newSavingAcc.getAccountID();
        }
        return SharedConstants.ERR_OPEN_ACCOUNT;
    }


    /**
     * open a new security account. This is an overloaded method
     *
     * @param userID
     * @param accountType  (security)
     * @param savAccountID
     * @return opened account id if succeed, or error message
     */
    public String openAccount(String userID, String accountType, String savAccountID) {
        switch (accountType) {
            case SharedConstants.SEC:
                SavingsAccount savingsAccount = getSavingsAccount(savAccountID);
                if (savingsAccount != null) {
                    if (savingsAccount.higherThanThreshold()) {
                        SecurityAccount newSecurityAcc = new SecurityAccount(this.bankID, userID, SharedConstants.SEC,
                                securityCountMap.getOrDefault(userID, 0), savAccountID);
                        this.securityList.add(newSecurityAcc);
                        this.securityMap.put(newSecurityAcc.getAccountID(), newSecurityAcc);
                        this.securityCountMap.put(userID, this.securityCountMap.getOrDefault(userID, 0) + 1);
                        return newSecurityAcc.getAccountID();
                    } else {
                        return SharedConstants.ERR_INSUFFICIENT_BALANCE;
                    }
                }
        }
        return SharedConstants.ERR_OPEN_ACCOUNT;
    }

    /**
     * close the corresponding account
     *
     * @param userID
     * @param accountID
     * @param accountType
     * @return message about whether it succeeds or fails
     */
    public String closeAccount(String userID, String accountID, String accountType) {
        switch (accountType) {
            case SharedConstants.CK:
                if (checkingCountMap.isEmpty() || checkingCountMap.get(userID) == 0 || !checkingMap.containsKey(accountID)) {
                    return SharedConstants.ERR_ACCOUNT_NOT_EXIST;
                }
                checkingCountMap.replace(userID, checkingCountMap.get(userID) - 1);
                checkingMap.remove(accountID);
                for (CheckingAccount checking : checkingList) {
                    if (checking.getAccountID().equals(accountID)) {
                        checkingList.remove(checking);
                        break;
                    }
                }
                return SharedConstants.SUCCESS_CLOSE_ACCOUNT;
            case SharedConstants.SAV:
                if (savingsCountMap.isEmpty() || savingsCountMap.get(userID) == 0 || !savingsMap.containsKey(accountID)) {
                    return SharedConstants.ERR_ACCOUNT_NOT_EXIST;
                }
                savingsCountMap.replace(userID, savingsCountMap.get(userID) - 1);
                savingsMap.remove(accountID);
                for (SavingsAccount saving : savingsList) {
                    if (saving.getAccountID().equals(accountID)) {
                        savingsList.remove(saving);
                        break;
                    }
                }
                return SharedConstants.SUCCESS_CLOSE_ACCOUNT;
        }
        return SharedConstants.ERR_CLOSE_ACCOUNT;
    }


    /**
     * make a deposit
     *
     * @param accountID
     * @param amount
     * @param selectedCurrency
     * @return success message
     */
    public String deposit(String accountID, double amount, String selectedCurrency) {
        setAccountBalance(accountID, amount, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    /**
     * make a withdraw
     *
     * @param accountID
     * @param amount
     * @param selectedCurrency
     * @return message about success or failure
     */
    public String withdraw(String accountID, double amount, String selectedCurrency) {
        double balance = getAccountBalance(accountID);
        if (balance - operationFee - amount < 0) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        }
        setAccountBalance(accountID, -amount - operationFee, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    /**
     * make a transfer from one account to another
     *
     * @param sourceAccountID
     * @param targetAccountID
     * @param amount
     * @param selectedCurrency
     * @return message about success or failure
     */
    public String transfer(String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        double sourceBalance = getAccountBalance(sourceAccountID);
        if (sourceBalance - amount - operationFee < 0) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        }
        setAccountBalance(sourceAccountID, -amount - operationFee, selectedCurrency);
        setAccountBalance(targetAccountID, amount, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    /**
     * request a loan, success if user has enough collaterals
     *
     * @param userID
     * @param amount
     * @param selectedCurrency
     * @return message success or failure
     */
    public String takeLoan(String userID, double amount, String selectedCurrency) {
        if (getCustomerCollateral(userID) == 0) {
            return SharedConstants.ERR_INSUFFICIENT_COLLATERAL;
        } else {
            for (Customer customer : customerList) {
                if (customer.getUserID().equals(userID)) {
                    String loanID = customer.addLoan(amount, loanInterestRate, selectedCurrency);
                    return loanID;
                }
            }
        }
        return SharedConstants.ERR_ACCOUNT_NOT_EXIST;
    }

    /**
     * pay off a loan
     *
     * @param userID
     * @param loanID
     * @return success message
     */
    public String payoffLoan(String userID, String loanID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                customer.payoffLoan(loanID);
            }
        }
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    /**
     * buy stock
     *
     * @param secAccountID
     * @param stockID
     * @param unit
     * @return success message
     */
    public String buyStock(String secAccountID, String stockID, int unit, String company, double price) {
        for (SecurityAccount securityAccount : securityList) {
            if (securityAccount.getAccountID().equals(secAccountID)) {
                String result = securityAccount.buyStock(stockID, unit, company, price);
                return result;
            }
        }
        return SharedConstants.ERR_STOCK_NOT_EXIST;
    }

    /**
     * sell some stock
     *
     * @param secAccountID (security accout identification)
     * @param stockID
     * @param unit
     * @return success message
     */
    public String sellStock(String secAccountID, String stockID, int unit, String company, double price) {
        for (SecurityAccount securityAccount : securityList) {
            if (securityAccount.getAccountID().equals(secAccountID)) {
                String result = securityAccount.sellStock(stockID, unit, company, price);
                return result;
            }
        }
        return SharedConstants.ERR_ACCOUNT_NOT_EXIST;
    }


    public double getOperationFee() {
        return operationFee;
    }

    private double getAccountBalance(String accountID) {
        double balance = 0;
        if (isCheckingAccount(accountID)) {
            balance = checkingMap.get(accountID).getBalance();
        } else if (isSavingsAccount(accountID)) {
            balance = savingsMap.get(accountID).getBalance();
        }
        return balance;
    }

    private void setAccountBalance(String accountID, double balanceDiff, String selectedCurrency) {
        Currency currency = getCurrencyObj(balanceDiff, selectedCurrency);
        if (isCheckingAccount(accountID)) {
            checkingMap.get(accountID).setBalance(currency);
        } else if (isSavingsAccount(accountID)) {
            savingsMap.get(accountID).setBalance(currency);
        }
    }

    /**
     * factory design pattern that returns a Currency obj given its balance and currency name
     *
     * @param balanceDiff
     * @param selectedCurrency
     * @return
     */
    private Currency getCurrencyObj(double balanceDiff, String selectedCurrency) {
        switch (selectedCurrency) {
            case SharedConstants.CNY:
                return new CNY(balanceDiff);
            case SharedConstants.USD:
                return new USD(balanceDiff);
            case SharedConstants.YEN:
                return new CNY(balanceDiff);
        }
        return null;
    }

    /**
     * compute interests of balance and loan
     */
    public void computeInterest() {
        for (SavingsAccount saving : savingsList) {
            saving.computeInterest();
        }
        for (Customer customer : customerList) {
            customer.computeLoanInterest();
        }
    }

    public boolean isCheckingAccount(String accountID) {
        return checkingMap.containsKey(accountID);
    }

    public boolean isSavingsAccount(String accountID) {
        return savingsMap.containsKey(accountID);
    }

    public boolean isSecurityAccount(String accountID) {
        return securityMap.containsKey(accountID);
    }


    public CheckingAccount getCheckingAccount(String accountID) {
        return this.checkingMap.getOrDefault(accountID, null);
    }

    public SavingsAccount getSavingsAccount(String accountID) {
        return this.savingsMap.getOrDefault(accountID, null);
    }

    public SecurityAccount getSecurityAccount(String accountID) {
        return this.securityMap.getOrDefault(accountID, null);
    }

    /**
     * get a String array of accountID given a specific accountType
     *
     * @param accountType type of the account.
     * @return array of accountID
     */
    public String[] getAccountList(String accountType) {
        List<String> accountList = new ArrayList<>();
        switch (accountType) {
            case SharedConstants.CK:
                for (CheckingAccount checking : checkingList) {
                    accountList.add(checking.getAccountID());
                }
                break;
            case SharedConstants.SAV:
                for (SavingsAccount saving : savingsList) {
                    accountList.add(saving.getAccountID());
                }
                break;
            case SharedConstants.SEC:
                for (SecurityAccount securityAccount : securityList) {
                    accountList.add(securityAccount.getAccountID());
                }
                break;
        }
        return accountList.toArray(new String[0]);
    }

    public String[] getLoanList(String userID) {
        List<String> loanIDList = new ArrayList<>();
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                loanIDList = customer.getLoans();
            }
        }
        return loanIDList.toArray(new String[0]);
    }

    public String[] getCurrencyList() {
        return this.currencyList;
    }

    /**
     * check if this account belongs to this user
     *
     * @param userID
     * @param accountID
     * @return true if it does, false otherwise
     */
    public boolean isUserAccount(String userID, String accountID) {
        if (isCheckingAccount(accountID) && getCheckingAccount(accountID).getUserID().equals(userID)) {
            return true;
        } else if (isSavingsAccount(accountID) && getSavingsAccount(accountID).getUserID().equals(userID)) {
            return true;
        } else if (isSecurityAccount(accountID) && getSecurityAccount(accountID).getUserID().equals(userID)) {
            return true;
        }
        return false;
    }

    /**
     * check if the given user is a manager
     *
     * @param userID
     * @return
     */
    public boolean isManager(String userID) {
        for (Manager manager : managerList) {
            if (manager.getUserID().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the number of collaterals owned by this user
     *
     * @param userID
     * @return number of collaterals, or -1 if it fails
     */
    public int getCustomerCollateral(String userID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                return customer.getCollateral();
            }
        }
        return -1;
    }

    /**
     * get all customer ID
     * @return String Array
     */
    public String[] getCustomerList() {
        List<String> customerIDList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerIDList.add(customer.getUserID());
        }
        return customerIDList.toArray(new String[0]);
    }

    public List<Customer> getCustomers() {
        return customerList;
    }

    public List<Manager> getManagers() {
        return managerList;
    }

    /**
     * get the number of some type of accounts owned by this user
     *
     * @param userID
     * @param accountType
     * @return the number, or -1 if it fails
     */
    public Integer getAccountNumber(String userID, String accountType) {
        switch (accountType) {
            case SharedConstants.CK:
                return this.checkingCountMap.getOrDefault(userID, 0);
            case SharedConstants.SAV:
                return this.savingsCountMap.getOrDefault(userID, 0);
            case SharedConstants.SEC:
                return this.securityCountMap.getOrDefault(userID, 0);
        }
        return -1;
    }

    /**
     * check if id matches password for both types of users
     *
     * @param userID
     * @param password
     * @param identity (customer or manager)
     * @return message about success or failure
     */
    public String authenticateUser(String userID, String password, String identity) {
        switch (identity) {
            case SharedConstants.MANAGER:
                if (managerList.isEmpty()) {
                    System.out.println(SharedConstants.ERR_ACCOUNT_NOT_EXIST);
                    return SharedConstants.ERR_USER_NOT_EXIST;
                }
                for (Manager manager : managerList) {
                    if (manager.getUserID().equals(userID) && manager.getPassword().equals(password)) {
                        BankPortal.getInstance().userLogin(userID);
                        return SharedConstants.SUCCESS_AUTHENTICATE_USER;
                    } else if (manager.getUserID().equals(userID) && !manager.getPassword().equals(password)) {
                        return SharedConstants.ERR_WRONG_PASS;
                    }
                }
                return SharedConstants.ERR_USER_NOT_EXIST;
            case SharedConstants.CUSTOMER:
                if (customerList.isEmpty()) {
                    return SharedConstants.ERR_USER_NOT_EXIST;
                }
                for (Customer customer : customerList) {
                    if (customer.getUserID().equals(userID) && customer.getPassword().equals(password)) {
                        BankPortal.getInstance().userLogin(userID);
                        return SharedConstants.SUCCESS_AUTHENTICATE_USER;
                    } else if (customer.getUserID().equals(userID) && !customer.getPassword().equals(password)) {
                        return SharedConstants.ERR_WRONG_PASS;
                    }
                }
                return SharedConstants.ERR_USER_NOT_EXIST;
        }
        return SharedConstants.ERR_INVALID_ARGUMENT;
    }

}
