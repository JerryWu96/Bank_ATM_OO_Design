import java.util.List;

public class Report {
    int day;
    List<Transaction> content;

    Report(int day, List<Transaction> transactions) {
        this.day = day;
        content.addAll(transactions);
    }
}
