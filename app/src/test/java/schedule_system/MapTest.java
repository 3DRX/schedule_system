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
        KList<Location> res = null;
        try {
            res = mapData.pathFromXtoY("学三公寓", "物美超市");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            res.stream()
                    .forEach(e -> System.out.println(e));
        }
    }

    // @Test
    void testPassingLocations() {
        MapData mapData = new MapData();
        KList<Location> res = null;
        String[] locations = { "学三公寓", "物美超市" };
        try {
            res = mapData.pathPassingLocations(locations, "教三楼");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != null) {
            res.stream()
                    .forEach(e -> System.out.println(e));
        }
    }
}
