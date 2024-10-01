package learn.stack;

import learn.list.List;
import learn.list.SinglyLinkedList;

import java.util.NoSuchElementException;

public class LLStack<T> implements Stack<T> {

    private List<T> list = new SinglyLinkedList<>();

    @Override
    public void push(T input) {
        list.addFirst(input);
    }

    @Override
    public T pop() {
        if (size() == 0) {
            throw new NoSuchElementException("Stack is empty");
        }
        T pop = list.get(0);
        list.removeFirst();
        return pop;
    }

    @Override
    public T peek() {
        return list.get(0);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
