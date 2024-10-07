package queue;

import learn.queue.LLQueue;
import learn.queue.Queue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LLQueueTest {

    private final Queue<Integer> queue = new LLQueue<>();

    public static Stream<Arguments> enqueueSourceAndDequeueResults() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, "[5, 4, 3, 2, 1]", new int[]{1, 2, 3, 4, 5}),
                Arguments.of(new int[]{4, 5}, "[5, 4]", new int[]{4, 5}),
                Arguments.of(new int[]{5}, "[5]", new int[]{5})
        );
    }

    @ParameterizedTest
    @MethodSource("enqueueSourceAndDequeueResults")
    void firstInFirstOutIsGuaranteed(int[] source, String expectedString, int[] dequeueOutput) {
        for (int element : source) {
            queue.enqueue(element);
        }

        assertThat(queue.toString()).isEqualTo(expectedString);

        for (int element : dequeueOutput) {
            assertThat(queue.dequeue()).isEqualTo(element);
        }
    }
}
