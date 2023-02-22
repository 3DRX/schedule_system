package schedule_system.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.Login;
import schedule_system.theUser;
import schedule_system.records.UserRecord;

/**
 * LoginController
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public UserRecord loginValidation(
            @RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "password", defaultValue = "") String password) {
        Login login = new Login();
        for (theUser user : login.getInputUsers()) {
            if (id.equals(user.getId()) && password.equals(user.getPassword())) {
                return new UserRecord(true, user.isAdmin());
            }
        }
        return new UserRecord(false, false);
    }
}
