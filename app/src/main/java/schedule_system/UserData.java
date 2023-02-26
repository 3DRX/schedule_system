package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.theUser;

public class UserData {
    // 用于读写json
    final private static String path = "src/main/resources/users.json";
    final private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 从resources/users.json读取所有的用户存入数组中
    final private static theUser[] jsonUsers = readUsers();
    private static theUser[] students = getStudents();

    /**
     * @return theUser[] allUsers
     */
    public static theUser[] allUsers() {
        return jsonUsers;
    }

    /**
     * @return theUser[] students
     */
    public static theUser[] students() {
        return students;
    }

    private static theUser[] getStudents() {
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
    private static theUser[] readUsers() {
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
    private static void addUsers(theUser[] users) {
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
