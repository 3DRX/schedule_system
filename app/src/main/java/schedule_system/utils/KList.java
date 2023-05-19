package schedule_system.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;

/**
 * @param <T>
 */
public class KList<T> extends AbstractList<T> {
    private T[] list;

    public KList(Class<T> t) {
        this.list = (T[]) Array.newInstance(t, 0);
    }

    /**
     * 快速排序
     * 
     * @param rule lambda 表达式，如果a应排在b前面，返回true
     */
    public KList<T> quickSort(CompareRule<T> rule) {
        pQuickSort(rule, 0, list.length - 1);
        return this;
    }

    private void pQuickSort(CompareRule<T> rule, int left, int right) {
        if (left < right) {
            int pi = partition(rule, left, right);
            pQuickSort(rule, left, pi - 1);
            pQuickSort(rule, pi + 1, right);
        }
    }

    private int partition(CompareRule<T> rule, int left, int right) {
        T pivot = this.list[right];
        int i = (left - 1);
        for (int j = left; j < right; j++) {
            if (rule.calc(this.list[j], pivot)) {
                i++;
                T swapTemp = this.list[i];
                this.list[i] = this.list[j];
                this.list[j] = swapTemp;
            }
        }
        T swapTemp = this.list[i + 1];
        this.list[i + 1] = this.list[right];
        this.list[right] = swapTemp;
        return i + 1;
    }

    public int size() {
        return this.list.length;
    }

    public boolean add(T t) {
        list = Arrays.copyOf(list, list.length + 1);
        list[list.length - 1] = t;
        return true;
    }

    public void add(int index, T t) {
        list = Arrays.copyOf(list, list.length + 1);
        for (int i = list.length - 1; i > index; i--) {
            list[i] = list[i - 1];
        }
        list[index] = t;
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

    public T remove(int index) {
        if (index < 0 || index >= list.length) {
            return null;
        }
        T res = list[index];
        for (int i = index; i < list.length - 1; i++) {
            list[i] = list[i + 1];
        }
        list = Arrays.copyOf(list, list.length - 1);
        return res;
    }

    @Override
    public T[] toArray() {
        return Arrays.copyOf(this.list, this.list.length);
    }
}
