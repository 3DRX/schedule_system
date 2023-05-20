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
    public boolean addActivity(
            @RequestBody ActivityInfoRecord inputActivity) {
        if (!mapData.isValidLocation(inputActivity.activity().getLocationName())) {
            logger.warn("添加课外活动 " + inputActivity.activity().getName() + " 失败：地点不存在");
            return false;
        }
        if (!createActivity(inputActivity.activity())) {
            return false;
        }
        if (!addActivityToStudents(inputActivity.activity().getName(), inputActivity.students())) {
            return false;
        }
        return true;
    }

    private boolean createActivity(Activity activity) {
        if (activityData.isActivityExist(activity.getName())) {
            logger.warn("添加课外活动 " + activity.getName() + " 失败：课外活动已存在");
            return false;
        }
        logger.info("添加课外活动 " + activity.getName() + " 成功");
        return true;
    }

    private boolean addActivityToStudents(String activityName, String[] studentNames) {
        return studentData.addActivityToStudents(activityName, studentNames);
    }
}

record ActivityInfoRecord(Activity activity, String[] students) {
}
