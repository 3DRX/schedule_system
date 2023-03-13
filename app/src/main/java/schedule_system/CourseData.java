package schedule_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

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

    public CourseData() {
        this.courses = readCourses();
    }

    /**
     * 获得所有课程数组
     * 
     * @return
     */
    public Course[] allCourses() {
        return this.courses;
    }

    /**
     * 按名称查找课程
     * 
     * @param String
     * @return Course course
     */
    public Course getCourseByName(String courseName) {
        for (Course course : this.courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    /**
     * 从内存中删除课程并将更改写入文件
     * 
     * @param courseName
     * @return
     */
    public boolean deleteCourse(String courseName) {
        ArrayList<Course> newCourses = new ArrayList<>();
        for (Course course : this.courses) {
            if (course.getName().equals(courseName)) {
            } else {
                newCourses.add(course);
            }
        }
        Course[] resCourses = new Course[newCourses.size()];
        newCourses.toArray(resCourses);
        this.courses = resCourses;
        return writeCourses(this.courses);
    }

    /**
     * 从文件中读取课程列表
     * 
     * @return
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
     * @param newCourse
     * @return
     */
    public boolean addCourse(Course newCourse) {
        Course[] newCourses = new Course[this.courses.length + 1];
        for (int i = 0; i < this.courses.length; i++) {
            if (this.courses[i].conflictsWith(newCourse)) {
                return false;
            }
            newCourses[i] = this.courses[i];
        }
        newCourses[newCourses.length - 1] = newCourse;
        this.courses = newCourses;
        return writeCourses(this.courses);
    }

    /**
     * 将更新后的课程列表写入文件
     * 
     * @param courses
     * @return
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
