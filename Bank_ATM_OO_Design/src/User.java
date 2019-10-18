public class User {
    private String name;
    private int userID;
    private int password;
    private String permission;

    User(String name, int userID, int password, String permission) {
        this.name = name;
        this.userID = userID;
        this.password = password;
        this.permission = permission;
    }

    public String getName() {
        return this.name;
    }

    public int getUserID() {
        return this.userID;
    }

    public int getPassword() {
        return this.password;
    }

    public String getPermission() {
        return this.permission;
    }

}
