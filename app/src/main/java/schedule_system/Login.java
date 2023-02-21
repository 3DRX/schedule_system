package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Login {
    // 用于读写json
    final private String path = "src/main/resources/users.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 从resources/users.json读取所有的用户存入数组中
    final private theUser[] inputUsers = readUsers();

    Login() {
        initWindow();
    }

    /**
     * 创建登陆界面
     */
    private void initWindow() {
        JFrame loginWindow = new JFrame("Login");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginWindow.setBounds(screensize.width / 2 - 150, screensize.height / 2 - 80, 300, 160);
        loginWindow.setResizable(false);
        loginWindow.setLayout(new GridLayout(2, 1, 5, 5));
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2, 2, 5, 5));
        jp.setSize(100, 50);
        JLabel jl1 = new JLabel("\u8d26\u53f7");            //账号
        JLabel jl2 = new JLabel("\u5bc6\u7801");            //密码
        JTextField idInput = new JTextField("", 20);
        JPasswordField pswdInput = new JPasswordField("", 20);
        jp.add(jl1);
        jp.add(idInput);
        jp.add(jl2);
        jp.add(pswdInput);
        jp.setVisible(true);
        loginWindow.add(jp);
        JPanel jpl = new JPanel();
        jpl.setLayout(new GridLayout(2, 1));
        JLabel jl = new JLabel();
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jpl.add(jl);
        JButton bt = new JButton("\u767b\u5f55");       //登录
        bt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                boolean loginSuccess = false;
                for (int i = 0; i < inputUsers.length; i++) {
                    theUser theuser = inputUsers[i];
                    if (idInput.getText().equals(theuser.id) && pswdInput.getText().equals(theuser.password)) {
                        System.out.println("asd");
                        loginSuccess = true;
                        break;
                    }
                }
                if (loginSuccess) {
                    jl.setText("登陆成功");
                } else {
                    jl.setText("登陆失败");
                }
            }
        });
        jpl.add(bt);
        loginWindow.add(jpl);
        loginWindow.setVisible(true);
    }

    /**
     * 从resources/users.json中读取users，返回users[]
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
