package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.*;

/**
 * 地图控制
 * 操作一组 {@link MapNode} 对象
 */
public class MapData {
    final private String path = "src/main/resources/map.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger = LoggerFactory.getLogger(MapData.class);

    private KMap<String, MapNode> nodes;

    /**
     * 从文件读取地图信息
     */
    public MapData() {
        nodes = new KMap<>();
        Arrays.stream(this.readMap())
                .forEach(x -> nodes.put(x.getLocation().getName(), x));
        this.logger.info("从 " + path + " 读取地图数据成功");
    }

    /**
     * 按名称查找地点
     * 
     * @param locationName 地点名称
     * @return 该地点的实例
     */
    public Location getLocation(String locationName) {
        return this.nodes.get(locationName).getLocation();
    }

    /**
     * 判断地点是否存在
     * 
     * @param locationName 地点名称
     * @return 是否存在该地点
     */
    public boolean isValidLocation(String locationName) {
        if (this.nodes.get(locationName) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得从 x 出发到所有点的最短距离
     * 
     * @param x 起点
     * @return 从 x 出发到所有点的最短距离
     */
    private KMap<String, Integer> distence(String x) {
        // x 点到每个点的距离
        KMap<String, Integer> distence = new KMap<>();
        distence.put(x, 0);
        // 已访问的节点（这里value无意义，当Set用）
        KMap<String, Integer> visitedNodes = new KMap<>();
        KList<MapNode> queue = new KList<>(MapNode.class);
        queue.add(this.nodes.get(x));
        while (queue.size() != 0
                && visitedNodes.size() < this.nodes.size()) {
            MapNode currentNode = queue.popLeft();
            visitedNodes.put(currentNode.getLocation().getName(), 0);
            for (AdjData e : currentNode.getAdj()) {
                int weight = distence.get(currentNode.getLocation().getName()) + e.weight();
                if (distence.get(e.name()) == null || distence.get(e.name()) > weight) {
                    // 如果通过当前节点访问其邻接节点比原来的距离近，则更新最短距离。
                    distence.put(e.name(), weight);
                }
                if (visitedNodes.get(e.name()) == null) {
                    // 如果邻接节点没有访问过，入队
                    queue.add(this.nodes.get(e.name()));
                }
            }
        }
        return distence;
    }

    /**
     * 从 x 出发途径若干地点最后返回 x 的最短路径
     * 
     * @param locations 途径地点名称数组
     * @param x         起点名称
     * @return 从 x 出发途径若干地点最后返回 x 的最短路径
     */
    public KList<Location> pathPassingLocations(String[] locations, String x) {
        // check input
        if (!this.isValidLocation(x))
            throw new IllegalArgumentException();
        for (String location : locations)
            if (!this.isValidLocation(location))
                throw new IllegalArgumentException();
        final KMap<String, Integer> unvisited = new KMap<>();
        Arrays.stream(locations)
                .forEach((e) -> unvisited.put(e, 0));
        final KList<Location> res = new KList<>(Location.class);
        String currentLocation = x;
        while (unvisited.size() != 0) {
            res.add(this.nodes.get(currentLocation).getLocation());
            int minDistence = Integer.MAX_VALUE;
            String nearestLocation = null;
            KMap<String, Integer> distence = this.distence(currentLocation);
            for (String location : unvisited.getKeyArray(String.class)) {
                if (distence.get(location) != null
                        && distence.get(location) < minDistence) {
                    minDistence = distence.get(location);
                    nearestLocation = location;
                }
            }
            KList<Location> path = null;
            try {
                path = this.pathFromXtoY(currentLocation, nearestLocation);
            } catch (Exception e) {
                try {
                    path = this.pathFromXtoY(nearestLocation, currentLocation).reverse();
                } catch (Exception e1) {
                    throw new RuntimeException("No path from " + currentLocation + " to " + nearestLocation);
                }
            }
            path.popLeft();
            path.popRight();
            for (Location e : path) {
                res.add(e);
            }
            currentLocation = nearestLocation;
            unvisited.remove(nearestLocation);
        }
        KList<Location> path = null;
        try {
            path = this.pathFromXtoY(currentLocation, x);
        } catch (Exception e) {
            try {
                path = this.pathFromXtoY(x, currentLocation).reverse();
            } catch (Exception e1) {
                throw new RuntimeException("No path from " + currentLocation + " to " + x);
            }
        }
        for (Location e : path) {
            res.add(e);
        }
        return res;
    }

    /**
     * 从 x 到 y 的最短路径
     * 
     * @param x 起点
     * @param y 终点
     * @return 从 x 到 y 的最短路径
     */
    public KList<Location> pathFromXtoY(String x, String y) {
        KMap<String, Integer> distence = this.distence(x);
        KList<Location> res = new KList<>(Location.class);
        MapNode currentNode = this.nodes.get(y);
        res.add(currentNode.getLocation());
        int temp = 0;
        while (temp < 100) {
            AdjData filtered = null;
            for (AdjData adj : currentNode.getAdj()) {
                MapNode adjNode = this.nodes.get(adj.name());
                int adjDistence = distence.get(adjNode.getLocation().getName());
                int adjWeight = 0;
                for (AdjData i : adjNode.getAdj()) {
                    if (i.name().equals(currentNode.getLocation().getName())) {
                        adjWeight = i.weight();
                    }
                }
                if (adjDistence + adjWeight == distence.get(currentNode.getLocation().getName())) {
                    filtered = adj;
                    break;
                }
            }
            String theNode = filtered.name();
            temp++;
            res.add(0, this.nodes.get(theNode).getLocation());
            if (theNode.equals(x)) {
                break;
            }
            currentNode = this.nodes.get(theNode);
        }
        return res;
    }

    /**
     * 添加节点
     * 
     * @param node 节点
     */
    @Deprecated
    private void add(MapNode node) {
        this.nodes.put(node.getLocation().getName(), node);
    }

    /**
     * 获得所有节点
     * 
     * @return 所有节点
     */
    public MapNode[] getNodes() {
        return Arrays.stream(nodes.getKeyArray(String.class))
                .map(k -> nodes.get(k))
                .toArray(size -> new MapNode[size]);
    }

    /**
     * 从文件读取所有节点
     * 
     * @return 所有节点
     */
    private MapNode[] readMap() {
        MapNode[] readMap = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            readMap = new Gson().fromJson(reader, MapNode[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readMap;
    }

    /**
     * 将所有节点写入文件
     * 
     * @param nodes 所有节点
     * @return 是否成功写入文件
     */
    @Deprecated
    private boolean writeMap(MapNode[] nodes) {
        File file = new File(path);
        String res = gson.toJson(nodes);
        boolean successFlag = true;
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            successFlag = false;
            e.printStackTrace();
        }
        return successFlag;
    }
}
