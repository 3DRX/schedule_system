package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Course;
import schedule_system.utils.KList;
import schedule_system.utils.Student;

public class ChangeCourseController {
    private final Logger logger = LoggerFactory.getLogger(ChangeCourseController.class);
    CourseData courseData;
    StudentData studentData;

    @PostMapping("https://localhost:8888/changeCourse")
    public boolean changeCourse(
            @RequestBody CourseInfoRecord inPutCourse) {
        return changeCourseInStudents(inPutCourse.courseName, inPutCourse.upDatedStudents, inPutCourse.newLocationName);
    }

    public boolean changeCourseInStudents(String courseName, String[] upDatedStudents, String newLocationName) {
        boolean success = false;
        KList<Student> students = studentData.getStudentsByClass(courseName);//有className 名字的所有学生信息
//        for () {
//            for (String updatedStudent : upDatedStudents) {
//                if(students.getName().equals(updatedStudent)){//如果当前的学生名字跟upDatedStudents 里的某一个学生的名字相同
//                    //不删除该学生，只需修改上课地址信息
//                    success=true;
//                }
//                else {
//                    student.deleteCourseIfHave(courseName);
//                }
//            }
//        }
        return success;
    }
    record CourseInfoRecord(String courseName, String[] upDatedStudents,
                            String newLocationName) {
    }
}
