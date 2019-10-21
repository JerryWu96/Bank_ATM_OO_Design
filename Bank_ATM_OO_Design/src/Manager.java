public class Manager extends User {
    private String bankID;

    Manager(String name, String userID, String password, String bankID) {
        super(name, userID, password, "Manager");
        this.bankID = bankID;
    }
}