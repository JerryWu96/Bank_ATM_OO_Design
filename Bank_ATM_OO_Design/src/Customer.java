import java.util.List;
import java.util.ArrayList;

public class Customer extends User {
    private List<Loan> loanList;
    private int collateral;

    Customer(String name, String userID, String password) {
        super(name, userID, password, "Customer");
        this.loanList = new ArrayList<>();
        this.collateral = 5;
    }

    public void makeTransaction() {

    }

    public void takeLoan() {

    }
}
