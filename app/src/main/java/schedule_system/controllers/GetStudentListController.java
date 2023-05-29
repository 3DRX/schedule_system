package schedule_system.controllers;

import java.util.Arrays;

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
        return Arrays.stream(userData.students())
                .map(theUser::getId)
                .toArray(String[]::new);
    }
}
