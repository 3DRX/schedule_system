package schedule_system.controllers;

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

    @PostMapping("/addCourse")
    public boolean addCourse(
            @RequestBody CourseInfoRecord inputCourse) {
        return createCourse(inputCourse.course()) &&
                addCourseToStudent(inputCourse.course().getName(), inputCourse.students());
    }

    private boolean createCourse(Course course) {
        return courseData.addCourse(course);
    }

    private boolean addCourseToStudent(String courseName, String[] students) {
        return studentData.addCourseToStudents(courseName, students);
    }
}
