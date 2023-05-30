package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.KList;
import schedule_system.utils.theUser;

/**
 * 用户控制
 * 与 {@link StudentData} 的区别是，这里只操作学生的登陆信息，即姓名和密码
 * 操作一组 {@link theUser} 对象
 */
public class UserData {
    final private String path = "src/main/resources/users.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private KList<theUser> students;

    /**
     * 从文件读取学生信息
     */
    public UserData() {
        this.students = readUsers();
    }

    /**
     * @return 所有学生
     */
    public theUser[] students() {
        return this.students.toArray(new theUser[this.students.size()]);
    }

    /**
     * 向名单中添加学生
     * 
     * @param user 学生
     * @return 是否添加成功
     */
    public boolean addStudent(theUser user) {
        if (!this.students.contains(user) && !user.getId().equals("admin")) {
            this.students.add(user);
            writeUsers(this.students.toArray());
            return true;
        }
        return false;
    }

    /**
     * 从名单中删除学生
     * 
     * @param id 姓名
     * @return 删除是否成功
     */
    public boolean removeStudent(String id) {
        KList<theUser> newStudents = new KList<>(theUser.class);
        for (theUser user : this.students) {
            if (!user.getId().equals(id)) {
                newStudents.add(user);
            }
        }
        this.students = newStudents;
        writeUsers(this.students.toArray());
        return true;
    }

    /**
     * 从文件读取所有学生的信息
     * 
     * @return 所有学生的 {@link KList} 列表
     */
    private KList<theUser> readUsers() {
        try {
            KList<theUser> res = new KList<>(theUser.class);
            theUser[] users;
            users = new Gson()
                    .fromJson(new JsonReader(new FileReader(path)),
                            theUser[].class);
            for (theUser user : users) {
                res.add(user);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将一组用户写入文件
     */
    private void writeUsers(theUser[] users) {
        File file = new File(path);
        String res = gson.toJson(users);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
