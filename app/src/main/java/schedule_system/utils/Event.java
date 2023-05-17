package schedule_system.utils;

public class Event {
    private String name;
    private EventTime time;
    private String location;

    public Event(String name, EventTime time, String location) {
        // check input
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (time == null) {
            throw new IllegalArgumentException("输入不合法");
        }
        if (location == null || location.length() == 0) {
            throw new IllegalArgumentException("输入不合法");
        }
        this.name = name;
        this.time = time;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public EventTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }
}
