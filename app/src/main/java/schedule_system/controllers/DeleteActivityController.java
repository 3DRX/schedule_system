package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.StudentData;

@RestController
@CrossOrigin
public class DeleteActivityController {
    private final Logger logger = LoggerFactory.getLogger(DeleteActivityController.class); // 日志控制器
    @Autowired
    StudentData studentData;

    @DeleteMapping("/deleteActivity")
    public boolean deleteActivity(String activityName, String studentName) {
        boolean flag = studentData.deleteActivityFromStudet(activityName, studentName);
        logger.info(new StringBuilder()
                .append("从 ")
                .append(studentName)
                .append(" 的日程中删除课程 ")
                .append(activityName)
                .append(flag ? " 成功" : " 失败")
                .toString());
        return flag;
    }
}
