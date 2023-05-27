package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.StudentData;

@RestController
@CrossOrigin
public class DeleteActivityController {
    private final Logger logger = LoggerFactory.getLogger(DeleteActivityController.class); // 日志控制器
    @Autowired
    ActivityData activityData;
    @Autowired
    StudentData studentData;

    @DeleteMapping("/deleteActivity")
    public boolean deleteActivity(String activityName) {
        return deleteActivityItSelf(activityName) && deleteActivityInStudents(activityName);
    }

    private boolean deleteActivityItSelf(String activityName) {
        boolean successFlag = activityData.deleteActivity(activityName);
        if (!successFlag) {
            logger.warn("从所有活动的列表中删除活动：" + activityName + "失败");
        } else {
            logger.info("从所有活动的列表中删除活动：" + activityName + "成功");
        }
        return successFlag;
    }

    private boolean deleteActivityInStudents(String activityName) {
        boolean successFlag = studentData.deleteActivityFromStudets(activityName);
        if (!successFlag) {
            logger.warn("从学生活动表中删除活动：" + activityName + "失败");
        } else {
            logger.info("从学生活动表中删除活动：" + activityName + "成功");
        }
        return successFlag;
    }
}
