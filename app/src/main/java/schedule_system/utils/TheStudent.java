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
}
