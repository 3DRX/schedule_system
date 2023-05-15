package schedule_system.utils;

@FunctionalInterface
public interface CompareRule<T> {

    /**
     * 如果a应排在b前面，返回true
     *
     * @param a
     * @param b
     * @return
     */
    boolean calc(T a, T b);
}
