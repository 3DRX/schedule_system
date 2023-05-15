package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.CourseData;
import schedule_system.utils.Course;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

@Disabled
public class CourseDataTest {
    @Test
    void test1() {
        CourseData courseData = new CourseData();
        Course course = courseData.getCourseByName("java");
        assertTrue(course.getName().equals("java"));
    }

    @Test
    void test2() {
        CourseData courseData = new CourseData();
        Arrays.stream(courseData.allCourses())
                .forEach(e -> System.out.println(e.getName()));
    }
}
