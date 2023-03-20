package schedule_system.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class TimeController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class); // 日志控制器
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @GetMapping("/time/{id}")
    @CrossOrigin
    public SseEmitter streamDateTime(@PathVariable String id) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> logger.info("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> logger.info("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> logger.info("SseEmitter got error:", ex));

        executor.execute(() -> {
            int i = 0;
            while (i < 1000) {
                i++;
                try {
                    sseEmitter
                            .send(LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern(id + ", " + i)));
                    sleep(5, sseEmitter);
                } catch (IOException e) {
                    e.printStackTrace();
                    sseEmitter.completeWithError(e);
                }
            }
            sseEmitter.complete();
        });
        logger.info("Controller exists");
        return sseEmitter;
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
