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

    public Location getLocation(String locationName) {
        return this.nodes.get(locationName).getLocation();
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
     * return the path of shortest distence from x passing all locations
     * 
     * @param locations
     * @param x
     * @return
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
            // System.out.println("start+++++++++++++++++++++++++++");
            // System.out.println("unvisited: ");
            // System.out.print("\t");
            // Arrays.stream(unvisited.getKeyArray(String.class))
            //         .forEach(e -> System.out.print(e + " "));
            // System.out.println();
            // System.out.println("currentNode: " + currentLocation);
            // System.out.println("start+++++++++++++++++++++++++++");
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
            // add path to nearestLocation in res
            KList<Location> path = this.pathFromXtoY(currentLocation, nearestLocation);
            path.popLeft();
            path.popRight();
            for (Location e : path) {
                res.add(e);
            }
            currentLocation = nearestLocation;
            unvisited.remove(nearestLocation);
            // System.out.println("end============================");
            // System.out.println("unvisited: ");
            // System.out.print("\t");
            // Arrays.stream(unvisited.getKeyArray(String.class))
            //         .forEach(e -> System.out.print(e + " "));
            // System.out.println();
            // System.out.println("currentNode: " + currentLocation);
            // System.out.println("end============================");
        }
        res.add(this.nodes.get(x).getLocation());
        return res;
    }

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
                Boolean flag = adjDistence + adjWeight == distence.get(currentNode.getLocation().getName());
                if (flag) {
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
