package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.StudentData;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDataTest {
    @Test
    void test1() {
        StudentData studentData = new StudentData();
        studentData.getStudentsByClass("java");
    }
}
