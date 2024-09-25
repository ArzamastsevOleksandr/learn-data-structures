package array;

import learn.array.Array;
import learn.array.SortedArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class SortedArrayTest {

    private final int capacity = 8;

    private Array<Integer> sortedArray;

    public static Stream<Arguments> sourceAndResults() {
        return Stream.of(
                of(new int[]{10, 9, 8, 7, 6}, "[6, 7, 8, 9, 10]", 5),
                of(new int[]{1, 9, 2, 7, 3}, "[1, 2, 3, 7, 9]", 5),
                of(new int[]{1, 2, 3, 4}, "[1, 2, 3, 4]", 4),
                of(new int[]{4, 3}, "[3, 4]", 2),
                of(new int[]{4}, "[4]", 1),
                of(new int[]{}, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndSearchTargetAndIndex() {
        return Stream.of(
                of(new int[]{10, 9, 8, 7, 6}, 10, 4),
                of(new int[]{10, 9, 5, 1, 7}, 1, 0),
                of(new int[]{10, 9, 5, 1, 7}, 5, 1),
                of(new int[]{1, 10, 21, 31}, 31, 3),
                of(new int[]{1, 10, 21, 31}, 50, -1),
                of(new int[]{}, 50, -1)
        );
    }

    public static Stream<Arguments> sourceAndRemovableAndResults() {
        return Stream.of(
                of(new int[]{10, 9, 8, 7, 6}, 6, "[7, 8, 9, 10]", 4, true),
                of(new int[]{10, 9, 8, 7, 6}, 10, "[6, 7, 8, 9]", 4, true),
                of(new int[]{10, 9, 8, 7, 6}, 8, "[6, 7, 9, 10]", 4, true),
                of(new int[]{10, 9}, 9, "[10]", 1, true),
                of(new int[]{10}, 10, "[]", 0, true),
                of(new int[]{10, 9}, 11, "[9, 10]", 2, false),
                of(new int[]{10}, 11, "[10]", 1, false),
                of(new int[]{}, 11, "[]", 0, false)
        );
    }

    @BeforeEach
    void setUp() {
        sortedArray = new SortedArray<>(capacity);
    }

    @ParameterizedTest
    @MethodSource("sourceAndResults")
    void addElementsSorted(int[] source, String expectedToStringValue, int expectedSize) {
        for (int element : source) {
            sortedArray.add(element);
        }
        assertThat(sortedArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(sortedArray.size()).isEqualTo(expectedSize);
    }

    @Test
    void addElementFailsWhenArrayIsFull() {
        for (int i = 0; i < capacity; ++i) {
            sortedArray.add(i);
        }

        assertThatThrownBy(() -> sortedArray.add(11))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Array is full [size=%s]".formatted(capacity));
    }

    @ParameterizedTest
    @MethodSource("sourceAndSearchTargetAndIndex")
    void returnIndexOfTargetElement(int[] source, int searchTarget, int expectedIndex) {
        for (int element : source) {
            sortedArray.add(element);
        }

        assertThat(sortedArray.indexOf(searchTarget)).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemovableAndResults")
    void removeElements(int[] source, int removable, String expectedToStringValue, int expectedSize, boolean success) {
        for (int element : source) {
            sortedArray.add(element);
        }

        assertThat(sortedArray.remove(removable)).isEqualTo(success);
        assertThat(sortedArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(sortedArray.size()).isEqualTo(expectedSize);
    }
}
