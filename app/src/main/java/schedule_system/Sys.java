package schedule_system;

import schedule_system.utils.theUser;

/**
 * System
 */
public class Sys {
    private static Student[] students = null;
    final private UserData userData = new UserData();

    private void generateStudents() {
        students = new Student[userData.students().length];
        int i = 0;
        for (theUser student : userData.students()) {
            students[i] = new Student(student.getId());
            i++;
        }
    }

    // public static Student[] getStudents() {
    //     generateStudents();
    //     return students;
    // }

    // public static Student getStudentByName(String name) {
    //     generateStudents();
    //     for (Student student : students) {
    //         if (student.getName().equals(name)) {
    //             return student;
    //         }
    //     }
    //     return null;
    // }
}
