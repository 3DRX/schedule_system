package schedule_system.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.CourseData;
import schedule_system.StudentData;
import schedule_system.records.CourseObjectRecord;
import schedule_system.utils.Course;
import schedule_system.utils.Student;

/**
 * ReferCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class ReferCourseController {
    private StudentData studentData = new StudentData();
    private CourseData courseData = new CourseData();
    private final Logger logger = LoggerFactory.getLogger(ReferCourseController.class);

    @GetMapping("/getCourseStatusByTime")
    public CourseObjectRecord checkCourseTableElement(String time, int week, int day, String userName) {
        int start = Integer.parseInt(time.split("-")[0]);
        int end = Integer.parseInt(time.split("-")[1]);
        Course course = getCourse(start, end, week, day);
        String courseName;
        String courseLoactionName;
        String[] students;
        if (course == null) {
            courseName = "";
            students = new String[0];
            courseLoactionName = "";
            logger.info("第" + week + "周，周" + day + "，" + time + "：所有学生均无课程");
            return new CourseObjectRecord(courseName, students, courseLoactionName);
        } else {
            courseName = course.getName();
            courseLoactionName = course.getLocation().getName();
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
                    courseLoactionName = "";
                    logger.info("第" + week + "周，周" + day + "，" + time + "：学生" + userName + "查询，无课程");
                } else {
                    logger.info("第" + week + "周，周" + day + "，" + time + "：学生" + userName + "查询，有课程");
                }
            } else {
                logger.info("第" + week + "周，周" + day + "，" + time + "：管理员查询，有课程");
                return new CourseObjectRecord(courseName, students, courseLoactionName);
            }
        }
        return new CourseObjectRecord(courseName, students, courseLoactionName);
    }

    private Course getCourse(int start, int end, int week, int day) {
        for (Course course : courseData.allCourses()) {
            if (course.covers(week) && course.getClassTime().covers(start, end, day)) {
                return course;
            }
        }
        return null;
    }

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
