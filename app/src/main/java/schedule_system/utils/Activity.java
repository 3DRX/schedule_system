package schedule_system.utils;

import java.util.Arrays;

/**
 * 课外活动
 *
 * {@link #name} 活动名称
 * {@link #participants} 参与者
 * {@link #startWeek} 开始周
 * {@link #endWeek} 结束周
 * {@link #time} 活动时间
 * {@link #location} 活动地点
 */
public class Activity {
    private String name;
    private String[] participants;
    private int startWeek;
    private int endWeek;
    private ActivityTime time;
    private String location;

    public Activity(
            final String name,
            final String[] participants,
            final int startWeek,
            final int endWeek,
            final ActivityTime time,
            final String location) {
        this.name = name;
        this.participants = participants;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.time = time;
        this.location = location;
    }

    /**
     * 活动是否在该时间发生
     * 
     * @param index 时间索引
     * @return 活动是否在该时间发生
     */
    public boolean takesPlaceAt(int index) {
        int week = ClassTime.weekOfIndex(index);
        int day = ClassTime.dayOfIndex(index);
        int timeInt = ClassTime.timeOfIndex(index);
        if (!(week >= startWeek && week <= endWeek)) {
            return false;
        }
        if (!(day == time.getDay())) {
            return false;
        }
        if (!(timeInt >= time.getTime() && timeInt <= time.getTime() + time.getDuration())) {
            return false;
        }
        return true;
    }

    /**
     * @return 活动占用的时间 {@link BitMap}
     */
    public BitMap getOccupiedTime() {
        BitMap occupiedTime = new BitMap(ClassTime.getMaxIndex());
        for (int week = startWeek; week <= endWeek; week++) {
            int day = this.time.getDay();
            int time = this.time.getTime();
            for (int j = 0; j < this.time.getDuration(); j++) {
                occupiedTime.set(ClassTime.realTimeToIndex(week, day, time + j));
            }
        }
        return occupiedTime;
    }

    /**
     * 删除参与者
     * 
     * @param participant 参与者姓名
     */
    public void removeParticipant(String participant) {
        this.participants = Arrays.stream(this.participants)
                .filter(p -> !p.equals(participant))
                .toArray(String[]::new);
    }

    /**
     * @param other 另一个活动
     * @return 两个活动是否时间重叠
     */
    public boolean timeOverlapsWith(Activity other) {
        // TODO
        return false;
    }

    /**
     * @return 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 活动参与者
     */
    public String[] getParticipants() {
        return participants;
    }

    /**
     * @return 活动开始周
     */
    public int getStartWeek() {
        return startWeek;
    }

    /**
     * @return 活动结束周
     */
    public int getEndWeek() {
        return endWeek;
    }

    /**
     * @return 活动时间 {@link SystemTime}
     */
    public SystemTime getTime() {
        return time;
    }

    /**
     * @return 活动地点名称
     */
    public String getLocationName() {
        return location;
    }
}
