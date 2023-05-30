package schedule_system.utils;

import java.util.Arrays;

/**
 * 位图
 */
public class BitMap {
    private int[] bits;
    private int size;

    /**
     * @param size 位长
     */
    public BitMap(int size) {
        this.size = size;
        bits = new int[size / 32 + 1];
        this.unsetAll();
    }

    /**
     * @param n 将第 n 位设置为 1
     */
    public void set(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] |= (1 << offset);
    }

    /**
     * 将所有位设置为 1
     */
    public void setAll() {
        Arrays.fill(bits, 0xffffffff);
    }

    /**
     * 将所有位设置为 0
     */
    public void unsetAll() {
        Arrays.fill(bits, 0);
    }

    /**
     * @param other 另一个位图
     * @return 两个位图是否有重叠
     */
    public boolean overlaps(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        for (int i = 0; i < bits.length; i++) {
            if ((bits[i] & other.bits[i]) != 0)
                return true;
        }
        return false;
    }

    /**
     * @param n 将第 n 位设置为 0
     */
    public void unset(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] &= ~(1 << offset);
    }

    /**
     * @param n 将第 n 位取反
     */
    public void flip(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] ^= (1 << offset);
    }

    /**
     * 将所有位取反
     */
    public void flipAll() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = ~bits[i];
        }
    }

    /**
     * @param n 位索引
     * @return 第 n 位是否为 1
     */
    public boolean get(int n) {
        int index = n / 32;
        int offset = n % 32;
        return (bits[index] & (1 << offset)) != 0;
    }

    /**
     * @param 将第 n 位设置为 0
     */
    public void clear(int n) {
        int index = n / 32;
        int offset = n % 32;
        bits[index] &= ~(1 << offset);
    }

    /**
     * 将所有位设置为 0
     */
    public void clear() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }
    }

    /**
     * @return 位长
     */
    public int size() {
        return size;
    }

    /**
     * @param other 另一个位图
     * @return 两个位图按位与产生的新位图
     */
    public BitMap and(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] & other.bits[i];
        }
        return result;
    }

    /**
     * @param other 另一个位图
     * @return 两个位图按位或产生的新位图
     */
    public BitMap or(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] | other.bits[i];
        }
        return result;
    }

    /**
     * @param other 另一个位图
     * @return 两个位图按位异或产生的新位图
     */
    public BitMap xor(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] ^ other.bits[i];
        }
        return result;
    }

    /**
     * @return 位图按位取反产生的新位图
     */
    public BitMap not() {
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = ~bits[i];
        }
        return result;
    }

    /**
     * @param other 另一个位图
     * @return 两个位图是否相等
     */
    public boolean equals(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != other.bits[i])
                return false;
        }
        return true;
    }
}
