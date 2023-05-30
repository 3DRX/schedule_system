package schedule_system.utils;

/**
 * 课外活动时间 合法的时间范围：周一到周日，6:00到22:00
 */
public class ActivityTime extends SystemTime {
    /**
     * @param day      周几
     * @param time     几点
     * @param duration 持续几个小时
     */
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
