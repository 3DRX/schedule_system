package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.StudentData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

@Disabled
public class StudentDataTest {
    @Test
    void test1() {
        StudentData studentData = new StudentData();
        assertTrue(studentData.getStudentById("002").getName().equals("002"));
    }

    @Test
    void test2() {
        StudentData studentData = new StudentData();
        Arrays.stream(studentData.getStudentClasses())
                .forEach(e -> System.out.println(e.getName()));
    }
}
