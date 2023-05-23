package schedule_system.utils;

public class EventTime {
    private int week;
    private int day;
    private int time;

    public EventTime(int week, int day, int time) throws IllegalArgumentException {
        // check input
        if (!ClassTime.isValidTime(week, day, time)) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.week = week;
        this.day = day;
        this.time = time;
    }

    public EventTime(String week, String day, String time) throws IllegalArgumentException {
        // convert string to int
        int weekInt = Integer.parseInt(week);
        int dayInt = Integer.parseInt(day);
        int timeInt = Integer.parseInt(time);
        // check input
        if (!ClassTime.isValidTime(weekInt, dayInt, timeInt)) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.week = weekInt;
        this.day = dayInt;
        this.time = timeInt;
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append(week)
                .append("-")
                .append(day)
                .append("-")
                .append(time)
                .toString();
    }
}
