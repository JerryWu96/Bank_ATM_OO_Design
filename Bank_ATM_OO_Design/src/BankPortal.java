
public class BankPortal {
    private int sessionID;
    private int userID;
    private String permission;
    private int day;
    PortalLoginPanel loginPanel;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0;
        loginPanel = new PortalLoginPanel();
    }

    public void run() {

    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
