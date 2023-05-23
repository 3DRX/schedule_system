package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.BitMap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

@Disabled
public class BitMapTest {
    @Test
    void test1() {
        BitMap bitMap = new BitMap(1001);
        bitMap.set(10);
        bitMap.set(100);
        bitMap.set(1000);
        assertTrue(bitMap.get(10));
        assertTrue(bitMap.get(100));
        assertTrue(bitMap.get(1000));
        assertFalse(bitMap.get(1));
    }

    @Test
    void test2() {
        BitMap bitMap = new BitMap(1000);
        bitMap.set(1001);
    }
}
