package learn.queue;

public interface Queue<T> {
    void enqueue(T input);

    T dequeue();
}
