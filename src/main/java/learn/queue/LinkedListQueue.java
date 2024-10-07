package learn.queue;

import learn.list.DoublyLinkedList;
import learn.list.List;

public class LinkedListQueue<T> implements Queue<T> {

    private final List<T> list = new DoublyLinkedList<>();

    @Override
    public void enqueue(T input) {
        list.addFirst(input);
    }

    @Override
    public T dequeue() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.removeLast();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
