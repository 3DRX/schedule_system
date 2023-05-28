package schedule_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.MapData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.EventTime;
import schedule_system.utils.Location;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NavigateController {
    // 日志控制器
    private final Logger logger = LoggerFactory.getLogger(NavigateController.class);

    @Autowired
    MapData mapData;
    @Autowired
    StudentData studentData;
    @Autowired
    EventData eventData;

    @PostMapping("/navigateToClass")
    @CrossOrigin
    public Location[] navigate(@RequestBody NavigateRecord input) {
        if (!mapData.isValidLocation(input.start())) {
            logger.warn("导航失败：起点 " + input.start() + " 不存在");
            return null;
        }
        if (!mapData.isValidLocation(input.end())) {
            logger.warn("导航失败：终点 " + input.end() + " 不存在");
            return null;
        }
        logger.info("导航开始，起点: " + input.start() + " 终点: " + input.end());
        return mapData.pathFromXtoY(input.start(), input.end()).toArray();
    }

    @PostMapping("/navigateToEvents")
    @CrossOrigin
    public Location[] navigateToEvents(@RequestBody EventNavRecord input) {
        if (!mapData.isValidLocation(input.start())) {
            logger.warn("导航失败：起点 " + input.start() + " 不存在");
            return null;
        }
        for (String location : input.locations()) {
            if (!mapData.isValidLocation(location)) {
                logger.warn("导航失败：途径地点 " + location + " 不存在");
                return null;
            }
        }
        logger.info("导航开始，途径: " + Arrays.toString(input.locations()) + " 起点: " + input.start());
        try {
            return mapData.pathPassingLocations(input.locations(), input.start()).toArray();
        } catch (Exception e) {
            logger.warn("导航失败：无法到达");
            e.printStackTrace();
            return new Location[0];
        }
    }

    @GetMapping("/getLocations")
    @CrossOrigin
    public String[] getLocations(String eventName, String studentName) {
        final EventTime eventTime = eventData
                .getEventByName(eventName + "," + studentName)
                .getTime();
        return Arrays.stream(studentData.getStudentById(studentName).getEvents())
                .map(e -> eventData.getEventByName(e + "," + studentName))
                .filter(e -> e.getTime().equals(eventTime))
                .map(e -> e.getLocationName())
                .toArray(String[]::new);
    }
}

record NavigateRecord(String start, String end) {
}

record EventNavRecord(String start, String[] locations) {
}
