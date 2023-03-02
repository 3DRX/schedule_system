package schedule_system.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.CourseData;
import schedule_system.StudentData;
import schedule_system.records.CourseObjectRecord;
import schedule_system.utils.Course;
import schedule_system.utils.TheStudent;

/**
 * CourseTableController
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CourseTableController {
    private StudentData studentData = new StudentData();
    private CourseData courseData = new CourseData();

    @GetMapping("/getCourseStatusByTime")
    public CourseObjectRecord checkCourseTableElement(String time, int week, int day) {
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
        } else {
            courseName = course.getName();
            courseLoactionName = course.getLocation().getName();
            students = getStudents(courseName);
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
        for (TheStudent theStudent : studentData.getStudentClasses()) {
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
