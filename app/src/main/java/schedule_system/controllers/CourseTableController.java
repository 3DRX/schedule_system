package schedule_system.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.Student;
import schedule_system.System;
import schedule_system.records.CourseObjectRecord;
import schedule_system.utils.Course;

/**
 * CourseTableController
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CourseTableController {

    @GetMapping("/getCourseStatusByTime")
    public CourseObjectRecord checkCourseTableElement(String time) {
        int start = Integer.parseInt(time.split("-")[0]);
        int end = Integer.parseInt(time.split("-")[1]);
        Course course = getCourse(start, end);
        String courseName = course.getName();
        String courseLoactionName = course.getLocation().getName();
        String[] students = getStudents(courseName);
        return new CourseObjectRecord(courseName, students, courseLoactionName);
    }

    private Course getCourse(int start, int end) {
        for (Student student : System.getStudents()) {
            for (Course course : student.getAllCourses()) {
                if (course.getClassTime().covers(start, end)) {
                    return course;
                }
            }
        }
        return null;
    }

    private String[] getStudents(String courseName) {
        ArrayList<String> res = new ArrayList<>();
        for (Student student : System.getStudents()) {
            if (student.getCourseByName(courseName) != null) {
                res.add(student.getName());
            }
        }
        String[] ans = new String[res.size()];
        return ans;
    }
}
