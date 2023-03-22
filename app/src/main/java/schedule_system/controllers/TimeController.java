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

import schedule_system.fakeDB.CourseData;
import schedule_system.fakeDB.StudentData;
import schedule_system.utils.Course;
import schedule_system.utils.Student;

/**
 * 模拟系统时间控制器
 */
@RestController
public class TimeController {
    private final Logger logger = LoggerFactory.getLogger(TimeController.class); // 日志控制器
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Autowired
    StudentData studentData;
    @Autowired
    CourseData courseData;

    /**
     * 收到GET请求后发送SSE
     *
     * id: 人名
     * start: 开始index（index定义见getSimRes的注释）
     * 
     * @param id
     * @param start
     * @return
     */
    @GetMapping("/time/{id}/{start}")
    @CrossOrigin
    public SseEmitter streamDateTime(@PathVariable String id, @PathVariable int start) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> logger.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> logger.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> logger.info("SseEmitter completed with error"));
        executor.execute(() -> {
            int i = start;
            while (i <= 1200) {
                i++;
                ResponseRecord res = getSimRes(id, i);
                try {
                    sleep(1, sseEmitter);
                    sseEmitter.send(res.toString());
                } catch (IOException e) {
                    sseEmitter.completeWithError(e);
                    break;
                }
            }
            sseEmitter.complete();
        });
        logger.info("Controller exists");
        return sseEmitter;
    }

    /**
     * 获得指定 id 和指定 index 限定下的通知信息
     * id: 人名
     * index: 时间轴索引（第一周周一早上8点的index是1，以此类推）
     * 
     * @param id
     * @param index
     * @return
     */
    private ResponseRecord getSimRes(String id, int index) {
        Student theStudent = studentData.getStudentById(id);
        System.out.println(id + ": " + index);
        if (theStudent == null) {
            logger.error("can't find student simulating: " + id);
            return new ResponseRecord("", "", 0, 0, index++);
        } else {
            for (String courseName : theStudent.getCourses()) {
                Course theCourse = courseData.getCourseByName(courseName);
                if (theCourse.atIndex(index + 1)) {
                    return new ResponseRecord(
                            courseName,
                            theCourse.getLocation().getName(),
                            theCourse.getLocation().getX(),
                            theCourse.getLocation().getY(),
                            index++);
                } else {
                }
            }
        }
        return new ResponseRecord("", "", 0, 0, index++);
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
        int x,
        int y,
        int index) {
    @Override
    public String toString() {
        return this.courseName + "," + this.location + "," + x + "," + y + "," + index;
    }
}
