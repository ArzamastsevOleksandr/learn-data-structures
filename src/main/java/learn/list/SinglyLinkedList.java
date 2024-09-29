package learn.list;

import java.util.Objects;

public class SinglyLinkedList<T> implements List<T> {

    private SLLNode<T> head;
    private int size = 0;

    @Override
    public void addFirst(T input) {
        head = new SLLNode<>(input, head);
        ++size;
    }

    @Override
    public void addLast(T input) {
        SLLNode<T> newTail = new SLLNode<>(input, null);
        SLLNode<T> oldTail = findLast();
        if (oldTail == null) {
            head = newTail;
        } else {
            oldTail.append(newTail);
        }
        ++size;
    }

    @Override
    public boolean removeFirst() {
        if (size == 0) {
            return false;
        }
        head = head.next;
        --size;
        return true;
    }

    @Override
    public boolean removeLast() {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            head = null;
            --size;
            return true;
        }
        SLLNode<T> prev = head;
        SLLNode<T> current = head.next();
        while (current.hasNext()) {
            prev = current;
            current = current.next();
        }
        prev.append(null);
        --size;
        return true;
    }

    @Override
    public boolean remove(T target) {
        SLLNode<T> prev = null;
        SLLNode<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data(), target)) {
                if (prev == null) {
                    head = current.next();
                } else {
                    prev.append(current.next());
                }
                --size;
                return true;
            }
            prev = current;
            current = current.next();
        }
        return false;
    }

    private SLLNode<T> findLast() {
        if (head == null) {
            return null;
        }
        SLLNode<T> current = head;
        while (current.hasNext()) {
            current = current.next();
        }
        return current;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        if (size == 1) {
            return "[" + head.data() + "]";
        }
        SLLNode<T> current = head;
        StringBuilder result = new StringBuilder("[").append(current.data());
        while (current.hasNext()) {
            current = current.next();
            result.append(", ").append(current.data());
        }
        return result.append("]").toString();
    }

    private static class SLLNode<T> {
        private final T data;
        private SLLNode<T> next;

        public SLLNode(T data, SLLNode<T> next) {
            this.data = data;
            this.next = next;
        }

        public SLLNode(T data) {
            this(data, null);
        }

        public boolean hasNext() {
            return next != null;
        }

        public SLLNode<T> next() {
            return next;
        }

        public T data() {
            return data;
        }

        public void append(SLLNode<T> next) {
            this.next = next;
        }
    }
}
