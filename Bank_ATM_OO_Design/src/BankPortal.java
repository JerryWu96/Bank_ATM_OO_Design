public class BankPortal {
    private int sessionID;
    private int userID;
    private int day;
    private welcomePanel welcomePanel;
//    private Bank bank;

    BankPortal() {
        this.day = 0; // initial day
        this.sessionID = 0;
//        this.bank = Bank.getInstance();
    }

    public void run() {
        welcomePanel = new welcomePanel();
    }

    public static void main(String[] args) {
        BankPortal portal = new BankPortal();
        portal.run();
    }
}
