import javax.swing.plaf.synth.SynthEditorPaneUI;
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
    private List<SavingsAccount> savingsList;
    private List<CheckingAccount> checkingList;
    private List<Manager> managerList;
    private List<Customer> customerList;
    private String[] currencyList;
    private double operationFee = SharedConstants.OPERATION_FEE;
    private double loanInterestRate = SharedConstants.LOAN_INTEREST_RATE;

    private Map<String, CheckingAccount> checkingMap;
    private Map<String, SavingsAccount> savingsMap;
    private Map<String, Integer> checkingCountMap;
    private Map<String, Integer> savingsCountMap;

    public Bank() {
        this.bankName = SharedConstants.BANK_NAME;
        this.bankID = SharedConstants.BANK_ID;
        this.savingsList = new ArrayList<>();
        this.checkingList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.checkingMap = new HashMap<>();
        this.savingsMap = new HashMap<>();
        this.checkingCountMap = new HashMap<>();
        this.savingsCountMap = new HashMap<>();
        this.currencyList = new String[]{SharedConstants.USD, SharedConstants.YEN, SharedConstants.CNY};

        // initial customer/manager
        customerList.add(new Customer("a", "a", "a"));
        managerList.add(new Manager("a", "a", "a", SharedConstants.BANK_ID));
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


    public String openAccount(String userID, String accountType) {
        switch (accountType) {
            case SharedConstants.CK:
                CheckingAccount newChecking = new CheckingAccount(this.bankID, userID, SharedConstants.CK, checkingCountMap.getOrDefault(userID, 0));
                this.checkingList.add(newChecking);
                this.checkingMap.put(newChecking.getAccountID(), newChecking);
                this.checkingCountMap.put(userID, this.checkingCountMap.getOrDefault(userID, 0) + 1);
                return newChecking.getAccountID();
            case SharedConstants.SAV:
                SavingsAccount newSaving = new SavingsAccount(this.bankID, userID, SharedConstants.SAV, savingsCountMap.getOrDefault(userID, 0));
                this.savingsList.add(newSaving);
                this.savingsMap.put(newSaving.getAccountID(), newSaving);
                this.savingsCountMap.put(userID, this.savingsCountMap.getOrDefault(userID, 0) + 1);
                return newSaving.getAccountID();
        }
        return SharedConstants.ERR_OPEN_ACCOUNT;
    }

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
                        BankLogger.getInstance().closeAccount(accountID);
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
                        BankLogger.getInstance().closeAccount(accountID);
                        break;
                    }
                }
                return SharedConstants.SUCCESS_CLOSE_ACCOUNT;
        }
        return SharedConstants.ERR_CLOSE_ACCOUNT;
    }

    public String deposit(String accountID, double amount, String selectedCurrency) {
        setAccountBalance(accountID, amount, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    public String withdraw(String accountID, double amount, String selectedCurrency) {
        double balance = getAccountBalance(accountID);
        if (balance - operationFee - amount < 0) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        }
        setAccountBalance(accountID, -amount - operationFee, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    public String transfer(String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        double sourceBalance = getAccountBalance(sourceAccountID);
        if (sourceBalance - amount - operationFee < 0) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        }
        setAccountBalance(sourceAccountID, -amount - operationFee, selectedCurrency);
        setAccountBalance(targetAccountID, amount, selectedCurrency);
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    public String takeLoan(String userID, double amount, String selectedCurrency) {
        if (getCustomerCollateral(userID) == 0) {
            return SharedConstants.ERR_INSUFFICIENT_COLLATERAL;
        } else {
            for (Customer customer : customerList) {
                if (customer.getUserID().equals(userID)) {
                    customer.addLoan(amount, loanInterestRate, selectedCurrency);
                    return SharedConstants.SUCCESS_TRANSACTION;
                }
            }
        }
        return SharedConstants.ERR_ACCOUNT_NOT_EXIST;
    }

    public String payoffLoan(String userID, String loanID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                customer.payoffLoan(loanID);
            }
        }
        return SharedConstants.SUCCESS_TRANSACTION;
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

    // factory design pattern that returns a Currency obj given its balance and currency name
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

    public CheckingAccount getCheckingAccount(String accountID) {
        return this.checkingMap.get(accountID);
    }

    public SavingsAccount getSavingsAccount(String accountID) {
        return this.savingsMap.get(accountID);
    }

    public String[] getAccountList() {
        List<String> accountList = new ArrayList<>();
        for (CheckingAccount checking : checkingList) {
            accountList.add(checking.getAccountID());
        }
        for (SavingsAccount saving : savingsList) {
            accountList.add(saving.getAccountID());
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

    public boolean isUserAccount(String userID, String accountID) {
        if (isCheckingAccount(accountID)) {
            if (getCheckingAccount(accountID).getUserID().equals(userID)) {
                return true;
            }
        } else if (isSavingsAccount(accountID)) {
            if (getSavingsAccount(accountID).getUserID().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public int getCustomerCollateral(String userID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                return customer.getCollateral();
            }
        }
        return -1;
    }

    public Integer getAccountNumber(String userID, String accountType) {
        switch (accountType) {
            case SharedConstants.CK:
                return this.checkingCountMap.getOrDefault(userID, 0);
            case SharedConstants.SAV:
                return this.savingsCountMap.getOrDefault(userID, 0);
        }
        return -1;
    }

    public String authenticateUser(String userID, String password, String identity) {
        switch (identity) {
            case SharedConstants.MANAGER:
                if (managerList.isEmpty()) {
                    System.out.println(SharedConstants.ERR_ACCOUNT_NOT_EXIST);
                    return SharedConstants.ERR_USER_NOT_EXIST;
                }
                for (Manager manager : managerList) {
                    if (manager.getUserID().equals(userID) && manager.getPassword().equals(password)) {
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