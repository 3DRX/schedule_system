package schedule_system.utils;

public class BitMap {
    private int[] bits;
    private int size;

    public BitMap(int size) {
        this.size = size;
        bits = new int[size / 32 + 1];
    }

    public void set(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] |= (1 << offset);
    }

    public void unset(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] &= ~(1 << offset);
    }

    public void flip(int n) {
        if (n >= size)
            throw new IndexOutOfBoundsException();
        int index = n / 32;
        int offset = n % 32;
        bits[index] ^= (1 << offset);
    }

    public void flipAll() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = ~bits[i];
        }
    }

    public boolean get(int n) {
        int index = n / 32;
        int offset = n % 32;
        return (bits[index] & (1 << offset)) != 0;
    }

    public void clear(int n) {
        int index = n / 32;
        int offset = n % 32;
        bits[index] &= ~(1 << offset);
    }

    public void clear() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = 0;
        }
    }

    public int size() {
        return size;
    }

    public BitMap and(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] & other.bits[i];
        }
        return result;
    }

    public BitMap or(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] | other.bits[i];
        }
        return result;
    }

    public BitMap xor(BitMap other) {
        if (other.size != size)
            throw new IllegalArgumentException();
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = bits[i] ^ other.bits[i];
        }
        return result;
    }

    public BitMap not() {
        BitMap result = new BitMap(size);
        for (int i = 0; i < bits.length; i++) {
            result.bits[i] = ~bits[i];
        }
        return result;
    }

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
