package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.Course;
import schedule_system.utils.KMap;

/**
 * 课程控制
 * 操作一组 {@link Course} 对象
 */
public class CourseData {
    final private String path = "src/main/resources/courses.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private KMap<String, Course> courses;

    /**
     * 从文件读取课程信息
     */
    public CourseData() {
        this.courses = new KMap<>();
        Arrays.stream(readCourses())
                .forEach(e -> this.courses.put(e.getName(), e));
    }

    /**
     * @return 所有课程
     */
    public Course[] allCourses() {
        return Arrays.stream(this.courses.getKeyArray(String.class))
                .map(i -> this.courses.get(i))
                .toArray(size -> new Course[size]);
    }

    /**
     * 按名称查找课程
     * 
     * @param 课程名称
     * @return 课程
     */
    public Course getCourseByName(String courseName) {
        return this.courses.get(courseName);
    }

    /**
     * 按周数和星期查找课程
     * 
     * @param week 周数
     * @param day  星期
     * @return 该周该天的所有课程
     */
    public Course[] getCourseByDay(int week, int day) {
        return Arrays.stream(this.courses.getKeyArray(String.class))
                .map(i -> this.courses.get(i))
                .filter(i -> i.getStartWeek() <= week
                        && i.getEndWeek() >= week
                        && i.getClassTime().getDay() == day)
                .toArray(size -> new Course[size]);
    }

    /**
     * 从内存中删除课程并将更改写入文件
     * 
     * @param courseName 课程名称
     * @return 是否成功
     */
    public boolean deleteCourse(String courseName) {
        this.courses.remove(courseName);
        return writeCourses(this.allCourses());
    }

    /**
     * 从文件中读取课程列表
     * 
     * @return 课程列表
     */
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

    /**
     * 创建新课程，检查是否与已有课程冲突
     * 
     * @param newCourse 新课程
     * @return 是否成功
     */
    public boolean addCourse(Course newCourse) {
        if (this.courses.containKey(newCourse.getName())) {
            return false;
        }
        this.courses.put(newCourse.getName(), newCourse);
        return writeCourses(this.allCourses());
    }

    /**
     * 将课程列表写入文件
     * 
     * @param courses 课程列表
     * @return 是否成功
     */
    private boolean writeCourses(Course[] courses) {
        File file = new File(path);
        String res = gson.toJson(courses);
        boolean successFlag = true;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            successFlag = false;
        }
        return successFlag;
    }
}
