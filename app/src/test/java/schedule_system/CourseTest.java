package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.Location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

@Disabled("Disabled CourseTest")
public class CourseTest {
    @Test
    void testCourse1() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(1, 10, 11, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertTrue(flag);
    }

    @Test
    void testCourse2() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(1, 10, 21, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertFalse(flag);
    }

    @Test
    void testCourse3() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(10, 1, 11, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertFalse(flag);
    }

    @Test
    void testCourse4() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(-1, 10, 11, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertFalse(flag);
    }

    @Test
    void testCourse5() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(-10, -2, 11, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertFalse(flag);
    }

    @Test
    void testCourse6() {
        Course courseTest;
        boolean flag = true;
        try {
            ClassTime classTime = new ClassTime(1, 8, 3);
            courseTest = new Course(1, 10, 9, classTime, classTime, "testCourse", new Location("location1",0,0,new Location[0]));
        } catch (IllegalArgumentException e) {
            flag = false;
            System.out.println(e);
        }
        assertFalse(flag);
    }
}
