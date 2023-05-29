package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.UserData;
import schedule_system.utils.theUser;

@RestController
@CrossOrigin
public class RegisterController {
    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    UserData userData;

    @PostMapping("/register")
    public boolean register(@RequestBody theUser newUser) {
        boolean flag = userData.addStudent(newUser);
        if (flag) {
            logger.info("注册成功：" + newUser.getId());
        } else {
            logger.warn("注册失败：" + newUser.getId());
        }
        return flag;
    }
}
