package backend;
/**
 * Represents a daily report of the system. It has content such as: Customer transactions, Account opening/closing and more.
 */
public class Report {
    int day;
    String content;


    Report(int day, String transactions) {
        this.day = day;
        this.content = transactions;
    }
    
    /**
     * Modified by Ziqi Tan on 11, Nov.
     * Change the method name into "getContent" from "display".
     * */
    public String getContent() {
        String displayStr = "";
        displayStr += "Report for day " + this.day + "\n";
        displayStr += content;
        return displayStr;
    }
}
