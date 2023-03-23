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

    private MapNode[] nodes;

    public MapData() {
        this.nodes = readMap();
    }

    public void add(MapNode node) {
        // TODO: change to private
        this.nodes = Arrays.copyOf(this.nodes, this.nodes.length + 1);
        this.nodes[this.nodes.length - 1] = node;
    }

    public MapNode[] getNodes() {
        return this.nodes;
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
