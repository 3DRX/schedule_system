package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.KList;
import schedule_system.utils.theUser;

public class UserData {
    // 用于读写json
    final private String path = "src/main/resources/users.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 从resources/users.json读取所有的用户存入数组中
    private theUser[] jsonUsers;
    private KList<theUser> students;

    public UserData() {
        this.jsonUsers = readUsers();
        this.students = getStudents();
    }

    /**
     * @return theUser[] allUsers
     */
    public theUser[] allUsers() {
        return this.jsonUsers;
    }

    /**
     * @return theUser[] students
     */
    public theUser[] students() {
        return this.students.toArray(new theUser[this.students.size()]);
    }

    /**
     * 从所有用户的数组中找出学生并返回一个所有学生的数组
     * 
     * @return
     */
    private KList<theUser> getStudents() {
        KList<theUser> res = new KList<>(theUser.class);
        Arrays.stream(this.jsonUsers)
                .filter(e -> !e.isAdmin())
                .forEach(e -> res.add(e));
        return res;
    }

    /**
     * 从resources/users.json中读取users，返回users[]
     * 
     * @return theUser[]
     */
    private theUser[] readUsers() {
        theUser[] read_users = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            read_users = new Gson().fromJson(reader, theUser[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return read_users;
    }

    /**
     * 将一组特定的users写入resources/users.json中
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
