package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.theUser;

public class UserData {
    // 用于读写json
    final private String path = "src/main/resources/users.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 从resources/users.json读取所有的用户存入数组中
    private theUser[] jsonUsers;
    private theUser[] students;

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
        return this.students;
    }

    /**
     * 从所有用户的数组中找出学生并返回一个所有学生的数组
     * 
     * @return
     */
    private theUser[] getStudents() {
        theUser[] ret = new theUser[jsonUsers.length - 1];
        int i = 0;
        for (theUser user : jsonUsers) {
            if (user.isAdmin() == false) {
                continue;
            } else {
                ret[i] = user;
                i++;
            }
        }
        return ret;
        // TODO: test this
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
