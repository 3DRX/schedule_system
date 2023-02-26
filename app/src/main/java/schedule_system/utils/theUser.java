package schedule_system.utils;

// 用户类
public class theUser {
    private boolean isAdmin; // 是否是管理员
    private String id; // 用户名
    private String password; // 密码

    theUser(boolean isAdmin, String id, String pswd) {
        this.isAdmin = isAdmin;
        this.id = id;
        this.password = pswd;
    }

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
