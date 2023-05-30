package schedule_system.utils;

import java.util.Arrays;

/**
 * 地图节点，用在 {@link MapData} 中
 *
 * {@link #location} 本节点的地点
 * {@link #isBuilding} 是否是可被作为起点、终点的节点
 * {@link #adj} 邻接表
 */
public class MapNode {
    private Location location;
    private boolean isBuilding;
    private AdjData[] adj;

    public MapNode(Location location, boolean isBuilding) {
        this.location = location;
        this.isBuilding = isBuilding;
        this.adj = new AdjData[0];
    }

    /**
     * 返回节点到另一个节点的距离，若输入是自身，返回0
     * 若本节点与另一节点不相连，返回INTMAX
     * 
     * @param nodeName 另一节点的名称
     * @return 距离
     */
    public int distenceToAdj(String nodeName) {
        if (this.getLocation().getName().equals(nodeName)) {
            return 0;
        }
        AdjData res = Arrays.stream(adj)
                .filter(e -> e.name().equals(nodeName))
                .findFirst()
                .orElse(null);
        return res != null ? res.weight() : Integer.MAX_VALUE;
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
                res += "<";
                res += this.adj[i].weight();
                res += ">";
                res += ", ";
            } else {
                res += this.adj[i].name();
                res += "<";
                res += this.adj[i].weight();
                res += ">";
            }
        }
        res += "]";
        return res;
    }

    @Deprecated
    private void addAdj(AdjData adjData) {
        AdjData[] newList = Arrays.copyOf(this.adj, this.adj.length + 1);
        newList[newList.length - 1] = adjData;
        this.adj = newList;
    }

    /**
     * @return 本节点的地点
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return 本节点是否是可被作为起点、终点的节点
     */
    public boolean isBuilding() {
        return isBuilding;
    }

    /**
     * @return 本节点的邻接表
     */
    public AdjData[] getAdj() {
        return adj;
    }
}
