package schedule_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.fakeDB.MapData;
import schedule_system.utils.Location;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NavigateController {
    private final Logger logger = LoggerFactory.getLogger(NavigateController.class); // 日志控制器

    @Autowired
    MapData mapData;

    @PostMapping("/navigateToClass")
    @CrossOrigin
    public Location[] navigate(@RequestBody NavigateRecord input) {
        if (!mapData.isValidLocation(input.start())) {
            logger.warn("导航失败：起点不存在");
            return null;
        }
        if (!mapData.isValidLocation(input.end())) {
            logger.warn("导航失败：终点不存在");
            return null;
        }
        return mapData.pathFromXtoY(input.start(), input.end()).toArray();
    }

    @PostMapping("/navigateToEvents")
    @CrossOrigin
    public Location[] navigateToEvents(@RequestBody EventNavRecord input) {
        if (!mapData.isValidLocation(input.start())) {
            logger.warn("导航失败：起点不存在");
            return null;
        }
        for (String location : input.locations()) {
            if (!mapData.isValidLocation(location)) {
                logger.warn("导航失败：终点不存在");
                return null;
            }
        }
    }
}

record NavigateRecord(String start, String end) {
}

record EventNavRecord(String start, String[] locations) {
}
