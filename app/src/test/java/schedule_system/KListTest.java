package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.KList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;

@Disabled
public class KListTest {

    @Test
    void test1() {
        // 测试添加元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.get(0), 1);
        assertEquals(list.get(1), 2);
    }

    @Test
    void test2() {
        // 测试获取元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.get(0), 1);
        assertEquals(list.get(1), 2);
    }

    @Test
    void test3() {
        // 测试弹出左端元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.popLeft(), 1);
        assertEquals(list.popLeft(), 2);
    }

    @Test
    void test4() {
        // 测试查看左端元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.peekLeft(), 1);
        assertEquals(list.peekLeft(), 1);
    }

    @Test
    void test5() {
        // 测试弹出右端元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.popRight(), 2);
        assertEquals(list.popRight(), 1);
    }

    @Test
    void test6() {
        // 测试查看右端元素
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(2);
        assertEquals(list.peekRight(), 2);
        assertEquals(list.peekRight(), 2);
    }
}
