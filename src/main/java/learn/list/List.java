package learn.list;

public interface List<T> {

    void addFirst(T input);

    void addLast(T input);

    T removeFirst();

    T removeLast();

    boolean remove(T target);

    T get(int index);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

}
