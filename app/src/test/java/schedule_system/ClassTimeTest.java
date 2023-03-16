package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.ClassTime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

@Disabled("Disabled ClassTimeTest")
class ClassTimeTest {

    @Test
    void testClassTime1() {
        ClassTime classTimeUnderTest;
        boolean success = true;
        try {
            classTimeUnderTest = new ClassTime(1, 8, 3);
        } catch (IllegalArgumentException e) {
            success = false;
            System.out.println(e);
        }
        assertTrue(success);
    }

    @Test
    void testClassTime2() {
        ClassTime classTimeUnderTest;
        boolean success = true;
        try {
            classTimeUnderTest = new ClassTime(20, 8, 3);
        } catch (IllegalArgumentException e) {
            success = false;
            System.out.println(e);
        }
        assertFalse(success);
    }

    @Test
    void testClassTime3() {
        ClassTime classTimeUnderTest;
        boolean success = true;
        try {
            classTimeUnderTest = new ClassTime(5, 19, 3);
        } catch (IllegalArgumentException e) {
            success = false;
            System.out.println(e);
        }
        assertFalse(success);
    }

    @Test
    void testClassTime4() {
        ClassTime classTimeUnderTest;
        boolean success = true;
        try {
            classTimeUnderTest = new ClassTime(5, 19, 1);
        } catch (IllegalArgumentException e) {
            success = false;
            System.out.println(e);
        }
        assertTrue(success);
    }
}
