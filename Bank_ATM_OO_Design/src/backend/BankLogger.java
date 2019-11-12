package backend;

import java.util.List;
import db.DatabasePortal;

import javax.xml.crypto.Data;


/**
 * Class that stores information required to generate logs/reports.
 */
public class BankLogger {
    private int day;
    private static BankLogger logger = null;
    DatabasePortal myDB;

    BankLogger(int day) {
        this.day = day;
        myDB = DatabasePortal.getInstance();
    }

    /**
     * return an instance of BankLogger
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
        myDB.addTransaction(transaction);
    }

    /**
     * Generate a report object with transactions and operations by a specific day
     * @param requestDay: specific day that the manager is interested in
     * @return Object Report
     */
     public Report generateReportByDay(int requestDay) {
    	 
         List<Transaction> transHistory = myDB.getTransactionList(this.day, true);
    	 if( transHistory.isEmpty() ) {
    		 return new Report(requestDay, SharedConstants.ERR_INVALID_DAY);
    	 }
    	 return new Report(requestDay, transHistory.toString());
     }
     
     /**
      * Method: generateReport
      * */
     public String generateReport() {
         String updateReport = "";
         List<Transaction> transHistory = myDB.getTransactionList(this.day, false);
         for (Transaction transaction : transHistory) {
             updateReport += transaction.toString();
         }
    	 return updateReport;
     }
}
