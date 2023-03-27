package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.MapData;
import schedule_system.utils.MapNode;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void mapTest() {
        MapData mapData = new MapData();
        for (MapNode mapNode : mapData.getNodes()) {
            System.out.println(mapNode.toString());
        }
    }
}
