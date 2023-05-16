package schedule_system.utils;

/**
 * ClassTime
 */
public class ClassTime {
    private int day;
    private int time;
    private int duration;

    public ClassTime(int day, int time, int duration) {
        if (!setDay(day)) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (!setTime(time)) {
            throw new IllegalArgumentException("输入不合法");
        }
        if ((duration >= 1 && duration <= 3) && duration + time <= 20) {
            setDuration(duration);
        } else {
            throw new IllegalArgumentException("输入不合法");
        }
    }

    /**
     * Convert real time to time index
     * 
     * @param week
     * @param day
     * @param time
     * @return
     */
    public static int realTimeToIndex(int week, int day, int time) {
        // check input
        if (week < 1 || week > 20 || day < 1 || day > 5 || time < 8 || time > 20) {
            throw new IllegalArgumentException("输入不合法");
        }
        return (week - 1) * 50 + (day - 1) * 5 + time - 8;
    }

    public static boolean isValidTime(int week, int day, int time) {
        return week >= 1 && week <= 20 && day >= 1 && day <= 5 && time >= 8 && time <= 20;
    }

    /**
     * @return the maximum value of time index
     */
    public static int getMaxIndex() {
        return 20 * 5 * 10;
    }

    /**
     * 判断是否与另一个时间有重叠
     *
     * @param ClassTime
     * @return boolean
     */
    public boolean overlaps(ClassTime classTime) {
        boolean res;
        // System.out.printf("this: day%d time%d duration%d\n", this.day, this.time,
        // this.duration);
        // System.out.printf("that: day%d time%d duration%d\n", classTime.day,
        // classTime.time, classTime.duration);
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
        // if (res) {
        // System.out.println("class time overlaps");
        // } else {
        // System.out.println("class time did not overlap");
        // }
        return res;
    }

    public boolean covers(int start, int end, int day) {
        return time <= start && time + duration >= end && this.day == day;
    }

    private boolean setTime(int time) {
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

    private boolean setDuration(int duration) {
        if (duration <= 0 || duration > 3) {
            return false;
        } else {
            this.duration = duration;
            return true;
        }
    }

    public int getDay() {
        return day;
    }

    private boolean setDay(int day) {
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
