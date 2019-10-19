import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String bankName;
    private String bankID;
    private List<SavingsAccount> savings;
    private List<CheckingAccount> checkings;
    private List<Manager> managerList;
    private List<Customer> customerList;
    private static Bank bank = null;

    private Bank() {
        this.bankName = "Bank of Fools";
        this.bankID = "BofF";
        this.savings = new ArrayList<>();
        this.checkings = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.customerList = new ArrayList<>();
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

    public List<Manager> getManagerList() {
        return this.managerList;
    }

    public List<Customer> getCustomerList() {
        return this.customerList;
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
