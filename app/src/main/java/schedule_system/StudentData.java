package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Student;

/**
 * StudentData
 */
public class StudentData {
    final private String path = "src/main/resources/studentCourses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Student[] students;

    public Student[] getStudentClasses() {
        this.students = readStudentClasses();
        return this.students;
    }

    public boolean deleteCourseFromStudents(String courseName) {
        this.students = readStudentClasses();
        for (Student student : this.students) {
            student.deleteCourseIfHave(courseName);
        }
        return writeStudentClasses(this.students);
    }

    public boolean addCourseToStudents(String courseName, String[] students) {
        this.students = readStudentClasses();
        for (String studentName : students) {
            for (Student student : this.students) {
                if (student.getName().equals(studentName)) {
                    student.addCourse(courseName);
                }
            }
        }
        return writeStudentClasses(this.students);
    }

    public boolean isStudent(String userName) {
        this.students = readStudentClasses();
        for (Student theStudent : students) {
            if (theStudent.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    private Student[] readStudentClasses() {
        Student[] readStudent = {};
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            readStudent = new Gson().fromJson(reader, Student[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStudent;
    }

    public boolean writeStudentClasses(Student[] students) {
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
