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
    final private String path = "src/main/resources/studentCourses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private TheStudent[] students;

    public TheStudent[] getStudentClasses() {
        this.students = readStudentClasses();
        return this.students;
    }

    public boolean addCourseToStudents(String courseName, String[] students) {
        this.students = readStudentClasses();
        for (String studentName : students) {
            for (TheStudent student : this.students) {
                if (student.getName().equals(studentName)) {
                    student.addCourse(courseName);
                }
            }
        }
        return writeStudentClasses(this.students);
    }

    public boolean isStudent(String userName) {
        this.students = readStudentClasses();
        for (TheStudent theStudent : students) {
            if (theStudent.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private TheStudent[] readStudentClasses() {
        TheStudent[] readStudent = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            readStudent = new Gson().fromJson(reader, TheStudent[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStudent;
    }

    public boolean writeStudentClasses(TheStudent[] students) {
        File file = new File(path);
        String res = gson.toJson(students);
        boolean successFlag = true;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            successFlag = false;
            e.printStackTrace();
        }
        return successFlag;
    }
}
