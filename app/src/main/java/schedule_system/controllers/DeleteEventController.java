package schedule_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.StudentData;

@RestController
@CrossOrigin
public class DeleteEventController {
    // 日志控制器
    private final Logger logger = LoggerFactory.getLogger(
            ReferEventController.class);

    @Autowired
    EventData eventData; // 临时事物控制器
    @Autowired
    StudentData studentData; // 学生数据控制器

    @GetMapping("/deleteEvent")
    public boolean deleteEvent(String studentName, String eventName) {
        // check if the student exists
        if (!studentData.isStudent(studentName)) {
            logger.info("Student " + studentName + " does not exist.");
            return false;
        }
        // check if the event exists
        if (!eventData.containsEvent(eventName + "," + studentName)) {
            logger.info("Event " + eventName + " does not exist.");
            return false;
        }
        // delete the event
        studentData.deleteEventFromStudent(eventName, studentName);
        eventData.deleteEvent(eventName + "," + studentName);
        return true;
    }
}
