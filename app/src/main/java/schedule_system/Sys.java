package schedule_system;

import schedule_system.utils.theUser;

/**
 * System
 */
public class Sys {
    private static Student[] students = null;

    private static void generateStudents() {
        students = new Student[UserData.students().length];
        int i = 0;
        for (theUser student : UserData.students()) {
            students[i] = new Student(student.getId());
            i++;
        }
    }

    public static Student[] getStudents() {
        generateStudents();
        return students;
    }

    public static Student getStudentByName(String name) {
        generateStudents();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
}
