package learn.array;

public class UnsortedArray<T extends Comparable<T>> implements Array<T> {

    private int size = 0;
    private Object[] array;

    public UnsortedArray(int capacity) {
        this.array = new Object[capacity];
    }

    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return array.length;
    }

    @Override
    public void add(T input) {
        if (size == array.length) {
            throw new IndexOutOfBoundsException("Array is full [size=%s]".formatted(size));
        }
        array[size++] = input;
    }

    @Override
    public int indexOf(T target) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(T target) {
        int index = indexOf(target);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        --size;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds [size=%s, index=%s]".formatted(size, index));
        }
        return (T) array[index];
    }

    @Override
    public String toString() {
        return string();
    }
}
