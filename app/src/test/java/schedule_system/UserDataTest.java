package schedule_system;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.UserData;

import org.junit.jupiter.api.Disabled;

@Disabled
public class UserDataTest {

    @Test
    void test1() {
        UserData userData = new UserData();
        Arrays.stream(userData.students())
                .forEach(e -> System.out.println(e.getId()));
    }
}
