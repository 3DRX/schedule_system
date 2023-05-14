package schedule_system.utils;

/**
 * Location
 */
public class Location {

    private String name;
    private int x;
    private int y;

    public Location(String name, int x, int y) {
        // TODO: 检查输入合法性
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.name + "(" + x + "," + y + ")";
    }

    public boolean equals(Location location) {
        return this.name.equals(location.name);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return this.name;
    }
}
