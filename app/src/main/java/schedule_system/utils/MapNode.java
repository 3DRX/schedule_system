
package schedule_system.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MapNode {
    private Location location;
    // 是否是可被作为起点、终点的节点
    private boolean isBuilding;
    // 邻接表
    private AdjData[] adj;

    public MapNode(Location location, boolean isBuilding) {
        this.location = location;
        this.isBuilding = isBuilding;
        this.adj = new AdjData[0];
    }

    /**
     * 返回节点到另一个节点的距离，若输入是自身，返回0
     * 
     * @param nodeName
     * @return
     */
    public double distenceTo(String nodeName) {
        if (this.getLocation().getName().equals(nodeName)) {
            return 0;
        }
        AdjData[] res = (AdjData[]) Arrays.stream(adj)
                .filter(e -> e.name().equals(nodeName))
                .collect(Collectors.toList())
                .toArray();
        if (res.length == 0) {
            return Double.MAX_VALUE;
        } else {
            return res[0].weight();
        }
    }

    @Override
    public String toString() {
        String res = this.location.getName()
                + ":("
                + this.location.getX()
                + ", "
                + this.location.getY()
                + ")\t\t[";
        for (int i = 0; i < this.adj.length; i++) {
            if (i != this.adj.length - 1) {
                res += this.adj[i].name();
                res += ", ";
            } else {
                res += this.adj[i].name();
            }
        }
        res += "]";
        return res;
    }

    public void addAdj(AdjData adjData) {
        // TODO: change to private
        AdjData[] newList = Arrays.copyOf(this.adj, this.adj.length + 1);
        newList[newList.length - 1] = adjData;
        this.adj = newList;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isBuilding() {
        return isBuilding;
    }

    public AdjData[] getAdj() {
        return adj;
    }
}
