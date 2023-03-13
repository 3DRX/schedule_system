package schedule_system.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.records.CourseObjectRecord;
import schedule_system.utils.Course;
import schedule_system.utils.Student;

/**
 * ReferCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class ReferCourseController {
    private final Logger logger = LoggerFactory.getLogger(ReferCourseController.class); // 日志控制器
    @Autowired
    CourseData courseData; // 课程数据控制器
    @Autowired
    StudentData studentData; // 学生数据控制器

    /**
     * 按照时间查询课程
     * 
     * @param time
     * @param week
     * @param day
     * @param userName
     * @return
     */
    @GetMapping("/getCourseStatusByTime")
    public CourseObjectRecord checkCourseTableElement(String time, int week, int day, String userName) {
        int start = Integer.parseInt(time.split("-")[0]);
        int end = Integer.parseInt(time.split("-")[1]);
        // 获得改时间的课程
        Course course = getCourse(start, end, week, day);
        String courseName;
        String courseLocationName;
        String[] students;
        if (course == null) {
            // 若该时间没有课程，返回信息均为空
            courseName = "";
            students = new String[0];
            courseLocationName = "";
            return new CourseObjectRecord(courseName, students, courseLocationName);
        } else {
            // 若该时间有课程，判断该学生是否参与该课程
            // 若是，返回课程信息
            // 否则返回信息为空
            courseName = course.getName();
            courseLocationName = course.getLocation().getName();
            students = getStudents(courseName);
            if (studentData.isStudent(userName)) {
                boolean haveClass = false;
                for (String studentName : getStudents(courseName)) {
                    if (studentName.equals(userName)) {
                        haveClass = true;
                        break;
                    }
                }
                if (!haveClass) {
                    courseName = "";
                    students = new String[0];
                    courseLocationName = "";
                }
            } else {
                return new CourseObjectRecord(courseName, students, courseLocationName);
            }
        }
        return new CourseObjectRecord(courseName, students, courseLocationName);
    }

    /**
     * 获取该时间的课程
     * 如果没有课程，返回null
     * 
     * @param start
     * @param end
     * @param week
     * @param day
     * @return Course对象
     */
    private Course getCourse(int start, int end, int week, int day) {
        for (Course course : courseData.allCourses()) {
            if (course.covers(week) && course.getClassTime().covers(start, end, day)) {
                return course;
            }
        }
        return null;
    }

    /**
     * 获得指定课程所有学生的名字
     * 
     * @param courseName
     * @return
     */
    private String[] getStudents(String courseName) {
        ArrayList<String> studentsOfCourse = new ArrayList<>();
        for (Student theStudent : studentData.getStudentClasses()) {
            String[] courses = theStudent.getCourses();
            for (String cN : courses) {
                if (cN.equals(courseName)) {
                    studentsOfCourse.add(theStudent.getName());
                }
            }
        }
        String[] res = new String[studentsOfCourse.size()];
        studentsOfCourse.toArray(res);
        return res;
    }
}
