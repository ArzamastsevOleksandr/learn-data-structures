package learn.array;

public interface Array<T extends Comparable<T>> extends Iterable<T> {

    void add(T input);

    int indexOf(T target);

    boolean remove(T target);

    int size();

    String toString();

}
