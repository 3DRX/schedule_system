package schedule_system.utils;

/**
 * ClassTime
 */
public class ClassTime {
    private int week;
    private int day;
    private int time;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
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

    public boolean setTime(int time) {
        if (time >= 8 && time <= 20) {
            this.time = time;
            return true;
        } else {
            return false;
        }
    }
}
