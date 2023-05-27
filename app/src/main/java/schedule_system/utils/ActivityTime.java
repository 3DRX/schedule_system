package schedule_system.utils;

public class ActivityTime extends SystemTime {
    public ActivityTime(int day, int time, int duration) {
        if (day < 1 || day > getDayInWeek()) {
            throw new IllegalArgumentException("ActivityTime: day 不合法");
        }
        if (time < firstIndexOfHour() || time > lastIndexOfHour()) {
            throw new IllegalArgumentException("ActivityTime: time 不合法");
        }
        if (duration != 1) {
            throw new IllegalArgumentException("ActivityTime: duration 不合法");
        }
        this.day = day;
        this.time = time;
        this.duration = duration;
    }
}
