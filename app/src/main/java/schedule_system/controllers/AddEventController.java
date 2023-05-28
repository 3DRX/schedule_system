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
            logger.warn("Student " + newEvent.student() + " does not exist.");
            return false;
        }
        if (!mapData.isValidLocation(newEvent.location())) {
            logger.warn("Location " + newEvent.location() + " does not exist.");
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
                    .append("Time ")
                    .append(newEvent.week())
                    .append("-")
                    .append(newEvent.day())
                    .append("-")
                    .append(newEvent.time())
                    .append(" is invalid.")
                    .toString());
        }
        // check if time conflicts with student schedule
        if (studentData.isOccupied(
                newEvent.student(),
                time.toIndex())) {
            logger.warn(new StringBuilder()
                    .append("Time ")
                    .append(newEvent.week())
                    .append("-")
                    .append(newEvent.day())
                    .append("-")
                    .append(newEvent.time())
                    .append(" conflicts with student ")
                    .append(newEvent.student())
                    .append("'s schedule.")
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
                    .append("Event ")
                    .append(newEvent.name())
                    .append(" of ")
                    .append(newEvent.student())
                    .append(" already exists.")
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
