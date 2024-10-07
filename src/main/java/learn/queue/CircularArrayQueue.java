package learn.queue;

public class CircularArrayQueue<T> implements Queue<T> {

    private int size;
    private int front;
    private int rear;

    private T[] array;

    public CircularArrayQueue(int capacity) {
        array = (T[]) new Object[capacity];
    }

    @Override
    public void enqueue(T input) {
        if (size == array.length) {
            throw new IllegalStateException("enqueue() called on a full queue");
        }
        array[rear] = input;
        size++;
        rear = (rear + 1) % array.length;
    }

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new IllegalStateException("dequeue() called on an empty queue");
        }
        T first = array[front];
        --size;
        front = (front + 1) % array.length;
        return first;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        int itemsProcessed = 0;
        int index = rear;
        StringBuilder result = new StringBuilder("[");
        while (itemsProcessed != size - 1) {
            index = index(index);
            result.append(array[index]).append(", ");
            itemsProcessed++;
        }
        index = index(index);
        return result.append(array[index]).append("]").toString();
    }

    private int index(int index) {
        index = index - 1;
        if (index == -1) {
            index = array.length - 1;
        }
        return index;
    }
}
