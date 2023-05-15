package schedule_system;

import org.junit.jupiter.api.Test;

import schedule_system.utils.KList;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;

@Disabled
public class KListSortTest {

    @Test
    void test1() {
        KList<Integer> list = new KList<>(Integer.class);
        list.add(1);
        list.add(5);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(1);
        list.add(4);
        list.add(1);
        list.add(7);
        list.add(1);
        list.add(8);
        list.add(1);
        list.add(0);
        list.quickSort((a, b) -> {
            return a < b;
        });
        Arrays.stream(list.toArray())
                .forEach(System.out::println);
    }
}
