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
    private List<SavingsAccount> savings;
    private List<CheckingAccount> checkings;
    private List<Manager> managerList;
    private List<Customer> customerList;
    private String[] currencyList;
    private static final double OPERATION_FEE = 2;
    private static final double LOAN_INTEREST = 0.1;
    private Map<String, CheckingAccount> checkingMap;
    private Map<String, SavingsAccount> savingsMap;
    private Map<String, Integer> checkingCountMap;
    private Map<String, Integer> savingsCountMap;

    private static Bank bank = null;

    private Bank() {
        this.bankName = "Bank of Fools";
        this.bankID = "BofF";
        this.savings = new ArrayList<>();
        this.checkings = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.checkingMap = new HashMap<>();
        this.savingsMap = new HashMap<>();
        this.checkingCountMap = new HashMap<>();
        this.savingsCountMap = new HashMap<>();
        this.currencyList = new String[]{"USD", "CNY", "YEN"};
    }

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    public String getBankName() {
        return this.bankName;
    }

    public String getBankID() {
        return this.bankID;
    }

    public List<SavingsAccount> getSavings() {
        return this.savings;
    }

    public List<CheckingAccount> getCheckings() {
        return this.checkings;
    }

    public String getCustomerName(String userID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                return customer.getName();
            }
        }
        return "NULL";
    }

    public String getManagerName(String userID) {
        for (Manager manager : managerList) {
            if (manager.getUserID().equals(userID)) {
                return manager.getName();
            }
        }
        return "NULL";
    }

    public void addUser(String userID, String password, String name, String identity) {
        switch (identity) {
            case "Customer":
                customerList.add(new Customer(name, userID, password));
                break;
            case "Manager":
                managerList.add(new Manager(name, userID, password, this.bankID));
                break;
        }
    }

    public void openCheckingAccount(CheckingAccount newAccount) {
        String userID = newAccount.getUserID();
        this.checkings.add(newAccount);
        this.checkingMap.put(newAccount.getAccountID(), newAccount);
        this.checkingCountMap.put(userID, this.checkingCountMap.getOrDefault(userID, 0) + 1);
    }

    public String closeCheckingAccount(String userID, String accountID) {
        if (checkingCountMap.isEmpty() || checkingCountMap.get(userID) == 0 || !checkingMap.containsKey(accountID)) {
            return "Not Exist";
        }
        checkingCountMap.replace(userID, checkingCountMap.get(userID) - 1);
        checkingMap.remove(accountID);
        for (CheckingAccount checking : checkings) {
            if (checking.getAccountID().equals(accountID)) {
                checkings.remove(checking);
                BankLogger.getInstance().closeChecking(checking);
                break;
            }
        }
        return "Successful";
    }

    public void openSavingsAccount(SavingsAccount newAccount) {
        String userID = newAccount.getUserID();
        this.savings.add(newAccount);
        this.savingsMap.put(newAccount.getAccountID(), newAccount);
        this.savingsCountMap.put(userID, this.savingsCountMap.getOrDefault(userID, 0) + 1);
    }

    public String closeSavingsAccount(String userID, String accountID) {
        if (savingsCountMap.isEmpty() || savingsCountMap.get(userID) == 0 || !savingsMap.containsKey(accountID)) {
            return "Not Exist";
        }
        savingsCountMap.replace(userID, savingsCountMap.get(userID) - 1);
        savingsMap.remove(accountID);
        for (SavingsAccount saving : savings) {
            if (saving.getAccountID().equals(accountID)) {
                savings.remove(saving);
                BankLogger.getInstance().closeSavings(saving);
                break;
            }
        }
        return "Successful";
    }

    public void deposit(String accountID, double amount, String selectedCurrency) {
        setAccountBalance(accountID, amount, selectedCurrency);
    }

    public String withdraw(String accountID, double amount, String selectedCurrency) {
        double balance = getAccountBalance(accountID, selectedCurrency);
        if (balance - OPERATION_FEE - amount < 0) {
            return "Insufficient balance";
        }
        setAccountBalance(accountID, -amount - OPERATION_FEE, selectedCurrency);
        return "Success";
    }

    public String transfer(String sourceAccountID, String targetAccountID, double amount, String selectedCurrency) {
        double sourceBalance = getAccountBalance(sourceAccountID, selectedCurrency);
        if (sourceBalance - amount - OPERATION_FEE < 0) {
            return "Insufficient balance";
        }
        setAccountBalance(sourceAccountID, -amount - OPERATION_FEE, selectedCurrency);
        setAccountBalance(targetAccountID, amount, selectedCurrency);
        return "Success";
    }

    public String takeLoan(String userID, double amount, String selectedCurrency) {
        if (getCustomerCollateral(userID) == 0) {
            return "Not Eligible";
        } else {
            for (Customer customer : customerList) {
                if (customer.getUserID().equals(userID)) {
                    customer.addLoan(amount, LOAN_INTEREST, selectedCurrency);
                    return "Success";
                }
            }
        }
        return "Not Exist";
    }

    public void payoffLoan(String userID, String loanID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals(userID)) {
                customer.payoffLoan(loanID);
            }
        }
    }

    public double getOperationFee() {
        return OPERATION_FEE;
    }

    private double getAccountBalance(String accountID, String selectedCurrency) {
        double balance = 0;
        if (isCheckingAccount(accountID)) {
            balance = checkingMap.get(accountID).getBalance(selectedCurrency);
        } else if (isSavingsAccount(accountID)) {
            balance = savingsMap.get(accountID).getBalance(selectedCurrency);
        }
        return balance;
    }

    private void setAccountBalance(String accountID, double balanceDiff, String selectedCurrency) {
        if (isCheckingAccount(accountID)) {
            checkingMap.get(accountID).setBalance(balanceDiff, selectedCurrency);
        } else if (isSavingsAccount(accountID)) {
            savingsMap.get(accountID).setBalance(balanceDiff, selectedCurrency);
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
        for (CheckingAccount checking : checkings) {
            accountList.add(checking.getAccountID());
        }
        for (SavingsAccount saving : savings) {
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

    public Integer getUserCheckingCount(String userID) {
        return this.checkingCountMap.getOrDefault(userID, 0);
    }

    public Integer getUserSavingsCount(String userID) {
        return this.savingsCountMap.getOrDefault(userID, 0);
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

    public String authenticateUser(String userID, String password, String identity) {
        switch (identity) {
            case "Manager":
                if (managerList.isEmpty()) {
                    System.out.println("Not Exist");
                    return "NotExist";
                }
                for (Manager manager : managerList) {
                    if (manager.getUserID().equals(userID) && manager.getPassword().equals(password)) {
                        return "Pass";
                    } else if (manager.getUserID().equals(userID) && !manager.getPassword().equals(password)) {
                        return "WrongPass";
                    }
                }
                return "NotExist";
            case "Customer":
                if (customerList.isEmpty()) {
                    return "NotExist";
                }
                for (Customer customer : customerList) {
                    if (customer.getUserID().equals(userID) && customer.getPassword().equals(password)) {
                        return "Pass";
                    } else if (customer.getUserID().equals(userID) && !customer.getPassword().equals(password)) {
                        return "WrongPass";
                    }
                }
                return "NotExist";
        }
        return "Error";
    }

}
