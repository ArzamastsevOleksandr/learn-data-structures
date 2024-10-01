package learn.list;

public interface List<T> {

    void addFirst(T input);

    void addLast(T input);

    boolean removeFirst();

    boolean removeLast();

    boolean remove(T target);

    T get(int index);

    int size();

}
