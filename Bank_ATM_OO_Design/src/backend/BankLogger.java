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
    private int lastReportDay;
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
     * @param requestDay: specific day that the manager is interested in
     * @return Object Report
     */
     public Report generateReportByDay(int requestDay) {
    	 
    	 List<Transaction> transHistory = transactions.getOrDefault(requestDay, null);
    	 
    	 if( transHistory == null ) {
    		 return new Report(requestDay, SharedConstants.ERR_INVALID_DAY);
    	 }
    	 this.lastReportDay = requestDay;
    	 return new Report(requestDay, transHistory.toString());
     }
     
     /**
      * Method: generateReport
      * @author Ziqi Tan
      * */
     public String generateReport() {
    	 String updateReport = "";
    	 for( Integer key: transactions.keySet() ) {
    		 if( key > this.lastReportDay ) {
    			 updateReport += transactions.get(key);
    		 }
    	 }
    	 return updateReport;
     }
}
