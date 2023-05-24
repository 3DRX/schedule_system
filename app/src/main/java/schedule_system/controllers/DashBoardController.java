package schedule_system.controllers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.CellContent;
import schedule_system.utils.KList;

@RestController
@CrossOrigin
public class DashBoardController {
    // 日志控制器
    private final Logger logger = LoggerFactory.getLogger(DashBoardController.class);
    @Autowired
    CourseData courseData;
    @Autowired
    ActivityData activityData;
    @Autowired
    EventData eventData;
    @Autowired
    StudentData studentData;

    @GetMapping("/getDashBoard")
    public CellContent[] getDashBoard(int week, int day, String studentName) {
        KList<CellContent> res = new KList<>(CellContent.class);
        // 获得该时间的课程
        Arrays.stream(courseData.getCourseByDay(week, day))
                .filter(course -> Arrays.stream(studentData.getStudentById(studentName).getCourses())
                        .anyMatch(c -> c.equals(course.getName())))
                .forEach(e -> res.add(
                        new CellContent(
                                e.getName(),
                                Arrays.stream(studentData.getStudentsArray())
                                        .filter(s -> Arrays.stream(s.getCourses())
                                                .anyMatch(c -> c.equals(e.getName())))
                                        .map(s -> s.getName())
                                        .toArray(String[]::new),
                                e.getLocationName(), false)));
        // 获得该时间的活动
        Arrays.stream(activityData.getActivityByDay(week, day))
                .filter(activity -> Arrays.stream(activity.getParticipants())
                        .anyMatch(a -> a.equals(studentName)))
                .forEach(e -> res.add(new CellContent(
                        e.getName(),
                        e.getParticipants(),
                        e.getLocationName(), true)));
        // 获得该时间的事件
        String[] participants = new String[1];
        participants[0] = studentName;
        Arrays.stream(eventData.getEventByDay(week, day))
                .forEach(e -> res.add(new CellContent(
                        e.getName(),
                        participants,
                        e.getLocationName(), true)));
        return res.toArray();
    }
}
