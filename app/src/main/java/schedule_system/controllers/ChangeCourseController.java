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

@RestController
@CrossOrigin(maxAge = 3600)
public class ChangeCourseController {
    private final Logger logger = LoggerFactory.getLogger(ChangeCourseController.class);
    @Autowired
    CourseData courseData;
    @Autowired
    StudentData studentData;

    @PostMapping("/changeCourse")
    public boolean changeCourse(@RequestBody CourseInfoRecord inPutCourse) {
        return changeCourseLocation(
                inPutCourse.courseName,
                inPutCourse.newLocationName
                )
            && changeCourseInStudents(
                    inPutCourse.courseName,
                    inPutCourse.upDatedStudents
                    );
    }

    private boolean changeCourseLocation(String courseName, String newLocationName){
        // TODO: 下面这个函数没有实现
        courseData.changeCourseLocation(courseName, newLocationName);
        return true;
    }

    private boolean changeCourseInStudents(String courseName, String[] upDatedStudents) {
        boolean success = true;
        // first delete the course form student
        success = success && studentData.deleteCourseFromStudents(courseName);
        // then add the course back
        success = success && studentData.addCourseToStudents(courseName, upDatedStudents);
        return success;
    }

    record CourseInfoRecord(
            String courseName,
            String[] upDatedStudents,
            String newLocationName) {
    }
}
