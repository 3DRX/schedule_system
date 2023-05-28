package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.MapData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Event;
import schedule_system.utils.EventTime;

@RestController
@CrossOrigin
public class AddEventController {
    // 日志控制器
    private final Logger logger = LoggerFactory.getLogger(
            AddEventController.class);

    @Autowired
    EventData eventData; // 临时事物控制器
    @Autowired
    StudentData studentData; // 学生数据控制器
    @Autowired
    MapData mapData; // 地图数据控制器

    @PostMapping("/addEvent")
    public boolean addEvent(@RequestBody EventRecord newEvent) {
        // check input
        if (!studentData.isStudent(newEvent.student())) {
            logger.warn("学生 " + newEvent.student() + " 不存在");
            return false;
        }
        if (!mapData.isValidLocation(newEvent.location())) {
            logger.warn("地点 " + newEvent.location() + " 不存在");
            return false;
        }
        EventTime time = null;
        try {
            time = new EventTime(
                    newEvent.week(),
                    newEvent.day(),
                    newEvent.time());
        } catch (Exception e) {
            logger.warn(new StringBuilder()
                    .append("时间 ")
                    .append(newEvent.week())
                    .append("-")
                    .append(newEvent.day())
                    .append("-")
                    .append(newEvent.time())
                    .append(" 不合法")
                    .toString());
        }
        // check if time conflicts with student schedule
        if (studentData.isOccupied(
                newEvent.student(),
                time.toIndex())) {
            logger.warn(new StringBuilder()
                    .append("时间 ")
                    .append(newEvent.week())
                    .append("-")
                    .append(newEvent.day())
                    .append("-")
                    .append(newEvent.time())
                    .append(" 与学生 ")
                    .append(newEvent.student())
                    .append(" 的时间安排冲突")
                    .toString());
            return false;
        }
        Event event = new Event(
                newEvent.name(),
                time,
                newEvent.location(),
                newEvent.student());
        // check if event exists
        if (eventData.containsEvent(newEvent.name() + "," + newEvent.student())) {
            logger.warn(new StringBuilder()
                    .append("学生 ")
                    .append(newEvent.name())
                    .append(" 的临时事物 ")
                    .append(newEvent.student())
                    .append(" 已经存在")
                    .toString());
            return false;
        }
        boolean successFlag = true;
        successFlag &= eventData.addEvent(event);
        successFlag &= studentData.addEventToStudent(
                newEvent.name(),
                newEvent.student());
        return successFlag;
    }
}

record EventRecord(
        String student,
        String name,
        String week,
        String day,
        String time,
        String location) {
}
