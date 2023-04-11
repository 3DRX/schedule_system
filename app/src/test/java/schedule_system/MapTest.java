package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.MapData;
import schedule_system.utils.KList;
import schedule_system.utils.Location;
import schedule_system.utils.MapNode;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

// @Disabled
class MapTest {

    void mapTest() {
        MapData mapData = new MapData();
        MapNode[] mapNodes = mapData.getNodes();
        Arrays.stream(mapNodes)
                .forEach(e -> System.out.println(e));
        System.out.println();
        System.out.println(mapNodes.length);
    }

    @Test
    void testShortestPath() {
        MapData mapData = new MapData();
        try {
            KList<Location> res = mapData.pathFromXtoY("57", "70");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
