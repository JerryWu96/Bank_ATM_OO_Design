import java.util.List;

public class BankPortal {
    private int sessionID;
    private int userID;
    private String permission;
    private int day;
    PortalLoginPanel loginPanel;
    private List<Integer> customerIDList;
    private List<Integer> mnagerIDList;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0;
    }

    public void run() {
        loginPanel = new PortalLoginPanel();
    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
