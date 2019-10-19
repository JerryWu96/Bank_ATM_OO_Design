import java.util.List;

public class Bank {
    private String bankName;
    private String bankID;
    private List<SavingsAccount> savings;
    private List<CheckingAccount> checkings;
    private List<Manager> managerList;
    private List<Customer> customerList;

    public String getBankName() {
        return this.bankName;
    }

    public String getBankID() {
        return this.bankID;
    }
}
