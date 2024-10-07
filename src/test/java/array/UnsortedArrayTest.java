package array;

import learn.array.Array;
import learn.array.UnsortedArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class UnsortedArrayTest {

    private final int capacity = 8;

    private final Array<Integer> unsortedArray = new UnsortedArray<>(capacity);

    public static Stream<Arguments> sourceAndResults() {
        return Stream.of(
                of(new int[]{}, "[]", 0),
                of(new int[]{10, 9, 8}, "[10, 9, 8]", 3),
                of(new int[]{10, 8, 6, 4, 2}, "[10, 8, 6, 4, 2]", 5),
                of(new int[]{1, 8, 2, 4, 3, 5, 7, 9}, "[1, 8, 2, 4, 3, 5, 7, 9]", 8)
        );
    }

    public static Stream<Arguments> sourceAndTargetAndIndex() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6}, 1, 0),
                of(new int[]{1, 2, 3, 4, 5, 6}, 6, 5),
                of(new int[]{1, 2, 3, 4, 5, 6}, 3, 2),
                of(new int[]{1, 2, 3, 4, 5, 6}, 4, 3),
                of(new int[]{1, 2, 3, 4, 5, 6}, 7, -1),
                of(new int[]{1, 2, 3, 4, 5, 6}, 0, -1)
        );
    }

    public static Stream<Arguments> sourceAndRemovableAndOutcomes() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6}, 1, "[2, 3, 4, 5, 6]", 5, true),
                of(new int[]{1, 2, 3, 4, 5, 6}, 6, "[1, 2, 3, 4, 5]", 5, true),
                of(new int[]{1, 2, 3, 4, 5, 6}, 3, "[1, 2, 4, 5, 6]", 5, true),
                of(new int[]{1, 2}, 2, "[1]", 1, true),
                of(new int[]{1}, 1, "[]", 0, true),
                of(new int[]{1, 2}, 3, "[1, 2]", 2, false),
                of(new int[]{1}, 3, "[1]", 1, false),
                of(new int[]{}, 3, "[]", 0, false)
        );
    }

    public static Stream<Arguments> sourceAndGetByIndex() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6}, 0, 1),
                of(new int[]{1, 2, 3, 4, 5, 6}, 1, 2),
                of(new int[]{1, 2, 3, 4, 5, 6}, 2, 3),
                of(new int[]{1, 2, 3, 4, 5, 6}, 3, 4),
                of(new int[]{1, 2, 3, 4, 5, 6}, 4, 5),
                of(new int[]{1, 2, 3, 4, 5, 6}, 5, 6)
        );
    }

    public static Stream<Arguments> sourceAndGetByWrongIndex() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6}, -1),
                of(new int[]{1, 2, 3, 4, 5, 6}, -2),
                of(new int[]{1, 2, 3, 4, 5, 6}, 7),
                of(new int[]{1, 2, 3, 4, 5, 6}, 8),
                of(new int[]{}, 1),
                of(new int[]{}, 0),
                of(new int[]{}, -1)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceAndResults")
    void addElementsUnsorted(int[] elements, String expectedToStringValue, int expectedSize) {
        for (int element : elements) {
            unsortedArray.add(element);
        }
        assertThat(unsortedArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(unsortedArray.size()).isEqualTo(expectedSize);
    }

    @Test
    void addElementFailsWhenArrayIsFull() {
        for (int i = 0; i < capacity; ++i) {
            unsortedArray.add(i);
        }

        assertThatThrownBy(() -> unsortedArray.add(11))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Array is full [size=%s]".formatted(capacity));
    }

    @ParameterizedTest
    @MethodSource("sourceAndTargetAndIndex")
    void returnIndexOfTargetElement(int[] source, int searchTarget, int expectedIndex) {
        for (int element : source) {
            unsortedArray.add(element);
        }

        assertThat(unsortedArray.indexOf(searchTarget)).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemovableAndOutcomes")
    void removeElements(int[] source, int removable, String expectedToStringValue, int expectedSize, boolean success) {
        for (int element : source) {
            unsortedArray.add(element);
        }

        assertThat(unsortedArray.remove(removable)).isEqualTo(success);

        assertThat(unsortedArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(unsortedArray.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndGetByIndex")
    void getByIndex(int[] source, int index, int expectedValue) {
        for (int element : source) {
            unsortedArray.add(element);
        }

        assertThat(unsortedArray.get(index)).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("sourceAndGetByWrongIndex")
    void getByIndexThrowsExceptionWhenIndexIsOutOfBounds(int[] source, int index) {
        for (int element : source) {
            unsortedArray.add(element);
        }

        assertThatThrownBy(() -> unsortedArray.get(index))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index is out of bounds [size=%s, index=%s]".formatted(unsortedArray.size(), index));
    }
}
