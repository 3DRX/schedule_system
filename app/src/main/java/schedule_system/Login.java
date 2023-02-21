package schedule_system;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Login {
    final String path = "src/main/resources/users.json";
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Login() {
        // 初始化Login模块时，先从resources/users.json读取所有的用户存入数组中
        // addUsers();
        theUser[] inputUsers = readUsers();

        // 创建居中的420*420登陆窗口
        JFrame frame = new JFrame();
        frame.setTitle("Log In");
        frame.setResizable(false);
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screen.width / 2 - 210, screen.height / 2 - 210);
        frame.setVisible(true);
    }

    // 从resources/users.json中读取users，返回users[]
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

    // 将一组特定的users写入resources/users.json中
    private void addUsers() {
        ArrayList<theUser> users = new ArrayList<>();
        users.add(new theUser(true, "001", "password"));
        users.add(new theUser(false, "002", "69420"));
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
