import java.util.List;
import java.util.ArrayList;

public class Customer extends User {
    private List<Loan> loanList;

    Customer(String name, int userID, int password, String permission) {
        super(name, userID, password, permission);
        this.loanList = new ArrayList<>();
    }

    public void makeTransaction() {

    }

    public void takeLoan() {

    }
}
