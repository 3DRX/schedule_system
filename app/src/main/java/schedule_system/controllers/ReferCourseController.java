package schedule_system.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.ClassTime;
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
    public CourseObjectRecord[] checkCourseTableElement(String time, int week, int day, String userName) {
        int start = Integer.parseInt(time.split("-")[0]);
        int end = Integer.parseInt(time.split("-")[1]);
        // 获得该时间的课程
        Course[] courses = getCourses(start, week, day);
        String courseName;
        String courseLocationName;
        String[] students;
        if (courses.length == 0) {
            // 若该时间没有课程，返回信息均为空
            CourseObjectRecord[] res = new CourseObjectRecord[0];
            return res;
        } else {
            // 若该时间有课程，判断该学生是否参与该课程
            // 若是，返回课程信息
            // 否则返回信息为空
            if (studentData.isStudent(userName)) {
                // 学生
                // 1. 找到courses中学生上的那一门，
                // 如果该时段所有的课学生都不上，theCourse=null
                Course theCourse = null;
                for (Course course : courses) {
                    String[] studentsOfCourse = getStudents(course.getName());
                    for (String studentName : studentsOfCourse) {
                        if (userName.equals(studentName)) {
                            theCourse = course;
                        }
                    }
                }
                if (theCourse == null) {
                    return new CourseObjectRecord[0];
                } else {
                    // 2. 获得课程名字
                    courseName = theCourse.getName();
                    // 3. 获得上课地点的名字
                    courseLocationName = theCourse.getLocationName();
                    // 4. 获得上课学生的名单
                    students = getStudents(courseName);
                    CourseObjectRecord[] res = {
                            new CourseObjectRecord(
                                    courseName,
                                    students,
                                    courseLocationName)
                    };
                    return res;
                }
            } else {
                // 管理员
                CourseObjectRecord[] res = new CourseObjectRecord[courses.length];
                for (int i = 0; i < courses.length; i++) {
                    Course course = courses[i];
                    res[i] = new CourseObjectRecord(course.getName(),
                            getStudents(course.getName()),
                            course.getLocationName());
                }
                return res;
            }
        }
    }

    /**
     * 获取该时间的课程的数组
     * 如果没有课程，返回空数组
     * 
     * @param start 开始时间
     * @param end   结束时间
     * @param week  周
     * @param day   天
     * @return Course[]
     */
    private Course[] getCourses(int time, int week, int day) {
        // check input
        if (!ClassTime.isValidTime(week, day, time)) {
            throw new IllegalArgumentException("invalid time");
        }
        int index = ClassTime.realTimeToIndex(week, day, time);
        return Arrays.stream(courseData.allCourses())
                .filter(course -> course.getOccupiedTime().get(index))
                .toArray(Course[]::new);
    }

    /**
     * 获得指定课程所有学生的名字
     * 
     * @param courseName
     * @return
     */
    private String[] getStudents(String courseName) {
        ArrayList<String> studentsOfCourse = new ArrayList<>();
        for (Student theStudent : studentData.getStudentsArray()) {
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

record CourseObjectRecord(
        String name,
        String[] students,
        String location) {
}
