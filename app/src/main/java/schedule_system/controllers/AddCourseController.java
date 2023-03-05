package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.CourseData;
import schedule_system.StudentData;
import schedule_system.records.CourseInfoRecord;
import schedule_system.utils.Course;

/**
 * AddCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class AddCourseController {
    private final StudentData studentData = new StudentData();
    private final CourseData courseData = new CourseData();
    private final Logger logger = LoggerFactory.getLogger(ReferCourseController.class);

    @PostMapping("/addCourse")
    public boolean addCourse(
            @RequestBody CourseInfoRecord inputCourse) {
        // TODO: 检测inputCourse是否与现有数据库中信息冲突
        // （上课时间地点是否冲突、课程名称是否冲突）
        // 时间：同一个人不能同时上两个课
        // 地点：同一个地点不能在同一时间有两门课
        return createCourse(inputCourse.course()) &&
                addCourseToStudent(inputCourse.course().getName(), inputCourse.students());
    }

    private boolean createCourse(Course course) {
        boolean successFlag = courseData.addCourse(course);
        if (!successFlag) {
            logger.warn("创建课程失败");
        }
        return successFlag;
    }

    private boolean addCourseToStudent(String courseName, String[] students) {
        boolean successFlag = studentData.addCourseToStudents(courseName, students);
        if (!successFlag) {
            logger.warn("将课程添加到学生的课表中时失败");
        }
        return successFlag;
    }
}
