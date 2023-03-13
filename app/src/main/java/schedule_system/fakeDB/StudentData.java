package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Course;
import schedule_system.utils.Student;

/**
 * StudentData
 */
public class StudentData {
    final private String path = "src/main/resources/studentCourses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger = LoggerFactory.getLogger(StudentData.class);

    private Student[] students;

    public StudentData() {
        this.students = readStudentClasses();
    }

    public Student[] getStudentClasses() {
        return this.students;
    }

    public boolean deleteCourseFromStudents(String courseName) {
        for (Student student : this.students) {
            student.deleteCourseIfHave(courseName);
        }
        return writeStudentClasses(this.students);
    }

    public boolean addCourseToStudents(String newCourseName, String[] students) {
        final CourseData courseData = new CourseData();
        for (String studentName : students) {
            for (Student student : this.students) {
                if (student.getName().equals(studentName)) {
                    // 检查学生的课程会不会冲突
                    // 即：学生的所有课程是否有时间重叠
                    // TODO: TEST_ME
                    String[] studentCourses = student.getCourses();
                    Course newCourse = courseData.getCourseByName(newCourseName);
                    if (newCourse == null) {
                        logger.warn("为学生添加课程" + newCourseName + "失败：课程不存在");
                        return false;
                    }
                    for (int i = 0; i < studentCourses.length; i++) {
                        Course loopCourse = courseData.getCourseByName(studentCourses[i]);
                        if (loopCourse.timeOverlapsWith(newCourse)) {
                            logger.warn("为学生" + student.getName() + "添加课程"
                                    + newCourseName + "失败：与学生已有课程："
                                    + studentCourses[i] + "冲突");
                            return false;
                        }
                    }
                    student.addCourse(newCourseName);
                }
            }
        }
        return writeStudentClasses(this.students);
    }

    public boolean isStudent(String userName) {
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
