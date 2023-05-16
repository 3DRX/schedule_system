package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.BitMap;
import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.KMap;
import schedule_system.utils.Student;

/**
 * StudentData
 */
public class StudentData {
    final private String path = "src/main/resources/studentCourses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger = LoggerFactory.getLogger(StudentData.class);

    private KMap<String, Student> students;
    private KMap<String, BitMap> schedules;

    public StudentData() {
        this.students = new KMap<>();
        this.schedules = new KMap<>();
        Arrays.stream(readStudentClasses())
                .forEach((e) -> {
                    this.schedules.put(e.getName(), generateSchedule(e));
                    this.students.put(e.getName(), e);
                });
    }

    public boolean isOccupied(String studentName, int week, int day, int time) {
        BitMap occupiedTime = this.schedules.get(studentName);
        if (occupiedTime == null) {
            return false;
        }
        return occupiedTime.get(ClassTime.realTimeToIndex(week, day, time));
    }

    public boolean isOccupied(String studentName, int index) {
        BitMap occupiedTime = this.schedules.get(studentName);
        if (occupiedTime == null) {
            return false;
        }
        return occupiedTime.get(index);
    }

    private BitMap generateSchedule(Student student) {
        BitMap res = new BitMap(20 * 5 * 10);
        // get all occupied time slot
        CourseData courseData = new CourseData();
        for (String courseName : student.getCourses()) {
            Course course = courseData.getCourseByName(courseName);
            if (course == null) {
                continue;
            }
            res = res.or(course.getOccupiedTime());
        }
        return res;
    }

    public Student[] getStudentClasses() {
        return Arrays.stream(this.students.getKeyArray(String.class))
                .map(i -> this.students.get(i))
                .toArray(size -> new Student[size]);
    }

    public Student getStudentById(String id) {
        return this.students.get(id);
    }

    public boolean deleteCourseFromStudents(String courseName) {
        for (Student student : this.getStudentClasses()) {
            student.deleteCourseIfHave(courseName);
        }
        return writeStudentClasses(this.getStudentClasses());
    }

    public boolean addCourseToStudents(String newCourseName, String[] students) {
        final CourseData courseData = new CourseData();
        for (String studentName : students) {
            Student student = this.students.get(studentName);
            if (student == null) {
                continue;
            }
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
        return writeStudentClasses(this.getStudentClasses());
    }

    public boolean isStudent(String userName) {
        return this.students.containKey(userName);
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
