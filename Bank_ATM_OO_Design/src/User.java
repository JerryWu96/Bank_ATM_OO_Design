public class User {
    private String name;
    private String userID;
    private String password;

    User(String name, String userID, String password) {
        this.name = name;
        this.userID = userID;
        this.password = password;
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


}
