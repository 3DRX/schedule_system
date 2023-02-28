package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.TheStudent;

/**
 * StudentData
 */
public class StudentData {
    final private static String path = "src/main/resources/studentCourses.json";
    final private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static TheStudent[] students = readStudentClasses();

    public static TheStudent[] readStudentClasses() {
        TheStudent[] readStudent = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            readStudent = new Gson().fromJson(reader, TheStudent[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStudent;
    }

    public static void writeStudent() {
        File file = new File(path);
        String res = gson.toJson(students);
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
