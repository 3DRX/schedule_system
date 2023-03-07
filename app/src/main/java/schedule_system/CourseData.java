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

    public Course[] allCourses() {
        this.courses = readCourses();
        return this.courses;
    }

    public Course getCourseByName(String courseName) {
        for (Course course : this.courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    public boolean deleteCourse(String courseName) {
        this.courses = readCourses();
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

    public boolean addCourse(Course newCourse) {
        this.courses = readCourses();
        Course[] newCourses = new Course[this.courses.length + 1];
        for (int i = 0; i < this.courses.length; i++) {
            if (this.courses[i].conflictsWith(newCourse)) {
                // 检查课程本身与其他课程有无时空冲突
                return false;
            }
            newCourses[i] = this.courses[i];
        }
        newCourses[newCourses.length - 1] = newCourse;
        this.courses = newCourses;
        return writeCourses(this.courses);
    }

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
