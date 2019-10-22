import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BankLogger {
    private Map<Integer, List<BalanceInquiry>> inquiries;
    private Map<Integer, List<Deposit>>deposits;
    private Map<Integer, List<Withdraw>> withdraws;
    private Map<Integer, List<Transaction>> transactions;
    private int day;
    private static BankLogger logger = null;


    BankLogger(int day) {
        this.day = day;
        this.inquiries = new TreeMap<>();
        this.deposits = new TreeMap<>();
        this.withdraws = new TreeMap<>();
        this.transactions = new TreeMap<>();
    }

    public static BankLogger getInstance() {
        if (logger == null) {
            logger = new BankLogger(BankPortal.getInstance().getDay());
        }
        return logger;
    }

    public void addInquiry(BalanceInquiry inquiry) {
        this.inquiries.putIfAbsent(this.day, new ArrayList<BalanceInquiry>());
        this.inquiries.get(this.day).add(inquiry);
    }

    public void addDeposit(Deposit deposit) {
        this.deposits.putIfAbsent(this.day, new ArrayList<Deposit>());
        this.deposits.get(this.day).add(deposit);
    }


    public void addWithdraw(Withdraw withdraw) {
        this.withdraws.putIfAbsent(this.day, new ArrayList<Withdraw>());
        this.withdraws.get(this.day).add(withdraw);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.putIfAbsent(this.day, new ArrayList<Transaction>());
        this.transactions.get(this.day).add(transaction);
    }
}
