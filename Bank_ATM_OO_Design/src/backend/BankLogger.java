package backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that stores information required to generate logs/reports.
 */
public class BankLogger {
    private Map<Integer, List<Transaction>> transactions;
    private int day;
    private static BankLogger logger = null;

    BankLogger(int day) {
        this.day = day;
        this.transactions = new TreeMap<>();
    }

    /**
     * return a new instance of BankLogger
     *
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

    /**
     * add a transaction to the transactions map which maps day to a list of transactions took place within that day.
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        this.transactions.putIfAbsent(this.day, new ArrayList<Transaction>());
        this.transactions.get(this.day).add(transaction);
    }

    /**
     * Generate a report object with transactions and operations by a specific
     * @author Xiankang Wu
     * @author Ziqi Tan modified on 11, Nov
     * @param a specific day
     * @return Object Report
     */
     public Report generateReportByDay(int requestDay) {
    	 
    	 List<Transaction> transHistory = transactions.getOrDefault(requestDay, null);
    	 
    	 if( transHistory == null ) {
    		 return new Report(requestDay, SharedConstants.ERR_INVALID_DAY);
    	 }
    	 
    	 return new Report(requestDay, transHistory.toString());
     }


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
