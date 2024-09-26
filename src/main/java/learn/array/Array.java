package learn.array;

public interface Array<T extends Comparable<T>> {

    int size();

    int capacity();

    void add(T input);

    int indexOf(T target);

    boolean remove(T target);

    String toString();

}
