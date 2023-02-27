package schedule_system;

import schedule_system.utils.Course;

/**
 * Admin
 */
public class Admin {
    /**
     * 为学生添加课程
     * 
     * @param course
     * @param student
     * @return ifSuccess
     */
    public boolean addCourseForStudent(Course course, Student student) {
        student.addCourse(course);
        return true;
    }

    public Course getCourseOfStudent(Student student, String courseName){
        return student.getCourseByName(courseName);
    }

    public Student[] getAllStudents(){
        return System.getStudents();
    }
}
