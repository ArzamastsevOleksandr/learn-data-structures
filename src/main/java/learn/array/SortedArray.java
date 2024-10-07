package learn.array;

public class SortedArray<T extends Comparable<T>> implements Array<T> {

    private int size = 0;
    private T[] array;

    public SortedArray(int capacity) {
        this.array = (T[]) new Comparable[capacity];
    }

    @Override
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
        if (size == 0) {
            array[size++] = input;
            return;
        }
        for (int i = size; i > 0; i--) {
            if (array[i - 1].compareTo(input) <= 0) {
                array[i] = input;
                ++size;
                return;
            } else {
                array[i] = array[i - 1];
            }
        }
        array[0] = input;
        ++size;
    }

    @Override
    public int indexOf(T target) {
        int left = 0;
        int right = size - 1;
        int index = (left + right) / 2;

        while (left <= right) {
            if (array[index].equals(target)) {
                return index;
            }
            if (array[index].compareTo(target) < 0) {
                left = index + 1;
            } else {
                right = index - 1;
            }
            index = (left + right) / 2;
        }
        return -1;
    }

    @Override
    public boolean remove(T target) {
        int index = indexOf(target);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < size - 1; ++i) {
            array[i] = array[i + 1];
        }
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
