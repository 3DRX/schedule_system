package schedule_system.utils;

/**
 * 日志类，用于解析日志文件
 *
 * {@link #date} 日志日期
 * {@link #time} 日志时间
 * {@link #level} 日志级别
 * {@link #module} 日志模块
 * {@link #message} 日志信息
 */
public class Log {
    private String date; // YYYY-MM-DD
    private String time; // HH:MM:SS
    private String level; // INFO, WARN, ERROR
    private String module;
    private String message;

    /**
     * @param line 日志文件中的一行
     */
    public Log(String line) {
        line = line.trim();
        if (line.length() == 0) {
            throw new RuntimeException("输入行为空");
        }
        if (line.charAt(4) != '-' || line.charAt(7) != '-' || line.charAt(10) != ' ') {
            throw new RuntimeException("输入行不是日志格式");
        }
        String[] leftAndRight = line.split("\\)- ");
        this.message = leftAndRight.length == 2 ? leftAndRight[1] : "";
        String[] parts = leftAndRight[0].split(" ");
        this.date = parts[0];
        this.time = parts[1].split(",")[0];
        this.level = parts[2];
        this.module = parts[3];
    }

    /**
     * @return 日志日期
     */
    public String getDate() {
        return date;
    }

    /**
     * @return 日志时间
     */
    public String getTime() {
        return time;
    }

    /**
     * @return 日志级别
     */
    public String getLevel() {
        return level;
    }

    /**
     * @return 日志模块
     */
    public String getModule() {
        return module;
    }

    /**
     * @return 日志信息
     */
    public String getMessage() {
        return message;
    }
}
