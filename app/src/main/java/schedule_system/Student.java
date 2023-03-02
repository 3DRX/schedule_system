package schedule_system;

import java.util.ArrayList;

import schedule_system.utils.Course;

/**
 * Student
 */
public class Student {
    private ArrayList<Course> courses = new ArrayList<>();
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public Course[] getAllCourses() {
        Course[] res = new Course[this.courses.size()];
        this.courses.toArray(res);
        return res;
    }

    public String getName() {
        return this.name;
    }

    public void gotoClass(Course course) {
    }

    public boolean addCourse(Course course) {
        this.courses.add(course);
        return true;
    }

    public Course getCourseByName(String name) {
        for (Course i : courses) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }
}
