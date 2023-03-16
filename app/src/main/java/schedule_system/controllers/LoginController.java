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

/**
 * LoginController
 */
@RestController
@CrossOrigin(maxAge = 3600)
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class); // 日志控制器
    @Autowired
    UserData userData; // 用户数据控制器

    /**
     * 判断用户名和密码是否正确并返回
     * 
     * @param iptUser
     * @return
     */
    @PostMapping("/login")
    public UserRecord loginValidation(
            @RequestBody theUser iptUser) {
        for (theUser user : userData.allUsers()) {
            if (iptUser.getId().equals(user.getId()) && iptUser.getPassword().equals(user.getPassword())) {
                logger.info("登陆成功");
                return new UserRecord(true, user.isAdmin());
            }
        }
        logger.info("用户名或密码错误");
        return new UserRecord(false, false);
    }
}

record UserRecord(boolean isValid, boolean isAdmin) {
}
