package schedule_system.utils;

/**
 * 临时事物的时间
 *
 * {@link #week} 周
 * {@link #day} 天
 * {@link #time} 点
 */
public class EventTime {
    private int week;
    private int day;
    private int time;

    /**
     * @param week 周
     * @param day  天
     * @param time 点
     * @throws IllegalArgumentException
     */
    public EventTime(int week, int day, int time) throws IllegalArgumentException {
        if (!ClassTime.isValidTime(week, day, time)) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.week = week;
        this.day = day;
        this.time = time;
    }

    /**
     * @param week 周
     * @param day  天
     * @param time 点
     * @throws IllegalArgumentException
     */
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

    /**
     * 将时间转换为索引
     * 
     * @return 索引
     */
    public int toIndex() {
        return ClassTime.realTimeToIndex(week, day, time);
    }

    /**
     * @param other 另一个时间
     * @return 是否小于等于另一个时间
     */
    public boolean leq(EventTime other) {
        return this.toIndex() <= other.toIndex();
    }

    /**
     * @param other 另一个时间
     * @return 是否大于等于另一个时间
     */
    public boolean geq(EventTime other) {
        return this.toIndex() >= other.toIndex();
    }

    /**
     * @param other 另一个时间
     * @return 是否等于另一个时间
     */
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
