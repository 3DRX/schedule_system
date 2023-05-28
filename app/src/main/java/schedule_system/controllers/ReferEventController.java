package schedule_system.controllers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Event;
import schedule_system.utils.KList;

@RestController
@CrossOrigin
public class ReferEventController {
    // 日志控制器
    private final Logger logger = LoggerFactory.getLogger(
            ReferEventController.class);

    @Autowired
    EventData eventData; // 临时事物控制器
    @Autowired
    StudentData studentData; // 学生数据控制器

    @GetMapping("/referEvents")
    public Event[] referEvents(String studentName) {
        // check if the student exists
        if (!studentData.isStudent(studentName)) {
            logger.info("学生 " + studentName + " 不存在");
            return new Event[0];
        }
        KList<Event> events = new KList<>(Event.class);
        Arrays.stream(studentData
                .getStudentById(studentName)
                .getEvents())
                .map(e -> eventData.getEventByName(e + "," + studentName))
                .forEach(events::add);
        return events
                .quickSort((e1, e2) -> e1
                        .getTime()
                        .leq(e2.getTime()))
                .toArray();
    }
}
