package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.CourseData;
import schedule_system.utils.Course;
import schedule_system.utils.KMap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

@Disabled
public class KMapTest {
    @Test
    void test1() {
        // 长度相同
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        assertTrue(map.size() == courseData.allCourses().length);
    }

    @Test
    void test2() {
        // 对应元素相同
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        assertTrue(map.get("java").getLocation() == courseData.getCourseByName("java").getLocation());
    }

    @Test
    void test3() {
        // 删除元素后长度减少
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        map.remove("java");
        assertTrue(map.size() + 1 == courseData.allCourses().length);
    }

    @Test
    void test4() {
        // 获得所有Key（即遍历KMap）
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        for (String str : map.getKeyArray(String.class)) {
            System.out.println(str);
        }
        assertTrue(map.getKeyArray(String.class).length == courseData.allCourses().length);
    }

    @Test
    void test5() {
        // clear 之后 size() 应为0
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        map.clear();
        assertTrue(map.size() == 0);
    }

    @Test
    void test6() {
        // get 不存在的 key，应该返回null
        CourseData courseData = new CourseData();
        KMap<String, Course> map = new KMap<>();
        for (Course course : courseData.allCourses()) {
            map.put(course.getName(), course);
        }
        assertTrue(map.get("a name that dosen't exist") == null);
    }
}
