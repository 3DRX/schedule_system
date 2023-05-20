package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.MapData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Activity;

@RestController
@CrossOrigin
public class AddActivityController {
    private final Logger logger = LoggerFactory.getLogger(AddCourseController.class); // 日志控制器
    @Autowired
    CourseData courseData;
    @Autowired
    StudentData studentData;
    @Autowired
    ActivityData activityData;
    @Autowired
    MapData mapData;

    @PostMapping("/addActivity")
    public boolean addActivity(@RequestBody ActivityInfoRecord inputActivity) {
        // check if location is valid
        return true;
    }
}

record ActivityInfoRecord(Activity activity, String[] students) {
}
