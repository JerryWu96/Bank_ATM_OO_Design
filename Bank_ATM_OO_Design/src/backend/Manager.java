package backend;
/**
 * A manager of the system. If more banks are added, we can store the bankID in the manager private field.
 */
public class Manager extends User {
    private String bankID;

    public Manager(String name, String userID, String password, String bankID) {
        super(name, userID, password, SharedConstants.MANAGER);
        this.bankID = bankID;
    }
}
