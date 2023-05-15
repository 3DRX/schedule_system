package schedule_system.utils;

public class Student {
    private String[] courses;
    private String name;

    public Student(String[] courses, String name) {
        this.courses = courses.clone();
        this.name = name;
    }

    public void deleteCourseIfHave(String courseName) {
        KList<String> newCourses = new KList<>(String.class);
        for (String course : this.courses) {
            if (course.equals(courseName)) {
            } else {
                newCourses.add(course);
            }
        }
        this.courses = newCourses.toArray();
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
