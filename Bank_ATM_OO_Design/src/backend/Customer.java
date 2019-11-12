package backend;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Customer class that represents one of the system actors. A customer can open/close an account, request
 * for/pay off a loan, or make transactions between multiple accounts.
 */
public class Customer extends User {
    private List<LoanAccount> loanList;
    private int collateral;

    public Customer(String name, String userID, String password) {
        super(name, userID, password, SharedConstants.CUSTOMER);
        this.loanList = new ArrayList<>();
        this.collateral = 3;
    }

    public int getCollateral() {
        return this.collateral;
    }

    public void addCollateral() {
        this.collateral++;
    }

    public void useCollateral() {
        this.collateral--;
    }

    /**
     * add a loan entry to this customer, and remove one collateral
     *
     * @param amount
     * @param interest         (interest rate)
     * @param selectedCurrency
     */
    public String addLoan(double amount, double interest, String selectedCurrency) {
        LoanAccount loan = new LoanAccount(getUserID(), amount, interest, selectedCurrency, getLoanCount());
        this.loanList.add(loan);
        useCollateral();
        return loan.getLoanID();
    }

    /**
     * pay off the loan with the corresponding ID
     *
     * @param loanID
     */
    public void payoffLoan(String loanID) {
        for (Iterator<LoanAccount> iterator = loanList.iterator(); iterator.hasNext();) {
            LoanAccount loan = iterator.next();
            if (loan.getLoanID().equals(loanID)) {
                iterator.remove();
                addCollateral();
            }
        }
    }

    public int getLoanCount() {
        return this.loanList.size();
    }

    /**
     * compute interests for all loan entries
     */
    public void computeLoanInterest() {
        for (LoanAccount loan : loanList) {
            loan.computeInterest();
        }
    }

    /**
     * @return a list with all the loan entries this customer has
     */
    public List<String> getLoans() {
        List<String> loanIDList = new ArrayList<>();
        for (LoanAccount loan : loanList) {
            loanIDList.add(loan.getLoanID());
        }
        return loanIDList;
    }
}
