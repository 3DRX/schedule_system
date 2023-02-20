package schedule_system;

public class theUser {
    boolean isAdmin;
    String id;
    String pswd;

    theUser(boolean isAdmin, String id, String pswd) {
        this.isAdmin = isAdmin;
        this.id = id;
        this.pswd = pswd;
    }
}
