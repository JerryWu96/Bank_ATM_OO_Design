import java.util.Currency;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Bank {
    private String bankName;
    private String bankID;
    private List<SavingsAccount> savings;
    private List<CheckingAccount> checkings;
    private List<Manager> managerList;
    private List<Customer> customerList;
    private String[] currencyList;
    private Map<String, CheckingAccount> checkingMap;
    private Map<String, SavingsAccount> savingsMap;
    private Map<String, Integer> checkingCountMap;
    private Map<String, Integer> savingsCountMap;
    private Map<Integer, Double> dailyProfitsMap;

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
        this.currencyList = new String[] {"USD", "CNY", "YEN"};
    }

    public static Bank getInstance() {
        if (bank == null) {
            bank = new Bank();
        }
        return bank;
    }

    public boolean doesCustomerExist(String userID) {
        for (Customer customer : customerList) {
            if (customer.getUserID().equals((userID))) {
                return true;
            }
        }
        return false;
    }

    public boolean doesManagerExist(String userID) {
        for (Manager manager : managerList) {
            if (manager.getUserID().equals((userID))) {
                return true;
            }
        }
        return false;
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

    public List<Manager> getManagerList() {
        return this.managerList;
    }

    public List<Customer> getCustomerList() {
        return this.customerList;
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
        for (CheckingAccount checking: checkings) {
            if (checking.getAccountID().equals(accountID)) {
                checkings.remove(checking);
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
        if (savingsCountMap.isEmpty() ||savingsCountMap.get(userID) == 0 || !savingsMap.containsKey(accountID)) {
            return "Not Exist";
        }
        savingsCountMap.replace(userID, savingsCountMap.get(userID) - 1);
        savingsMap.remove(accountID);
        for (SavingsAccount saving: savings) {
            if (saving.getAccountID().equals(accountID)) {
                savings.remove(saving);
                break;
            }
        }
        return "Successful";
    }

    public CheckingAccount getCheckingAccount(String accountID) {
        return this.checkingMap.get(accountID);
    }

    public SavingsAccount getSavingsAccount(String accountID) {
        return this.savingsMap.get(accountID);
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

    public String authenticateUser(String userID, String password, String identity) {
        switch (identity) {
            case "Manager":
                if (managerList.isEmpty()) {
                    return "NotExist";
                }
                for (Manager manager : managerList) {
                    if (manager.getUserID().equals(userID) && manager.getPassword().equals(password)) {
                        return "Pass";
                    } else if (manager.getUserID().equals(userID) && !manager.getPassword().equals(password)) {
                        return "WrongPass";
                    }
                    return "NotExist";
                }
                break;
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
                    return "NotExist";
                }
                break;
        }
        return "Error";
    }


}
