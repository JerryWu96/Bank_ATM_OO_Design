import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that stores information required to generate logs/reports.
 */
public class BankLogger {
    private Map<Integer, List<CheckingAccount>> newCheckings;
    private Map<Integer, List<CheckingAccount>> closedCheckings;
    private Map<Integer, List<SavingsAccount>> newSavings;
    private Map<Integer, List<SavingsAccount>> closedSavings;
    private Map<Integer, List<Deposit>> deposits;
    private Map<Integer, List<Withdraw>> withdraws;
    private Map<Integer, List<Transfer>> transfers;
    private Map<Integer, List<Loan>> newLoans;
    private Map<Integer, List<Loan>> closedLoans;
    private int day;
    private static BankLogger logger = null;

    BankLogger(int day) {
        this.day = day;
        this.newCheckings = new TreeMap<>();
        this.closedCheckings = new TreeMap<>();
        this.closedSavings = new TreeMap<>();
        this.newSavings = new TreeMap<>();
        this.deposits = new TreeMap<>();
        this.withdraws = new TreeMap<>();
        this.transfers = new TreeMap<>();
        this.newLoans = new TreeMap<>();
        this.closedLoans = new TreeMap<>();
    }

    public static BankLogger getInstance() {
        if (logger == null) {
            logger = new BankLogger(BankPortal.getInstance().getDay());
        }
        return logger;
    }

    public void nextDay() {
        this.day++;
        newCheckings.clear();
        newSavings.clear();
    }

    public void addChecking(CheckingAccount checkingAccount) {
        this.newCheckings.putIfAbsent(this.day, new ArrayList<CheckingAccount>());
        this.newCheckings.get(this.day).add(checkingAccount);
    }

    public void closeChecking(CheckingAccount checkingAccount) {
        this.closedCheckings.putIfAbsent(this.day, new ArrayList<CheckingAccount>());
        this.closedCheckings.get(this.day).add(checkingAccount);
    }

    public void addSavings(SavingsAccount savingsAccount) {
        this.newSavings.putIfAbsent(this.day, new ArrayList<SavingsAccount>());
        this.newSavings.get(this.day).add(savingsAccount);
    }

    public void closeSavings(SavingsAccount savingsAccount) {
        this.closedSavings.putIfAbsent(this.day, new ArrayList<SavingsAccount>());
        this.closedSavings.get(this.day).add(savingsAccount);
    }

    public void addDeposit(Deposit deposit) {
        this.deposits.putIfAbsent(this.day, new ArrayList<Deposit>());
        this.deposits.get(this.day).add(deposit);
    }

    public void addNewLoan(Loan loan) {
        this.newLoans.putIfAbsent(this.day, new ArrayList<Loan>());
        this.newLoans.get(this.day).add(loan);
    }

    public void payoffLoan(Loan loan) {
        this.closedLoans.putIfAbsent(this.day, new ArrayList<Loan>());
        this.closedLoans.get(this.day).add(loan);
    }

    public void addWithdraw(Withdraw withdraw) {
        this.withdraws.putIfAbsent(this.day, new ArrayList<Withdraw>());
        this.withdraws.get(this.day).add(withdraw);
    }

    public void addTransfer(Transfer transfer) {
        this.transfers.putIfAbsent(this.day, new ArrayList<Transfer>());
        this.transfers.get(this.day).add(transfer);
    }

    public Report generateReport(int requestDay) {
        String transactions = "";
        for (Map.Entry<Integer, List<CheckingAccount>> entry : newCheckings.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (CheckingAccount checking : entry.getValue()) {
                    transactions += "Customer " + checking.getUserID() + " opened a new Checking account " +
                            checking.getAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }

        for (Map.Entry<Integer, List<CheckingAccount>> entry : closedCheckings.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (CheckingAccount checking : entry.getValue()) {
                    transactions += "Customer " + checking.getUserID() + " closed a Checking account " +
                            checking.getAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }

        for (Map.Entry<Integer, List<SavingsAccount>> entry : newSavings.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (SavingsAccount savings : entry.getValue()) {
                    transactions += "Customer " + savings.getUserID() + " opened a new Savings account " +
                            savings.getAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }

        for (Map.Entry<Integer, List<SavingsAccount>> entry : closedSavings.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (SavingsAccount savings : entry.getValue()) {
                    transactions += "Customer " + savings.getUserID() + " closed a Savings account " +
                            savings.getAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }

        for (Map.Entry<Integer, List<Deposit>> entry : deposits.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (Deposit deposit : entry.getValue()) {
                    transactions += "Customer " + deposit.getAccountID() + " deposited " + deposit.getSelectedCurrency() +
                            deposit.getDepositAmount() + " from account " + deposit.getAccountID() + ".\n";
                }
            }
        }
        for (Map.Entry<Integer, List<Withdraw>> entry : withdraws.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (Withdraw withdraw : entry.getValue()) {
                    transactions += "Customer " + withdraw.getAccountID() + " withdrew " + withdraw.getSelectedCurrency() +
                            withdraw.getWithdrawAmount() + " from account " + withdraw.getAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }

        for (Map.Entry<Integer, List<Transfer>> entry : transfers.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (Transfer transfer : entry.getValue()) {
                    transactions += "Customer " + transfer.getAccountID() + " transferred " + transfer.getSelectedCurrency() +
                            transfer.getTransferAmount() + " from account " + transfer.getSourceAccountID() +
                            " to account " + transfer.getTargetAccountID() + ".\n";
                    transactions += "Operation fee earned: USD" + Bank.getInstance().getOperationFee() + ".\n";
                }
            }
        }


        for (Map.Entry<Integer, List<Loan>> entry : newLoans.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (Loan loan : entry.getValue()) {
                    transactions += "Customer " + loan.getCustomerID() + " made a  " + loan.getSelectedCurrency() +
                            loan.getAmount() + " loan\nInterest earned: " + loan.getSelectedCurrency() +
                            loan.computeInterest() + ".\n";
                }
            }
        }


        for (Map.Entry<Integer, List<Loan>> entry : closedLoans.entrySet()) {
            int day = entry.getKey();
            if (day == requestDay) {
                for (Loan loan : entry.getValue()) {
                    transactions += "Customer " + loan.getCustomerID() + " paid off a  " + loan.getSelectedCurrency() +
                            loan.getAmount() + " loan.\n";
                }
            }
        }

        return new Report(day, transactions);
    }
}
