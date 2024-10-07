package learn.list;

import java.util.Objects;

public class DoublyLinkedList<T> implements List<T> {

    private int size = 0;
    private DLLNode<T> head;
    private DLLNode<T> tail;

    @Override
    public void addFirst(T input) {
        DLLNode<T> newHead = new DLLNode<>(input);
        if (head == null) {
            tail = newHead;
        } else {
            newHead.append(head);
        }
        head = newHead;
        ++size;
    }

    @Override
    public void addLast(T input) {
        DLLNode<T> newTail = new DLLNode<>(input);
        if (tail == null) {
            head = newTail;
        } else {
            tail.append(newTail);
        }
        tail = newTail;
        ++size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("removeFirst() invoked on an empty list");
        }
        if (size == 1) {
            T firstElement = head.data();
            head = tail = null;
            --size;
            return firstElement;
        }
        T firstElement = head.data();
        head = head.next();
        head.prev = null;
        --size;
        return firstElement;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            throw new IllegalStateException("removeLast() invoked on an empty list");
        }
        if (size == 1) {
            T lastElement = tail.data();
            head = tail = null;
            --size;
            return lastElement;
        }
        T lastElement = tail.data();
        tail = tail.prev();
        tail.next = null;
        --size;
        return lastElement;
    }

    @Override
    public boolean remove(T target) {
        if (size == 0) {
            return false;
        }
        DLLNode<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data(), target)) {
                if (current == head) {
                    head = head.next();
                    if (head == null) {
                        tail = null;
                    } else {
                        head.prev = null;
                    }
                } else if (current == tail) {
                    tail = tail.prev();
                    if (tail == null) {
                        head = null;
                    } else {
                        tail.next = null;
                    }
                } else {
                    current.prev().append(current.next());
                }
                --size;
                return true;
            } else {
                current = current.next();
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (size - 1) + ", got: " + index);
        }
        DLLNode<T> current = head;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }
        return current.data();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        if (size() == 1) {
            return "[" + head.data() + "]";
        }
        DLLNode<T> current = head;
        StringBuilder result = new StringBuilder("[").append(current.data());
        while (current.hasNext()) {
            current = current.next();
            result.append(", ").append(current.data());
        }
        return result.append("]").toString();
    }

    private static class DLLNode<T> {
        private T data;
        private DLLNode<T> prev;
        private DLLNode<T> next;

        public DLLNode(T data) {
            this(data, null, null);
        }

        public DLLNode(T data, DLLNode<T> prev, DLLNode<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public void append(DLLNode<T> next) {
            this.next = next;
            if (next != null) {
                next.prev = this;
            }
        }

        public T data() {
            return data;
        }

        public boolean hasNext() {
            return next != null;
        }

        public DLLNode<T> next() {
            return next;
        }

        public DLLNode<T> prev() {
            return prev;
        }
    }
}
