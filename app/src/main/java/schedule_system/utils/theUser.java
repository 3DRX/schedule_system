package schedule_system.utils;

/**
 * 用户类
 *
 * {@link #id} 用户名
 * {@link #password} 密码
 */
public class theUser {
    private String id; // 用户名
    private String password; // 密码

    theUser(String id, String pswd) {
        this.id = id;
        this.password = pswd;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
