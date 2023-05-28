package schedule_system.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import schedule_system.utils.Log;

@RestController
@CrossOrigin
public class LogController {
    final private String path = "log";

    @PostMapping("/getLogs")
    public Log[] getLogs(final String date, final String level, final String time) {
        return Arrays.stream(
                readLogs(date.equals(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd")
                        .format(LocalDateTime.now()))
                                ? path + "/sys.log"
                                : path + "/sys." + date + ".0.log"))
                .filter(log -> {
                    if (level.equals("INFO")) {
                        return true;
                    } else if (level.equals("WARN")) {
                        return log.getLevel().equals("WARN") || log.getLevel().equals("ERROR");
                    } else if (level.equals("ERROR")) {
                        return log.getLevel().equals("ERROR");
                    } else {
                        return false;
                    }
                })
                .filter(log -> {
                    int hour = Integer.parseInt(log.getTime().split(":")[0]);
                    String[] times = time.split("-");
                    int start = Integer.parseInt(times[0]);
                    int end = Integer.parseInt(times[1]);
                    return hour >= start && hour <= end;
                })
                .toArray(Log[]::new);
    }

    private Log[] readLogs(String fileName) {
        System.out.println("读取日志文件：" + fileName);
        try {
            return Arrays.stream(
                    new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(fileName))).split("\n"))
                    .map(line -> {
                        try {
                            return new Log(line);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(e -> e != null)
                    .toArray(Log[]::new);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Log[0];
        }
    }
}
