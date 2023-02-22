package schedule_system.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.Login;
import schedule_system.theUser;
import schedule_system.records.UserRecord;

/**
 * LoginController
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class LoginController {
    @PostMapping("/login")
    public UserRecord loginValidation(
            @RequestBody theUser iptUser
            ){
        Login login = new Login();
        System.out.println(iptUser.isAdmin());
        for (theUser user : login.getInputUsers()) {
            if (iptUser.getId().equals(user.getId()) && iptUser.getPassword().equals(user.getPassword())) {
                return new UserRecord(true, user.isAdmin());
            }
        }
        return new UserRecord(false, false);
    }
}
