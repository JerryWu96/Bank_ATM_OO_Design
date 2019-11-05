import java.util.List;
import java.util.ArrayList;

/**
 * Customer class that represents one of the system actors. A customer can open/close an account, request
 * for/pay off a loan, or make transactions between multiple accounts.
 */
public class Customer extends User {
    private List<LoanAccount> loanList;
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
        LoanAccount loan = new LoanAccount(getUserID(), amount, interest, selectedCurrency, getLoanCount());
        this.loanList.add(loan);
        useCollateral();
    }

    public void payoffLoan(String loanID) {
        for (LoanAccount loan : loanList) {
            if (loan.getLoanID().equals(loanID)) {
                loanList.remove(loan);
                addCollateral();
            }
        }
    }

    public int getLoanCount() {
        return this.loanList.size();
    }

    public void computeLoanInterest() {
        for (LoanAccount loan : loanList) {
            loan.computeInterest();
        }
    }

    public List<String> getLoans() {
        List<String> loanIDList = new ArrayList<>();
        for (LoanAccount loan : loanList) {
            loanIDList.add(loan.getLoanID());
        }
        return loanIDList;
    }
}
