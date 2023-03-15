package schedule_system.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.UserData;
import schedule_system.utils.theUser;

@RestController
@CrossOrigin(maxAge = 3600)
public class GetStudentListController {
    @Autowired
    UserData userData;

    @GetMapping("/studentList")
    public String[] getStudentList() {
        theUser[] users = userData.allUsers();
        ArrayList<String> li = new ArrayList<>();
        for (theUser user : users) {
            if (!user.isAdmin()) {
                li.add(user.getId());
            }
        }
        String[] res = new String[li.size()];
        li.toArray(res);
        return res;
    }
}
