package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Course;

/**
 * CourseData
 */
public class CourseData {
    final private String path = "src/main/resources/courses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Course[] courses;

    public Course[] allCourses() {
        this.courses = readCourses();
        return this.courses;
    }

    private Course[] readCourses() {
        Course[] read_users = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            read_users = new Gson().fromJson(reader, Course[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return read_users;
    }

    public void writeCourses(Course[] courses) {
        File file = new File(path);
        String res = gson.toJson(courses);
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
