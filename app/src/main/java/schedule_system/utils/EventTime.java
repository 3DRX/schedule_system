package schedule_system.utils;

public class EventTime {
    private int week;
    private int day;
    private int time;

    public EventTime(int week, int day, int time) {
        // check input
        if (!ClassTime.isValidTime(week, day, time)) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.week = week;
        this.day = day;
        this.time = time;
    }

    public int toIndex() {
        return ClassTime.realTimeToIndex(week, day, time);
    }

    public boolean leq(EventTime other) {
        return this.toIndex() <= other.toIndex();
    }

    public boolean geq(EventTime other) {
        return this.toIndex() >= other.toIndex();
    }

    public boolean equals(EventTime other) {
        return this.toIndex() == other.toIndex();
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }

    public int getTime() {
        return time;
    }
}
