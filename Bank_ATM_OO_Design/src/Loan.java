public class Loan {
    private String loanID;
    private int customerID;
    private double interest;
    private double amount;
    private boolean isPaidOff;

    Loan(int customerID, double amount, double interest) {
        this.customerID = customerID;
        this.amount = amount;
        this.interest = interest;
        this.isPaidOff = false;
        this.loanID = customerID + "_" + amount;
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

    public int getCustomerID() {
        return this.customerID;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean isPaidOff() {
        return this.isPaidOff;
    }


    public double payOff(double paid_amount) {
        if (paid_amount < this.amount) {
            double diff_amount = this.amount - paid_amount;
            this.amount -= diff_amount;
            return diff_amount;
        } else {
            this.setPaid();
            return paid_amount - this.amount;
        }
    }

}
