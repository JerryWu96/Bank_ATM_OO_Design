import java.util.List;

public class BankPortal {
    private int sessionID;
    private int userID;
    private String permission;
    private int day;
    private PortalLoginPanel loginPanel;
//    private Bank bank;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0;
//        this.bank = Bank.getInstance();
    }

    public void run() {
        loginPanel = new PortalLoginPanel();

    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
