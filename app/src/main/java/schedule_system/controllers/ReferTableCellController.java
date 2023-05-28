package schedule_system.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Activity;
import schedule_system.utils.CellContent;
import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.CourseDetail;
import schedule_system.utils.Student;

/**
 * ReferCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class ReferTableCellController {
    private final Logger logger = LoggerFactory.getLogger(ReferTableCellController.class); // 日志控制器
    @Autowired
    CourseData courseData; // 课程数据控制器
    @Autowired
    ActivityData activityData;
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
    @GetMapping("/adminGetStatusByTime")
    public CellContent[] checkCourseTableElement(String time, int week, int day, String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("查询课程：")
                .append("第 ")
                .append(week)
                .append(" 周，周 ")
                .append(day)
                .append("，")
                .append(time);
        int start = Integer.parseInt(time.split("-")[0]);
        // 获得该时间的课程
        Course[] courses = getCourses(start, week, day);
        if (courses.length == 0) {
            // 若该时间没有课程，返回信息均为空
            CellContent[] res = new CellContent[0];
            sb.append("：无课程");
            logger.info(sb.toString());
            return res;
        } else {
            // 若该时间有课程，判断该学生是否参与该课程
            // 若是，返回课程信息
            // 否则返回信息为空
            CellContent[] res = new CellContent[courses.length];
            sb.append("：[以下课程] ");
            for (int i = 0; i < courses.length; i++) {
                Course course = courses[i];
                sb.append(course.getName()).append(" ");
                res[i] = new CellContent(course.getName(),
                        getStudents(course.getName()),
                        course.getLocationName(),
                        false);
            }
            logger.info(sb.toString());
            return res;
        }
    }

    @GetMapping("/studentGetStatusByTime")
    public CellContent[] adminGetStatusByTime(String time, int week, int day, String userName) {
        int start = Integer.parseInt(time.split("-")[0]);
        if (!studentData.isStudent(userName)) {
            logger.error("用户 " + userName + " 不是学生");
            return new CellContent[0];
        }
        Course course = studentData.courseAt(userName, week, day, start);
        Activity activity = studentData.activityAt(userName, week, day, start);
        if (course == null && activity == null) {
            return new CellContent[0];
        } else if (course != null && activity != null) {
            logger.warn("{} 在 {} {} {} 有不明事物（潜在的系统错误）", userName, week, day, start);
            return new CellContent[0];
        } else if (course != null) {
            logger.info("{} 在 {} {} {} 有 {} 课", userName, week, day, start, course.getName());
            return new CellContent[] { new CellContent(course.getName(),
                    getStudents(course.getName()),
                    course.getLocationName(),
                    false) };
        } else if (activity != null) {
            logger.info("{} 在 {} {} {} 有课外活动 {}", userName, week, day, start,
                    activity.getName());
            return new CellContent[] { new CellContent(activity.getName(),
                    activity.getParticipants(),
                    activity.getLocationName(),
                    true) };
        } else {
            return new CellContent[0];
        }
    }

    @GetMapping("/studentGetCourseByName")
    public CourseDetail[] studentGetCourseByName(String studentName, String courseName) {
        if (!studentData.isStudent(studentName)) {
            logger.error("用户 {} 不是学生", studentName);
            return new CourseDetail[0];
        }
        if (Arrays.stream(studentData.getStudentById(studentName).getCourses())
                .noneMatch(courseName::equals)) {
            logger.error("用户 {} 没有参与 {} 课", studentName, courseName);
            return new CourseDetail[0];
        }
        Course course = courseData.getCourseByName(courseName);
        if (course == null) {
            logger.warn("查询 {} 的 {} 课失败", studentName, courseName);
            return new CourseDetail[0];
        } else {
            logger.info("查询到 {} 的课程 {}", studentName, courseName);
            return new CourseDetail[] {
                    new CourseDetail(
                            course.getTestWeek(),
                            new CellContent(
                                    course.getName(),
                                    getStudents(course.getName()),
                                    course.getLocationName(),
                                    false))
            };
        }
    }

    @GetMapping("/studentGetActivityByName")
    public CellContent[] studentGetActivityByName(String studentName, String activityName) {
        if (!studentData.isStudent(studentName)) {
            logger.error("用户 {} 不是学生", studentName);
            return new CellContent[0];
        }
        if (Arrays.stream(studentData.getStudentById(studentName).getActivities())
                .noneMatch(activityName::equals)) {
            logger.error("用户 {} 没有课外活动 {}", studentName, activityName);
            return new CellContent[0];
        }
        Activity activity = activityData.getActivityByName(activityName);
        if (activity == null) {
            logger.warn("查询 {} 的课外活动 {} 失败", studentName, activityName);
            return new CellContent[0];
        } else {
            logger.info("成功查询到 {} 的课外活动 {}", studentName, activityName);
            return new CellContent[] { new CellContent(activity.getName(),
                    activity.getParticipants(),
                    activity.getLocationName(),
                    true) };
        }
    }

    /**
     * 获取该时间的课程的数组
     * 如果没有课程，返回空数组
     * 
     * @param week 周
     * @param day  天
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
