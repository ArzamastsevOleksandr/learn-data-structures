package learn.array;

import java.util.Iterator;

public class SortedArray<T extends Comparable<T>> implements Array<T> {

    private int size = 0;
    private T[] array;

    public SortedArray(int capacity) {
        this.array = (T[]) new Comparable[capacity];
    }

    // [] => add 10 => [10]
    // [10] + 9 =>
    //  10 > 9 => [9, 10]

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
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        if (size == 1) {
            return "[" + array[0] + "]";
        }
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size - 1; ++i) {
            result.append(array[i]).append(", ");
        }
        result.append(array[size - 1]).append("]");
        return result.toString();
    }
}
