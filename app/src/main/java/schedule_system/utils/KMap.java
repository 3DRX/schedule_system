package schedule_system.utils;

import java.lang.reflect.Array;

public class KMap<K, V> {
    private static final int defautLength = 16;
    private static final double defaultFactor = 0.75;
    private int threshold;
    private int size;
    // 扩容次数
    private int resize;
    private MyEntry<K, V>[] table;

    public KMap() {
        this.table = new MyEntry[defautLength];
        this.threshold = (int) (defautLength * defaultFactor);
        this.size = 0;
    }

    private int index(K key, int capacity) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        int index = index(key, this.table.length);
        MyEntry<K, V> entry = table[index];
        while (entry != null) {
            if ((entry.getKey() == key || entry.getKey().equals(key))) {
                entry.setValue(value);
                return;
            }
            entry = entry.getNext();
        }
        add(index, key, value);
    }

    private void add(int index, K key, V value) {
        MyEntry<K, V> entry = new MyEntry<>(key, value, table[index]);
        table[index] = entry;
        if (size++ >= threshold) {
            resize(table.length * 2);
        }
    }

    private void resize(int capacity) {
        if (capacity <= table.length) {
            return;
        }
        MyEntry<K, V>[] newTabe = new MyEntry[capacity];
        for (int i = 0; i < table.length; i++) {
            MyEntry<K, V> old = table[i];
            while (old != null) {
                MyEntry<K, V> next = old.getNext();
                int index = index(old.getKey(), capacity);
                old.setNext(newTabe[index]);
                newTabe[index] = old;
                old = next;
            }
        }
        this.table = newTabe;
        this.threshold = (int) (this.table.length * KMap.defaultFactor);
        this.resize++;
    }

    public V get(K k) {
        if (k == null) {
            return null;
        }
        MyEntry<K, V> entry = getEntry(k);
        return entry == null ? null : entry.getValue();
    }

    private MyEntry<K, V> getEntry(K key) {
        MyEntry<K, V> entry = table[index(key, this.table.length)];
        while (entry != null) {
            if (entry.getKey().hashCode() == key.hashCode()
                    && (entry.getKey() == key
                            || entry.getKey().equals(key))) {
                return entry;
            }
            entry = entry.getNext();
        }
        return entry;
    }

    public void remove(K key) {
        if (key == null) {
            return;
        }
        int index = index(key, this.table.length);
        MyEntry<K, V> pre = null;
        MyEntry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.getKey().hashCode() == key.hashCode()
                    && (entry.getKey() == key
                            || entry.getKey().equals(key))) {
                if (pre == null) {
                    table[index] = entry.getNext();
                } else {
                    pre.setNext(entry.getNext());
                }
                size--;
                return;
            }
            pre = entry;
            entry = entry.getNext();
        }
    }

    public boolean containKey(K key) {
        if (key == null) {
            return false;
        }
        return getEntry(key) != null;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        this.size = 0;
    }

    @SuppressWarnings({ "unchecked" })
    public K[] getKeyArray(Class<K> c) {
        K[] res = (K[]) Array.newInstance(c, this.size);
        int index = 0;
        for (MyEntry<K, V> entry : this.table) {
            while (entry != null) {
                res[index] = entry.getKey();
                entry = entry.getNext();
                index++;
            }
        }
        return res;
    }

    public final class MyEntry<K, V> {
        private K k;
        private V v;
        private MyEntry<K, V> next;

        public MyEntry() {
        }

        public MyEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public MyEntry(K k, V v, MyEntry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        public K getKey() {
            return k;
        }

        public V getValue() {
            return v;
        }

        public void setValue(V v) {
            this.v = v;
        }

        public MyEntry<K, V> getNext() {
            return next;
        }

        public void setNext(MyEntry<K, V> next) {
            this.next = next;
        }
    }
}
