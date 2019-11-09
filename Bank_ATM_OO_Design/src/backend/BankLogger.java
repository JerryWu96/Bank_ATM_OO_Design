package backend;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that stores information required to generate logs/reports.
 */
public class BankLogger {
    private Map<Integer, List<String>> newAccountIDMap;
    private Map<Integer, List<String>> closedAccountIDMap;
    private Map<Integer, List<Transaction>> transactions;
    private int day;
    private static BankLogger logger = null;

    BankLogger(int day) {
        this.day = day;
        this.newAccountIDMap = new TreeMap<>();
        this.closedAccountIDMap = new TreeMap<>();
        this.transactions = new TreeMap<>();
    }

    /**
     * return a new instance of BankLogger
     * @return
     */
    public static BankLogger getInstance() {
        if (logger == null) {
            logger = new BankLogger(BankPortal.getInstance().getDay());
        }
        return logger;
    }

    public void nextDay() {
        this.day++;
    }

    public void addAccount(String newAccountID) {
            this.newAccountIDMap.putIfAbsent(this.day, new ArrayList<String>());
            this.newAccountIDMap.get(this.day).add(newAccountID);
    }

    public void closeAccount(String closedAccountID) {
        this.closedAccountIDMap.putIfAbsent(this.day, new ArrayList<String>());
        this.closedAccountIDMap.get(this.day).add(closedAccountID);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.putIfAbsent(this.day, new ArrayList<Transaction>());
        this.transactions.get(this.day).add(transaction);
    }

//    public void addDeposit(Deposit deposit) {
//        this.deposits.putIfAbsent(this.day, new ArrayList<Deposit>());
//        this.deposits.get(this.day).add(deposit);
//    }
//
//    public void addNewLoan(Loan loan) {
//        this.newLoans.putIfAbsent(this.day, new ArrayList<Loan>());
//        this.newLoans.get(this.day).add(loan);
//    }
//
//    public void payoffLoan(Loan loan) {
//        this.closedLoans.putIfAbsent(this.day, new ArrayList<Loan>());
//        this.closedLoans.get(this.day).add(loan);
//    }
//
//    public void addWithdraw(Withdraw withdraw) {
//        this.withdraws.putIfAbsent(this.day, new ArrayList<Withdraw>());
//        this.withdraws.get(this.day).add(withdraw);
//    }
//
//    public void addTransfer(Transfer transfer) {
//        this.transfers.putIfAbsent(this.day, new ArrayList<>());
//        this.transfers.get(this.day).add(transfer);
//    }

//    public Report generateReport(int requestDay) {
//        String reportContent = "";
//        for (Map.Entry<Integer, List<CheckingAccount>> entry : newCheckings.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (CheckingAccount checking : entry.getValue()) {
//                    reportContent += "Customer " + checking.getUserID() + " opened a new Checking account " +
//                            checking.getAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//        for (Map.Entry<Integer, List<CheckingAccount>> entry : closedCheckings.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (CheckingAccount checking : entry.getValue()) {
//                    reportContent += "Customer " + checking.getUserID() + " closed a Checking account " +
//                            checking.getAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//        for (Map.Entry<Integer, List<SavingsAccount>> entry : newSavings.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (SavingsAccount savings : entry.getValue()) {
//                    reportContent += "Customer " + savings.getUserID() + " opened a new Savings account " +
//                            savings.getAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//        for (Map.Entry<Integer, List<SavingsAccount>> entry : closedSavings.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (SavingsAccount savings : entry.getValue()) {
//                    reportContent += "Customer " + savings.getUserID() + " closed a Savings account " +
//                            savings.getAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//        for (Map.Entry<Integer, List<Deposit>> entry : deposits.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (Deposit deposit : entry.getValue()) {
//                    reportContent += "Customer " + deposit.getAccountID() + " deposited " + deposit.getSelectedCurrency() +
//                            deposit.getDepositAmount() + " from account " + deposit.getAccountID() + ".\n";
//                }
//            }
//        }
//        for (Map.Entry<Integer, List<Withdraw>> entry : withdraws.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (Withdraw withdraw : entry.getValue()) {
//                    reportContent += "Customer " + withdraw.getAccountID() + " withdrew " + withdraw.getSelectedCurrency() +
//                            withdraw.getWithdrawAmount() + " from account " + withdraw.getAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//        for (Map.Entry<Integer, List<Transfer>> entry : transfers.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (Transfer transfer : entry.getValue()) {
//                    reportContent += "Customer " + transfer.getAccountID() + " transferred " + transfer.getSelectedCurrency() +
//                            transfer.getTransferAmount() + " from account " + transfer.getSourceAccountID() +
//                            " to account " + transfer.getTargetAccountID() + ".\n";
//                    reportContent += "Operation fee earned: USD" + BankPortal.getInstance().getBank().getOperationFee() + ".\n";
//                }
//            }
//        }
//
//
//        for (Map.Entry<Integer, List<Loan>> entry : newLoans.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (Loan loan : entry.getValue()) {
//                    reportContent += "Customer " + loan.getCustomerID() + " made a  " + loan.getSelectedCurrency() +
//                            loan.getAmount() + " loan\nInterest earned: " + loan.getSelectedCurrency() +
//                            loan.computeInterest() + ".\n";
//                }
//            }
//        }
//
//
//        for (Map.Entry<Integer, List<Loan>> entry : closedLoans.entrySet()) {
//            int day = entry.getKey();
//            if (day == requestDay) {
//                for (Loan loan : entry.getValue()) {
//                    reportContent += "Customer " + loan.getCustomerID() + " paid off a  " + loan.getSelectedCurrency() +
//                            loan.getAmount() + " loan.\n";
//                }
//            }
//        }
//
//        return new Report(day, reportContent);
//    }
}
