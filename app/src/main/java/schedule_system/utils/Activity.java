package schedule_system.utils;

public class Activity {
    private String name;
    private String[] participants;
    private int startWeek;
    private int endWeek;
    private ClassTime time;
    private Location location;

    public Activity(
            final String name,
            final String[] participants,
            final int startWeek,
            final int endWeek,
            final ClassTime time,
            final Location location) {
        // TODO: check input
        this.name = name;
        this.participants = participants;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.time = time;
        this.location = location;
    }

    public boolean takesPlaceAt(int index) {
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

    public ClassTime getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }
}
