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
        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertTrue(course1.conflictsWith(course2));
    }

    @Test
    // 课程名字不同，class Time 相同
    void testConflictsWith_2() {
        Course course1 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertTrue(course1.conflictsWith(course2));
    }

    @Test
    // clasTime冲突 上课周不同
    void testConflictsWith_3() {
        Course course1 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(6, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location1", 0, 0, new Location[0]));
        assertFalse(course1.conflictsWith(course2));
    }

    @Test
    // 课程名称冲突
    void testConflictsWith_4() {
        Course course1 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(6, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        assertTrue(course1.conflictsWith(course2));
    }

    @Test
    // 时间不相同
    void testTimeOverlapsWith_1() {
        Course course1 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(6, 10, 11, new ClassTime(1, 5, 2), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertFalse(course1.timeOverlapsWith(course2));
    }

    @Test
    // 时间和地点相同
    void testTimeOverlapsWith_2() {
        Course course1 = new Course(6, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(1, 5, 11, new ClassTime(1, 8, 2), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertTrue(course1.timeOverlapsWith(course2));
    }

    @Test
    // 时间重叠
    void testTimeOverlapsWith_3() {
        Course course1 = new Course(6, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(1, 5, 11, new ClassTime(1, 6, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location2", 0, 0, new Location[0]));
        assertTrue(course1.timeOverlapsWith(course2));
    }

    @Test
    // 上课周是重叠的
    void testWeekOverlaps_1() {
        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(1, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location1", 0, 0, new Location[0]));
        assertTrue(course1.weekOverlaps(course2));
    }

    @Test
    // 上课周是重叠的
    void testWeekOverlaps_2() {
        Course course1 = new Course(1, 3, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(3, 5, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location1", 0, 0, new Location[0]));
        assertTrue(course1.weekOverlaps(course2));
    }

    @Test
    // 上课周是不重叠
    void testWeekOverlaps_3() {
        Course course1 = new Course(1, 4, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        Course course2 = new Course(5, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course2",
                new Location("location1", 0, 0, new Location[0]));
        assertFalse(course1.weekOverlaps(course2));
    }

    @Test
    // 上课周数一样的
    void testWeekOverlaps_4() {
        Course course1 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location1", 0, 0, new Location[0]));
        Course course2 = new Course(1, 10, 11, new ClassTime(1, 8, 3), new ClassTime(1, 8, 3), "course1",
                new Location("location2", 0, 0, new Location[0]));
        assertTrue(course1.conflictsWith(course2));
    }
}
