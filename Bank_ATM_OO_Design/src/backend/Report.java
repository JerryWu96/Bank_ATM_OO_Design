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

    public String display() {
        String displayStr = "";
        displayStr += "Report for day " + this.day + "\n";
        displayStr += content;
        return displayStr;
    }
}
