package schedule_system;

import java.util.ArrayList;

import schedule_system.utils.Course;

/**
 * Student
 */
public class Student {
    ArrayList<Course> courses = new ArrayList<>();

    public void gotoClass(Course course) {
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
