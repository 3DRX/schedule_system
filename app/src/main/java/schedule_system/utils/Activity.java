package schedule_system.utils;

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
        // TODO: check input
        this.name = name;
        this.participants = participants;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.time = time;
        this.location = location;
    }

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

    public boolean timeOverlapsWith(Activity other) {
        // TODO
        return false;
    }

    public String getName() {
        return name;
    }

    public String[] getParticipants() {
        return participants;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public SystemTime getTime() {
        return time;
    }

    public String getLocationName() {
        return location;
    }
}
