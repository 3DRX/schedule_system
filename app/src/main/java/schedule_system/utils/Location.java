package schedule_system.utils;

/**
 * Location
 */
public class Location {

    private String name;
    private int x;
    private int y;
    private Location[] adjList;
    private double[] adjLength;

    public Location(String name, int x, int y, Location[] adj) {
        // TODO: 检查输入合法性
        this.name = name;
        this.x = x;
        this.y = y;
        this.adjList = adj;
        for (int i = 0; i < this.adjList.length; i++) {
            this.adjLength[i] = getWeight(this.adjList[i]);
        }
    }

    private double getWeight(Location loc) {
        return Math.sqrt(Math.pow(Math.abs(loc.x - this.x), 2) + Math.pow(Math.abs(loc.y - this.y), 2));
    }

    public Location[] getAdj() {
        return this.adjList;
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
