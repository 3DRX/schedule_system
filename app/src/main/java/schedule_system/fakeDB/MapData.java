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
        MapNode[] readNodes = readMap();
        nodes = new KMap<>();
        Arrays.stream(readNodes)
                .forEach(x -> nodes.put(x.getLocation().getName(), x));
    }

    public void add(MapNode node) {
        // TODO: change to private
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

    public boolean writeMap(MapNode[] nodes) {
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
