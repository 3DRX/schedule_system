package schedule_system.utils;

public class Log {
    private String date; // YYYY-MM-DD
    private String time; // HH:MM:SS
    private String level; // INFO, WARN, ERROR
    private String module;
    private String message;

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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLevel() {
        return level;
    }

    public String getModule() {
        return module;
    }

    public String getMessage() {
        return message;
    }
}
