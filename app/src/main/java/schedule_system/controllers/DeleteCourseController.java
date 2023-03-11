package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.CourseData;
import schedule_system.StudentData;

/**
 * DeleteCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class DeleteCourseController {
    private final StudentData studentData = new StudentData(); // 学生数据控制器
    private final CourseData courseData = new CourseData(); // 课程数据控制器
    private final Logger logger = LoggerFactory.getLogger(LoginController.class); // 日志控制器

    /**
     * 删除课程
     * 
     * @param courseName
     * @return
     */
    @DeleteMapping("/deleteCourse")
    public boolean deleteCourse(String courseName) {
        return deleteCourseInStudents(courseName) && deleteCourseItSelf(courseName);
    }

    /**
     * 从学生课表中删除课程
     * 
     * @param courseName
     * @return
     */
    private boolean deleteCourseInStudents(String courseName) {
        boolean successFlag = studentData.deleteCourseFromStudents(courseName);
        if (!successFlag) {
            logger.warn("从学生课表中删除课程：" + courseName + "失败");
        } else {
            logger.info("从学生课表中删除课程：" + courseName + "成功");
        }
        return successFlag;
    }

    /**
     * 从所有课程的列表中删除课程
     * 
     * @param courseName
     * @return
     */
    private boolean deleteCourseItSelf(String courseName) {
        boolean successFlag = courseData.deleteCourse(courseName);
        if (!successFlag) {
            logger.warn("从课程列表中删除课程：" + courseName + "失败");
        } else {
            logger.info("从课程列表中删除课程：" + courseName + "成功");
        }
        return successFlag;
    }
}
