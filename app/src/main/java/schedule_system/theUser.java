package schedule_system;

// 用户类
public class theUser {
    boolean isAdmin; // 是否是管理员
    String id; // 用户名
    String password; // 密码

    theUser(boolean isAdmin, String id, String pswd) {
        this.isAdmin = isAdmin;
        this.id = id;
        this.password = pswd;
    }
}
