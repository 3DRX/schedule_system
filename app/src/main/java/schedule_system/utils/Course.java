package schedule_system.utils;

/**
 * Course
 */
public class Course {

    private int startTime;
    private int lastTime;
    private String name;
    private int id;
    private Location location;

    public int getStartTime() {
        return startTime;
    }

    public int getLastTime() {
        return lastTime;
    }

    public boolean setTime(int startTime, int lastTime) {
        if (lastTime <= 0 || lastTime >= 4) {
            return false;
        } else if (startTime < 8 || startTime + lastTime > 20) {
            return false;
        } else {
            this.startTime = startTime;
            this.lastTime = lastTime;
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
