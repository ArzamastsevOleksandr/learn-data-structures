package learn.array;

public interface Array<T extends Comparable<T>> {

    int size();

    // todo: should it be length - size?
    int capacity();

    void add(T input);

    int indexOf(T target);

    boolean remove(T target);

    default boolean isEmpty() {
        return size() == 0;
    }

    T get(int index);

    default String string() {
        if (size() == 0) {
            return "[]";
        }
        if (size() == 1) {
            return "[" + get(0) + "]";
        }
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size() - 1; ++i) {
            result.append(get(i)).append(", ");
        }
        result.append(get(size() - 1)).append("]");
        return result.toString();
    }

}
