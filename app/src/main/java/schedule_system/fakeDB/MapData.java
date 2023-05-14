package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import schedule_system.utils.*;

public class MapData {
    final private String path = "src/main/resources/map.json";
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Logger logger = LoggerFactory.getLogger(MapData.class);

    // private MapNode[] nodes;
    private KMap<String, MapNode> nodes;

    public MapData() {
        nodes = new KMap<>();
        Arrays.stream(this.readMap())
                // .forEach(e -> System.out.println(e.toString()));
                .forEach(x -> nodes.put(x.getLocation().getName(), x));
        this.logger.info("Reading map data from " + path);
    }

    public boolean isValidLocation(String locationName) {
        if (this.nodes.get(locationName) != null) {
            logger.info("Location " + locationName + " is valid.");
            return true;
        } else {
            logger.info("Location " + locationName + " is invalid.");
            return false;
        }
    }

    // Dijkstra's algorithm
    public KList<Location> pathFromXtoY(String x, String y) {
        // TODO:
        // 1. 生成最短距离的时候不能遍历全图
        // 2. 把下面HashSet替换成自己的数据结构

        // init
        // x 点到每个点的距离
        KMap<String, Integer> distence = new KMap<>();
        distence.put(x, 0);
        // 未访问的节点（这里value无意义，就是我懒得再写个Set了）
        // KMap<String, Integer> visitedNodes = new KMap<>();
        HashSet<String> visitedNodes = new HashSet<String>();
        // 广度有限遍历的队列
        KList<MapNode> queue = new KList<>(MapNode.class);
        queue.add(this.nodes.get(x));
        while (queue.size() != 0
                && visitedNodes.size() < this.nodes.size()) {
            MapNode currentNode = queue.popLeft();
            // logger.info("visiting: " + currentNode.getLocation().getName());
            for (AdjData e : currentNode.getAdj()) {
                int weight = distence.get(currentNode.getLocation().getName()) + e.weight();
                if (distence.get(e.name()) == null || distence.get(e.name()) > weight) {
                    // 如果通过当前节点访问其邻接节点比原来的距离近，则更新最短距离。
                    distence.put(e.name(), weight);
                    // logger.info("put " + e.name() + ", of distence " + weight);
                }
                if (!visitedNodes.contains(e.name())) {
                    // logger.info("Never visit " + e.name() + " before, add it into queue.");
                    // 如果邻接节点没有访问过，入队
                    queue.add(this.nodes.get(e.name()));
                    visitedNodes.add(e.name());
                }
            }
        }
        // logger.info("==========" + distence.getKeyArray(String.class).length);
        // logger.debug("node number: " + this.nodes.size());
        // get result
        KList<Location> res = new KList<>(Location.class);
        MapNode currentNode = this.nodes.get(y);
        // logger.debug("add node: " + currentNode.getLocation().getName());
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
                // logger.info(adj.name());
                // logger.info("adjDistence: " + adjDistence);
                // logger.info("adjWeight: " + adjWeight);
                // logger.info("thisDistence: " +
                // distence.get(currentNode.getLocation().getName()));
                Boolean flag = adjDistence + adjWeight == distence.get(currentNode.getLocation().getName());
                if (flag) {
                    filtered = adj;
                    break;
                }
            }
            String theNode = filtered.name();
            temp++;
            // logger.debug("add node: " + theNode);
            res.add(0, this.nodes.get(theNode).getLocation());
            if (theNode.equals(x)) {
                break;
            }
            currentNode = this.nodes.get(theNode);
        }
        return res;
    }

    @Deprecated
    private void add(MapNode node) {
        this.nodes.put(node.getLocation().getName(), node);
    }

    public MapNode[] getNodes() {
        return Arrays.stream(nodes.getKeyArray(String.class))
                .map(k -> nodes.get(k))
                .toArray(size -> new MapNode[size]);
    }

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
