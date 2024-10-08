package learn.array;

public class DynamicArray<T extends Comparable<T>> implements Array<T> {

    private final int scaleFactor = 2;
    private final int downScaleThresholdFactor = 4;

    private Object[] array;
    private int size;

    public DynamicArray(int initialCapacity) {
        this.array = new Object[initialCapacity];
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
            grow();
        }
        array[size++] = input;
    }

    @Override
    public int indexOf(T target) {
        for (int i = 0; i < size; ++i) {
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
        for (int i = index; i < size - 1; ++i) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        if (size <= capacity() / downScaleThresholdFactor) {
            shrink();
        }
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

    private void grow() {
        Object[] grownArray = new Object[size * scaleFactor];
        for (int i = 0; i < size; i++) {
            grownArray[i] = array[i];
        }
        array = grownArray;
    }

    private void shrink() {
        Object[] downsizedArray = new Object[capacity() / scaleFactor];
        for (int i = 0; i < size; ++i) {
            downsizedArray[i] = array[i];
        }
        array = downsizedArray;
    }
}
