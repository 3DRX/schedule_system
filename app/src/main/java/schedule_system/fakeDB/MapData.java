package schedule_system.fakeDB;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Map;

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

    // TODO
    // Dijkstra's algorithm
    public KList<Location> pathFromXtoY(String x, String y) {
        // init
        // x 点到每个点的距离
        KMap<String, Integer> distence = new KMap<>();
        distence.put(x, 0);
        // 未访问的节点（这里value无意义，就是我懒得再写个Set了）
        KMap<String, Integer> visitedNodes = new KMap<>();
        // 广度有限遍历的队列
        KList<MapNode> queue = new KList<>(MapNode.class);
        queue.add(this.nodes.get(x));
        while (queue.size() != 0
                && visitedNodes.getKeyArray(String.class).length != this.nodes.getKeyArray(String.class).length) {
            MapNode currentNode = queue.popLeft();
            visitedNodes.put(currentNode.getLocation().getName(), 0);
            logger.info("visiting: " + currentNode.getLocation().getName());
            for (AdjData e : currentNode.getAdj()) {
                int weight = distence.get(currentNode.getLocation().getName()) + e.weight();
                if (distence.get(e.name()) == null || distence.get(e.name()) > weight) {
                    // 如果通过当前节点访问其邻接节点比原来的距离近，则更新最短距离。
                    distence.put(e.name(), weight);
                    logger.info("put " + e.name() + ", of distence " + weight);
                }
                if (!visitedNodes.containKey(e.name())) {
                    logger.info("Never visit " + e.name() + " before, add it into queue.");
                    // 如果邻接节点没有访问过，入队
                    queue.add(this.nodes.get(e.name()));
                }
            }
        }
        // BUG: KMap 似乎有问题，会重复访问同一个节点。
        logger.info("==========" + distence.getKeyArray(String.class).length);
        // get result
        KList<Location> res = new KList<>(Location.class);
        MapNode currentNode = this.nodes.get(y);
        res.add(currentNode.getLocation());
        while (true) {
            String theNode = Arrays.stream(currentNode.getAdj())
                    .filter((adj) -> {
                        MapNode adjNode = this.nodes.get(adj.name());
                        int adjDistence = distence.get(adjNode.getLocation().getName());
                        int adjWeight = Arrays.stream(adjNode.getAdj())
                                .filter(i -> i.name().equals(currentNode.getLocation().getName()))
                                .findFirst()
                                .get()
                                .weight();
                        logger.info(adj.name());
                        logger.info("adjDistence: " + adjDistence);
                        logger.info("adjWeight: " + adjWeight);
                        logger.info("thisDistence: " + distence.get(currentNode.getLocation().getName()));
                        return adjDistence + adjWeight == distence.get(currentNode.getLocation().getName());
                    })
                    .findFirst()
                    .get() // this line throws NoSuchElementException
                    .name();
            logger.info("theNode: " + theNode);
            res.add(0, this.nodes.get(theNode).getLocation());
            if (theNode.equals(x)) {
                break;
            }
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
