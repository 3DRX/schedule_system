package schedule_system.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.Student;
import schedule_system.StudentData;
import schedule_system.Sys;
import schedule_system.records.CourseObjectRecord;
import schedule_system.utils.Course;
import schedule_system.utils.TheStudent;

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
        // System.out.println(start);
        // System.out.println("=================================");
        // Student[] stu = Sys.getStudents();
        // System.out.println(stu[0].getName());
        // System.out.println("=================================");
        Course course = getCourse(start, end);
        String courseName;
        String courseLoactionName;
        String[] students;
        if (course == null) {
            System.out.println("course == null");
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

    private Course getCourse(int start, int end) {
        StudentData.
        // for (TheStudent student : StudentData.readStudentClasses()) {
        //     System.out.println(student.getName());
        //     // for (Course course : student.getAllCourses()) {
        //     // if (course.getClassTime().covers(start, end)) {
        //     // return course;
        //     // }
        //     // }
        // }
        return null;
    }

    private String[] getStudents(String courseName) {
        ArrayList<String> res = new ArrayList<>();
        for (Student student : Sys.getStudents()) {
            if (student.getCourseByName(courseName) != null) {
                res.add(student.getName());
            }
        }
        String[] ans = new String[res.size()];
        res.toArray(ans);
        return ans;
    }
}
