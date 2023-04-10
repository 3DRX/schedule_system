package schedule_system.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;

/**
 * KList
 */
public class KList<T> extends AbstractList<T> {
    private T[] list;

    public KList(Class<T> t) {
        this.list = (T[]) Array.newInstance(t, 0);
    }

    public int size() {
        return this.list.length;
    }

    public boolean add(T t) {
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = t;
        return true;
    }

    public T get(int i) {
        return list[i];
    }

    public T popLeft() {
        T res = list[0];
        list = Arrays.copyOfRange(list, 1, list.length);
        return res;
    }

    public T peekLeft() {
        return list[0];
    }

    public T popRight() {
        T res = list[list.length - 1];
        list = Arrays.copyOfRange(list, 0, list.length - 1);
        return res;
    }

    public T peekRight() {
        return list[list.length - 1];
    }
}
