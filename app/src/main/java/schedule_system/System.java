package schedule_system;

import schedule_system.utils.theUser;

/**
 * System
 */
public class System {
    private static Student[] students = generateStudents();

    private static Student[] generateStudents() {
        Student[] ret = new Student[UserData.students().length];
        int i = 0;
        for (theUser student : UserData.students()) {
            ret[i] = new Student(student.getId());
            i++;
        }
        return ret;
    }

    public static Student[] getStudents(){
        return students;
    }

    public static Student getStudentByName(String name){
        for(Student student: students){
            if (student.getName().equals(name)){
                return student;
            }
        }
        return null;
    }
}
