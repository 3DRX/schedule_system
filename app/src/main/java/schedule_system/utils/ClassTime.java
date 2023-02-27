package schedule_system.utils;

/**
 * ClassTime
 */
public class ClassTime {
    private int day;
    private int time;
    private int duration;

    public ClassTime(int day, int time, int duration) {
        setDay(day);
        setTime(time);
        setDuration(duration);
        // TODO: 检查课程结束时间是否超过20:00
    }

    public boolean setTime(int time) {
        if (time >= 8 && time <= 20) {
            this.time = time;
            return true;
        } else {
            return false;
        }
    }

    public int getDuration() {
        return duration;
    }

    public boolean setDuration(int duration) {
        if (duration <= 0 || duration > 3) {
            this.duration = duration;
            return true;
        } else {
            return false;
        }
    }

    public int getDay() {
        return day;
    }

    public boolean setDay(int day) {
        if (day >= 1 && day <= 7) {
            this.day = day;
            return true;
        } else {
            return false;
        }
    }

    public int getTime() {
        return time;
    }
}
