package schedule_system.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.UserData;
import schedule_system.utils.theUser;
import schedule_system.records.UserRecord;

/**
 * LoginController
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class LoginController {
    private UserData userData = new UserData();

    @PostMapping("/login")
    public UserRecord loginValidation(
            @RequestBody theUser iptUser) {
        for (theUser user : userData.allUsers()) {
            if (iptUser.getId().equals(user.getId()) && iptUser.getPassword().equals(user.getPassword())) {
                return new UserRecord(true, user.isAdmin());
            }
        }
        return new UserRecord(false, false);
    }
}
