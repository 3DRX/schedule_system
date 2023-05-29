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
        if (iptUser.getId().equals("admin") && iptUser.getPassword().equals("admin")) {
            return new UserRecord(true, true);
        }
        for (theUser user : userData.students()) {
            if (iptUser.getId().equals(user.getId()) && iptUser.getPassword().equals(user.getPassword())) {
                logger.info(iptUser.getId() + " 登陆成功");
                return new UserRecord(true, false);
            }
        }
        logger.info(iptUser.getId() + " 登陆失败，用户名或密码错误");
        return new UserRecord(false, false);
    }
}

record UserRecord(boolean isValid, boolean isAdmin) {
}
