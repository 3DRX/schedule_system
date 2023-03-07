package schedule_system.utils;

/**
 * Location
 */
public class Location {
    // TODO: 未完成

    private String name;
    private int x;
    private int y;

    public boolean equals(Location location) {
        return this.name.equals(location.name) && this.x == location.x && this.y == location.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return this.name;
    }
}
