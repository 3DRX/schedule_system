package schedule_system.utils;

public abstract class SystemTime {
    int day;
    int time;
    int duration;

    /**
     * Convert real time to time index
     * 
     * @param week
     * @param day
     * @param time
     * @return
     */
    public final static int realTimeToIndex(int week, int day, int time) {
        // check input
        if (!isValidTime(week, day, time)) {
            throw new IllegalArgumentException("输入不合法");
        }
        return (week - 1) * getHourInWeek()
                + (day - 1) * getHourInDay()
                + time - firstIndexOfHour();
    }

    public final static int weekOfIndex(int index) {
        return index / getHourInWeek() + 1;
    }

    public final static int dayOfIndex(int index) {
        return index % getHourInWeek() / getHourInDay() + 1;
    }

    public final static int timeOfIndex(int index) {
        return index % getHourInDay() + firstIndexOfHour();
    }

    public final static boolean isValidTime(int week, int day, int time) {
        return week >= 1 && week <= getWeekInSemester()
                && day >= 1 && day <= getDayInWeek()
                && time >= firstIndexOfHour() && time <= lastIndexOfHour();
    }

    public final static int getMaxIndex() {
        return getDayInWeek() * getHourInDay() * getWeekInSemester();
    }

    public final static int getHourInWeek() {
        return getHourInDay() * getDayInWeek();
    }

    public final static int lastIndexOfHour() {
        return firstIndexOfHour() + getHourInDay() - 1;
    }

    public final static int firstIndexOfHour() {
        return 7;
    }

    public final static int getHourInDay() {
        return 14;
    }

    public final static int getDayInWeek() {
        return 7;
    }

    public final static int getWeekInSemester() {
        return 20;
    }

    public final static int getStartHourOfCourse() {
        return 8;
    }

    public final static int getLastHourOfCourse() {
        return 19;
    }

    public int getDay() {
        return this.day;
    }

    public int getTime() {
        return this.time;
    }

    public int getDuration() {
        return this.duration;
    }

    /**
     * 判断是否与另一个时间有重叠
     */
    public boolean overlaps(final SystemTime classTime) {
        boolean res;
        if (this.day == classTime.day) {
            if (this.time == classTime.time) {
                res = true;
            } else if (this.time < classTime.time && this.time + this.duration - 1 >= classTime.time) {
                res = true;
            } else if (classTime.time < this.time && classTime.time + classTime.duration - 1 >= this.time) {
                res = true;
            } else {
                res = false;
            }
        } else {
            res = false;
        }
        return res;
    }

    public boolean covers(final int start, final int end, final int day) {
        return time <= start && time + duration >= end && this.day == day;
    }
}
