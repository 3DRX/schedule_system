package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.CourseData;
import schedule_system.utils.Course;
import schedule_system.utils.KMap;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void test1() {
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        System.out.println(map.get("java").getLocation().getName());
    }
}
