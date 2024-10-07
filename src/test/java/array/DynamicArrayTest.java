package array;

import learn.array.Array;
import learn.array.DynamicArray;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class DynamicArrayTest {

    private final int initialCapacity = 8;

    private Array<Integer> dynamicArray;

    public static Stream<Arguments> sourceAndResults() {
        return Stream.of(
                of(2, 2, new int[]{}, "[]", 0),
                of(2, 4, new int[]{10, 9, 8}, "[10, 9, 8]", 3),
                of(5, 5, new int[]{10, 8, 6, 4, 2}, "[10, 8, 6, 4, 2]", 5),
                of(10, 10, new int[]{1, 8, 2, 4, 3, 5, 7, 9}, "[1, 8, 2, 4, 3, 5, 7, 9]", 8)
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
                of(20, 10, new int[]{1, 2, 3, 4, 5, 6}, 1, "[2, 3, 4, 5, 6]", 5, true),
                of(8, 4, new int[]{10, 9, 8}, 9, "[10, 8]", 2, true),
                of(4, 2, new int[]{10, 20}, 20, "[10]", 1, true),
                of(2, 2, new int[]{1, 2}, 2, "[1]", 1, true),
                of(2, 1, new int[]{1}, 1, "[]", 0, true),
                of(4, 4, new int[]{1, 2}, 3, "[1, 2]", 2, false),
                of(2, 2, new int[]{1}, 3, "[1]", 1, false),
                of(2, 2, new int[]{}, 3, "[]", 0, false)
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
    void addElementsWithResize(int initialCapacity, int resultingCapacity, int[] source, String expectedToStringValue, int expectedSize) {
        dynamicArray = new DynamicArray<>(initialCapacity);
        assertThat(dynamicArray.capacity()).isEqualTo(initialCapacity);

        for (int element : source) {
            dynamicArray.add(element);
        }
        assertThat(dynamicArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(dynamicArray.size()).isEqualTo(expectedSize);
        assertThat(dynamicArray.capacity()).isEqualTo(resultingCapacity);
    }

    @ParameterizedTest
    @MethodSource("sourceAndTargetAndIndex")
    void returnIndexOfTargetElement(int[] source, int searchTarget, int expectedIndex) {
        dynamicArray = new DynamicArray<>(initialCapacity);
        for (int element : source) {
            dynamicArray.add(element);
        }

        assertThat(dynamicArray.indexOf(searchTarget)).isEqualTo(expectedIndex);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemovableAndOutcomes")
    void removeElements(int initialCapacity, int resultingCapacity, int[] source, int removable, String expectedToStringValue, int expectedSize, boolean success) {
        dynamicArray = new DynamicArray<>(initialCapacity);
        assertThat(dynamicArray.capacity()).isEqualTo(initialCapacity);

        for (int element : source) {
            dynamicArray.add(element);
        }

        assertThat(dynamicArray.remove(removable)).isEqualTo(success);

        assertThat(dynamicArray.toString()).isEqualTo(expectedToStringValue);
        assertThat(dynamicArray.size()).isEqualTo(expectedSize);
        assertThat(dynamicArray.capacity()).isEqualTo(resultingCapacity);
    }

    @ParameterizedTest
    @MethodSource("sourceAndGetByIndex")
    void getByIndex(int[] source, int index, int expectedValue) {
        dynamicArray = new DynamicArray<>(initialCapacity);
        for (int element : source) {
            dynamicArray.add(element);
        }

        AssertionsForClassTypes.assertThat(dynamicArray.get(index)).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("sourceAndGetByWrongIndex")
    void getByIndexThrowsExceptionWhenIndexIsOutOfBounds(int[] source, int index) {
        dynamicArray = new DynamicArray<>(initialCapacity);
        for (int element : source) {
            dynamicArray.add(element);
        }

        assertThatThrownBy(() -> dynamicArray.get(index))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index is out of bounds [size=%s, index=%s]".formatted(dynamicArray.size(), index));
    }
}
