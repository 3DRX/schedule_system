package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Course;
import schedule_system.utils.Student;

public class ChangeCourseController {
    private final Logger logger = LoggerFactory.getLogger(ChangeCourseController.class);
    CourseData courseData;
    StudentData studentData;

    @PostMapping("https://localhost:8888/changeCourse")
    public boolean changeCourse(
            @RequestBody CourseInfoRecord inPutCourse) {
        return changeCourseInStudents(inPutCourse.courseName, inPutCourse.updatedStudents, inPutCourse.newLocationName)
                && changeCourseItself(inPutCourse.courseName, inPutCourse.updatedStudents, inPutCourse.newLocationName);
    }

    public boolean changeCourseInStudents(String courseName, String[] newStudents, String newLocationName) {
        boolean success = false;
        for(String studentName : newStudents){

        }
        return success;
    }
    private boolean deleteCourse(String courseName,String studentName){
        for(Student student: studentData.getStudentClasses()){
            if(student.getName().equals(studentName)){
            //修改该学生的该课程上课地点
            }
            else{
            //删除该学生的该课程
            }
        }
    }
    public boolean changeCourseItself(String courseName, String[] newStudents, String nweLocationName) {
        boolean success = false;

        return success;
    }

    record CourseInfoRecord(String courseName, String[] updatedStudents,
                            String newLocationName) {
    }
}
