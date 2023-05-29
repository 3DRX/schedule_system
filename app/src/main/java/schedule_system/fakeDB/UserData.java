package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.KList;
import schedule_system.utils.theUser;

public class UserData {
    final private String path = "src/main/resources/users.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private KList<theUser> students;

    public UserData() {
        this.students = readUsers();
    }

    /**
     * @return theUser[] students
     */
    public theUser[] students() {
        return this.students.toArray(new theUser[this.students.size()]);
    }

    public boolean addStudent(theUser user) {
        if (!this.students.contains(user) && !user.getId().equals("admin")) {
            this.students.add(user);
            writeUsers(this.students.toArray());
            return true;
        }
        return false;
    }

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
