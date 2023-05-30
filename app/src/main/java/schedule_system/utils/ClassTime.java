package schedule_system.utils;

/**
 * 课程时间 合法的时间范围：周一到周日，8:00到20:00
 */
public class ClassTime extends SystemTime {

    public ClassTime(int day, int time, int duration) {
        if (day < 1 || day > getDayInWeek()) {
            throw new IllegalArgumentException("ClassTime: day 不合法");
        }
        if (time < getStartHourOfCourse() || time > getLastHourOfCourse()) {
            throw new IllegalArgumentException("ClassTime: time 不合法");
        }
        if (duration < 1 || duration > 3) {
            throw new IllegalArgumentException("ClassTime: duration 不合法");
        }
        this.day = day;
        this.time = time;
        this.duration = duration;
    }
}
