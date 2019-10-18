public class Manager extends User{
    private String bankID;

    Manager(String name, int userID, int password, String bankID) {
        super(name, userID, password, "Manager");
        this.bankID = bankID;
    }

//    public void checkReport(Report dailyReport) {
//
//    }
}
