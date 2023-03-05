package schedule_system.utils;

/**
 * StudentData
 */
public class TheStudent {
    private String[] courses;
    private String name;

    public TheStudent(String[] courses, String name) {
        this.courses = courses.clone();
        this.name = name;
    }

    public String[] getCourses() {
        return courses;
    }

    public String getName() {
        return name;
    }

    public void addCourse(String courseName) {
        String[] newCourses = new String[this.courses.length + 1];
        for (int i = 0; i < this.courses.length; i++) {
            newCourses[i] = this.courses[i];
        }
        newCourses[this.courses.length] = courseName;
        this.courses = newCourses.clone();
    }
}
