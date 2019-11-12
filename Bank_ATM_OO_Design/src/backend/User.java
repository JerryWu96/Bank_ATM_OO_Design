package backend;
/**
 * Base class represents any user in the system. If we decide to add a banker, it can be inherited from this calss.
 */
public abstract class User {
    private String name;
    private String userID;
    private String password;
    private String permission;

    User(String name, String userID, String password, String permission) {
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.permission = permission;
    }

    public String getName() {
        return this.name;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPermission() {
        return this.permission;
    }

}
