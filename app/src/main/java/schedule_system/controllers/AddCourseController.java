package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;

import schedule_system.utils.Course;

/**
 * AddCourseController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class AddCourseController {
    private final Logger logger = LoggerFactory.getLogger(ReferCourseController.class); // 日志控制器
    @Autowired
    CourseData courseData; // 课程数据控制器
    @Autowired
    StudentData studentData; // 学生数据控制器

    /**
     * 添加课程，并检测inputCourse是否与现有数据库中信息冲突
     * 1. 新建课程是否与其他课程冲突
     * 2. 上新建课程的学生是否在新课程的时间有时间上课
     * 
     * @param inputCourse
     * @return
     */
    @PostMapping("/addCourse")
    public boolean addCourse(
            @RequestBody CourseInfoRecord inputCourse) {
        return createCourse(inputCourse.course()) &&
                addCourseToStudent(inputCourse.course().getName(), inputCourse.students());
    }

    /**
     * 添加课程检查课程与其他课程是否冲突
     * 
     * @param course
     * @return
     */
    private boolean createCourse(Course course) {
        boolean successFlag = courseData.addCourse(course);
        if (!successFlag) {
            logger.warn("添加课程" + course.getName() + "失败：与其他课程有冲突");
        } else {
            logger.info("成功创建课程：" + course.getName());
        }
        return successFlag;
    }

    /**
     * 将新课程添加到学生课表，并检查冲突
     * 
     * @param courseName
     * @param students
     * @return
     */
    private boolean addCourseToStudent(String courseName, String[] students) {
        boolean successFlag = studentData.addCourseToStudents(courseName, students);
        if (!successFlag) {
            logger.warn("将课程添加到学生的课表中时失败，从课程列表中删除该课程");
            // 从课程列表中删除创建的课程
            boolean a = courseData.deleteCourse(courseName);
            if (!a) {
                logger.warn("从课程列表中删除课程：" + courseName + "失败");
            } else {
                logger.info("从课程列表中删除课程：" + courseName + "成功");
            }
        } else {
            logger.info("成功将课程" + courseName + "添加到学生课表中");
        }
        return successFlag;
    }
}

record CourseInfoRecord(Course course, String[] students) {
}
