package backend;
/**
 * Class that encapsulates a loan.
 */

import java.text.DecimalFormat;

public class LoanAccount extends Account{
    private String loanID;
    private String customerID;
    private double interest;
    private double amount;
    private String selectedCurrency;
    private boolean isPaidOff;

    LoanAccount(String customerID, double amount, double interest, String selectedCurrency, int postfix) {
        super(customerID + SharedConstants.DELIMITER + amount + SharedConstants.DELIMITER + postfix, SharedConstants.BANK_ID, customerID, SharedConstants.LOAN);
        this.customerID = customerID;
        this.amount = amount;
        this.interest = interest;
        this.isPaidOff = false;
        this.selectedCurrency = selectedCurrency;
        this.loanID = customerID + SharedConstants.DELIMITER + amount + SharedConstants.DELIMITER + postfix;
    }

    private void setPaid() {
        this.isPaidOff = true;
    }

    public double getInterest() {
        return this.interest;
    }

    public String getLoanID() {
        return this.loanID;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public String getSelectedCurrency() {
        return this.selectedCurrency;
    }

    public double getAmount() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(this.amount));
    }

    public String getCurrencyName() {
        return this.selectedCurrency;
    }

    public boolean isPaidOff() {
        return this.isPaidOff;
    }

    
    /**
     * pay off this amount of loan
     * @param paid_amount
     * @return 
     */
    public double payByAmount(double paid_amount) {
        if (paid_amount < this.amount) {
            double diff_amount = this.amount - paid_amount;
            this.amount -= diff_amount;
            return diff_amount;
        } else {
            this.setPaid();
            return paid_amount - this.amount;
        }
    }

    /**
     * add the corresponding interest to this loan according to interest rate
     */
    public void computeInterest() {
        this.amount += amount * interest;
    }

    @Override
    public String toString() {
        String loanInfo = "";
        loanInfo += "Loan ID: " + getLoanID() + "Customer ID: " + getCustomerID() + "Amount: " + getCurrencyName() +
                getAmount();
        return loanInfo;
    }

}
