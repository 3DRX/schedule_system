package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.StudentData;
import schedule_system.fakeDB.UserData;

@RestController
@CrossOrigin
public class DeleteStudentController {
    private final Logger logger = LoggerFactory.getLogger(DeleteStudentController.class); // 日志控制器
    @Autowired
    StudentData studentData;
    @Autowired
    UserData userData;

    @PostMapping("/deleteStudent")
    public boolean deleteStudent(String id) {
        boolean flag = studentData.removeStudent(id);
        flag &= userData.removeStudent(id);
        if (flag) {
            logger.info("删除学生 " + id + " 成功");
        } else {
            logger.warn("删除学生 " + id + " 失败");
        }
        return flag;
    }
}
