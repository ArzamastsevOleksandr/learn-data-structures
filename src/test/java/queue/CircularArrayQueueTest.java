package queue;

import learn.queue.CircularArrayQueue;
import learn.queue.Queue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CircularArrayQueueTest {

    private final Queue<Integer> queue = new CircularArrayQueue<>(5);

    public static Stream<Arguments> enqueueSourceAndDequeueResultsWithoutOverflow() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, "[5, 4, 3, 2, 1]", new int[]{1, 2, 3, 4, 5}),
                Arguments.of(new int[]{4, 5}, "[5, 4]", new int[]{4, 5}),
                Arguments.of(new int[]{5}, "[5]", new int[]{5})
        );
    }

    public static Stream<Arguments> fullEnqueueSourceWithOverflow() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 5, 100),
                Arguments.of(new int[]{6, 7, 8, 9}, 4, 90),
                Arguments.of(new int[]{4, 3, 2}, 3, 80),
                Arguments.of(new int[]{4, 3}, 2, 70)
        );
    }

    @ParameterizedTest
    @MethodSource("enqueueSourceAndDequeueResultsWithoutOverflow")
    void firstInFirstOutIsGuaranteedWhenNoOverflowHappened(int[] source, String expectedString, int[] dequeueOutput) {
        for (int element : source) {
            queue.enqueue(element);
        }

        assertThat(queue.toString()).isEqualTo(expectedString);

        for (int element : dequeueOutput) {
            assertThat(queue.dequeue()).isEqualTo(element);
        }
    }

    @Test
    void firstInFirstOutIsGuaranteedWhenOverflowHappened() {
        queue.enqueue(1);
        assertThat(queue.toString()).isEqualTo("[1]");

        queue.enqueue(2);
        assertThat(queue.toString()).isEqualTo("[2, 1]");

        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        assertThat(queue.toString()).isEqualTo("[5, 4, 3, 2, 1]");

        assertThat(queue.dequeue()).isEqualTo(1);
        assertThat(queue.toString()).isEqualTo("[5, 4, 3, 2]");

        assertThat(queue.dequeue()).isEqualTo(2);
        assertThat(queue.toString()).isEqualTo("[5, 4, 3]");

        assertThat(queue.dequeue()).isEqualTo(3);
        assertThat(queue.toString()).isEqualTo("[5, 4]");

        assertThat(queue.dequeue()).isEqualTo(4);
        assertThat(queue.toString()).isEqualTo("[5]");

        assertThat(queue.dequeue()).isEqualTo(5);
        assertThat(queue.toString()).isEqualTo("[]");

        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);
        assertThat(queue.toString()).isEqualTo("[10, 9, 8, 7, 6]");

        assertThat(queue.dequeue()).isEqualTo(6);
        assertThat(queue.dequeue()).isEqualTo(7);
        assertThat(queue.dequeue()).isEqualTo(8);
        assertThat(queue.toString()).isEqualTo("[10, 9]");

        assertThat(queue.dequeue()).isEqualTo(9);
        assertThat(queue.dequeue()).isEqualTo(10);
        assertThat(queue.toString()).isEqualTo("[]");
    }

    @ParameterizedTest
    @MethodSource("fullEnqueueSourceWithOverflow")
    void enqueueThrowsExceptionWhenQueueIsFull(int[] source, int capacity, int newElement) {
        Queue<Integer> queue = new CircularArrayQueue<>(capacity);
        for (int element : source) {
            queue.enqueue(element);
        }

        assertThatThrownBy(() -> queue.enqueue(newElement))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("enqueue() called on a full queue");
    }

    @Test
    void dequeueThrowsExceptionWhenQueueIsEmpty() {
        assertThatThrownBy(queue::dequeue)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("dequeue() called on an empty queue");
    }
}
