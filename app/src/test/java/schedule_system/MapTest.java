package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.fakeDB.MapData;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

class MapTest {

    @Test
    void mapTest() {
        MapData mapData = new MapData();
        Arrays.stream(mapData.getNodes())
                .forEach(e -> System.out.println(e));
    }
}
