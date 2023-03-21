package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.Location;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

public class ConflictCheckTest {
    @Test
    void testConflictsWith_1() {
        // TODO
        // 创建两个新的Course
        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        // 显然，这里两个课程是冲突的，所以有下面这一行代码。
        assertTrue(course1.conflictsWith(course2));
        // 我们要测试的函数就是这个conflictsWith，
        // 此处如果这个函数的判断有问题，测试用例就不会通过。


    }

    @Test
    void testConflictsWith_2() {
        Course course1 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(6, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertFalse(course1.conflictsWith(course2));
    }
//    @Test
//    void testTimeOverlapsWith_1() {
//        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
//                new Location("location2",0,0,new Location[0]));
//        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
//                new Location("location2",0,0,new Location[0]));
//        assertTrue(course1.timeOverlapsWith(course2));
//    }
//    void testWeekOverlaps_1() {
//        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
//                new Location("location2",0,0,new Location[0]));
//        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
//                new Location("location2",0,0,new Location[0]));
//        assertTrue(course1.weekOverlaps(course2));
//    }
}
