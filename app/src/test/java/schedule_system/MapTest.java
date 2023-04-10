package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.MapData;
import schedule_system.utils.MapNode;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

class MapTest {

    @Test
    void mapTest() {
        MapData mapData = new MapData();
        MapNode[] mapNodes = mapData.getNodes();
        Arrays.stream(mapNodes)
                .forEach(e -> System.out.println(e));
        System.out.println();
        System.out.println(mapNodes.length);
    }
}
