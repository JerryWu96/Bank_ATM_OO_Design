import java.util.List;
import java.util.ArrayList;

/**
 * Customer class that represents one of the system actors. A customer can open/close an account, request
 * for/pay off a loan, or make transactions between multiple accounts.
 */
public class Customer extends User {
    private List<Loan> loanList;
    private int collateral;

    Customer(String name, String userID, String password) {
        super(name, userID, password, "Customer");
        this.loanList = new ArrayList<>();
        this.collateral = 3;
    }

    public int getCollateral() {
        return this.collateral;
    }

    public void addCollateral() {
        this.collateral ++;
    }

    public void useCollateral() {
        this.collateral--;
    }

    public void addLoan(double amount, double interest, String selectedCurrency) {
        Loan loan = new Loan(getUserID(), amount, interest, selectedCurrency, getLoanCount());
        this.loanList.add(loan);
        useCollateral();
        BankLogger.getInstance().addNewLoan(loan);
    }

    public void payoffLoan(String loanID) {
        for (Loan loan : loanList) {
            if (loan.getLoanID().equals(loanID)) {
                loanList.remove(loan);
                addCollateral();
                BankLogger.getInstance().payoffLoan(loan);
            }
        }
    }

    public int getLoanCount() {
        return this.loanList.size();
    }

    public List<String> getLoans() {
        List<String> loanIDList = new ArrayList<>();
        for (Loan loan : loanList) {
            loanIDList.add(loan.getLoanID());
        }
        return loanIDList;
    }
}
