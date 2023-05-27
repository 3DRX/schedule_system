package schedule_system.controllers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import schedule_system.fakeDB.ActivityData;
import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.EventData;
import schedule_system.fakeDB.MapData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Activity;
import schedule_system.utils.ClassTime;
import schedule_system.utils.Course;
import schedule_system.utils.Event;
import schedule_system.utils.Location;
import schedule_system.utils.Student;

/**
 * 模拟系统时间控制器
 */
@RestController
@CrossOrigin
public class TimeController {
    private final Logger logger = LoggerFactory.getLogger(TimeController.class); // 日志控制器
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Autowired
    StudentData studentData;
    @Autowired
    CourseData courseData;
    @Autowired
    EventData eventData;
    @Autowired
    MapData mapData;
    @Autowired
    ActivityData activityData;

    /**
     * 收到GET请求后发送SSE
     *
     * id: 人名
     * start: 开始index（从0开始）
     * 
     * @param id
     * @param start
     * @return
     */
    @GetMapping("/time")
    public SseEmitter streamDateTime(String id, int start) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> logger.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> logger.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> logger.info("SseEmitter completed with error"));
        if (start >= ClassTime.getMaxIndex()) {
            logger.error("simulation start index out of range");
            throw new IllegalArgumentException("simulation start index out of range");
        }
        executor.execute(() -> {
            int i = start;
            while (i < ClassTime.getMaxIndex()) {
                ResponseRecord res = getSimRes(id, i + 1);
                try {
                    sleep(1, sseEmitter);
                    sseEmitter.send(res.toString());
                } catch (IOException e) {
                    sseEmitter.completeWithError(e);
                    break;
                }
                i++;
            }
            sseEmitter.complete();
        });
        logger.info("Controller exists");
        return sseEmitter;
    }

    /**
     * 获得指定 id 和指定 index 限定下的通知信息
     * id: 人名
     * index: 时间轴索引（第一周周一早上8点的index是0，以此类推）
     * 
     * @param id
     * @param index
     * @return
     */
    private ResponseRecord getSimRes(final String id, int index) {
        Student theStudent = studentData.getStudentById(id);
        if (theStudent == null) {
            logger.error("can't find student simulating: " + id);
            return new ResponseRecord("", "", index++, true);
        }
        if (!studentData.isOccupied(id, index + 1)) {
            // 如果有临时事物
            for (String eventName : theStudent.getEvents()) {
                Event theEvent = eventData.getEventByName(eventName + "," + id);
                if (theEvent.takesPlaceAt(index + 1)) {
                    logger.info("event: " + eventName + " takes place at " + index);
                    Location theLocation = mapData.getLocation(theEvent.getLocationName());
                    return new ResponseRecord(
                            eventName,
                            theLocation.getName(),
                            index++,
                            false);
                }
            }
            return new ResponseRecord("", "", index++, true);
        }
        // 如果有课
        for (String courseName : theStudent.getCourses()) {
            Course theCourse = courseData.getCourseByName(courseName);
            if (theCourse.atIndex(index + 1)) {
                logger.info("course: " + courseName + " takes place at " + index);
                return new ResponseRecord(
                        courseName,
                        theCourse.getLocationName(),
                        index++,
                        true);
            }
        }
        // 如果有课外活动
        for (String activityName : theStudent.getActivities()) {
            Activity theActivity = activityData.getActivityByName(activityName);
            if (theActivity.takesPlaceAt(index + 1)) {
                logger.info("ativity: " + activityName + " takes place at " + index);
                Location theLocation = mapData.getLocation(theActivity.getLocationName());
                return new ResponseRecord(
                        activityName,
                        theLocation.getName(),
                        index++,
                        false);
            }
        }
        return new ResponseRecord("", "", index++, true);
    }

    private void sleep(int seconds, SseEmitter sseEmitter) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            sseEmitter.completeWithError(e);
        }
    }
}

/**
 * SSE 发送给前端的数据格式
 */
record ResponseRecord(
        String courseName,
        String location,
        int index,
        boolean isCourse) {
    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.courseName)
                .append(",")
                .append(this.location)
                .append(",")
                .append(index)
                .append(",")
                .append(isCourse)
                .toString();
    }
}
