package schedule_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.UserData;
import schedule_system.utils.KList;
import schedule_system.utils.theUser;

@RestController
@CrossOrigin(maxAge = 3600)
public class GetStudentListController {
    @Autowired
    UserData userData;

    @GetMapping("/studentList")
    public String[] getStudentList() {
        theUser[] users = userData.allUsers();
        KList<String> li = new KList<>(String.class);
        for (theUser user : users) {
            if (!user.isAdmin()) {
                li.add(user.getId());
            }
        }
        return li.toArray();
    }
}
