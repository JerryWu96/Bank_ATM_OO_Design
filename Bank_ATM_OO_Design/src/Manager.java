public class Manager extends User{
    private String bankID;

    Manager(String name, int userID, int password, String permission, String bankID) {
        super(name, userID, password, permission);
        this.bankID = bankID;
    }

    public Report checkReport() {
        Report dailyReport = new Report();
        return dailyReport;
    }
}
