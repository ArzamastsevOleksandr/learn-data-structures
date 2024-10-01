package list;

import learn.list.DoublyLinkedList;
import learn.list.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class DoublyLinkedListTest {

    List<Integer> doublyLinkedList = new DoublyLinkedList<>();

    public static Stream<Arguments> sourceAndResultsForAddFirst() {
        return Stream.of(
                of(new int[]{99, 88, 77, 66, 5, 4, 3, 2, 1}, "[1, 2, 3, 4, 5, 66, 77, 88, 99]", 9),
                of(new int[]{1, 1, 2, 3, 4, 4, 5}, "[5, 4, 4, 3, 2, 1, 1]", 7),
                of(new int[]{10, 9, 8, 7}, "[7, 8, 9, 10]", 4),
                of(new int[]{10}, "[10]", 1),
                of(new int[]{}, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndResultsForAddLast() {
        return Stream.of(
                of(new int[]{99, 88, 77, 66, 5, 4, 3, 2, 1}, "[99, 88, 77, 66, 5, 4, 3, 2, 1]", 9),
                of(new int[]{1, 1, 2, 3, 4, 4, 5}, "[1, 1, 2, 3, 4, 4, 5]", 7),
                of(new int[]{10, 9, 8}, "[10, 9, 8]", 3),
                of(new int[]{99}, "[99]", 1)
        );
    }

    public static Stream<Arguments> sourceAndRemoveFirstAndResults() {
        return Stream.of(
                of(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, "[1, 2, 3, 4, 5, 6, 7, 8, 9]", 9, "[2, 3, 4, 5, 6, 7, 8, 9]", 8),
                of(new int[]{10, 20, 30}, "[30, 20, 10]", 3, "[20, 10]", 2),
                of(new int[]{10, 9}, "[9, 10]", 2, "[10]", 1),
                of(new int[]{99}, "[99]", 1, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndRemoveLastAndResults() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "[1, 2, 3, 4, 5, 6, 7, 8, 9]", 9, "[1, 2, 3, 4, 5, 6, 7, 8]", 8),
                of(new int[]{10, 20, 30}, "[10, 20, 30]", 3, "[10, 20]", 2),
                of(new int[]{11, 22}, "[11, 22]", 2, "[11]", 1),
                of(new int[]{99}, "[99]", 1, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndRemovableAndResults() {
        return Stream.of(
                of(new int[]{1, 2, 3, 4, 5, 6}, "[1, 2, 3, 4, 5, 6]", 6, 3, "[1, 2, 4, 5, 6]", 5, true),
                of(new int[]{1, 2, 3, 4, 5, 6}, "[1, 2, 3, 4, 5, 6]", 6, 4, "[1, 2, 3, 5, 6]", 5, true),
                of(new int[]{1, 2, 3, 4, 5, 6}, "[1, 2, 3, 4, 5, 6]", 6, 1, "[2, 3, 4, 5, 6]", 5, true),
                of(new int[]{1, 2, 3, 4, 5, 6}, "[1, 2, 3, 4, 5, 6]", 6, 6, "[1, 2, 3, 4, 5]", 5, true),
                of(new int[]{11, 22, 33}, "[11, 22, 33]", 3, 11, "[22, 33]", 2, true),
                of(new int[]{11, 22, 33}, "[11, 22, 33]", 3, 33, "[11, 22]", 2, true),
                of(new int[]{99, 88}, "[99, 88]", 2, 99, "[88]", 1, true),
                of(new int[]{99, 88}, "[99, 88]", 2, 88, "[99]", 1, true),
                of(new int[]{99}, "[99]", 1, 99, "[]", 0, true),
                of(new int[]{99}, "[99]", 1, 88, "[99]", 1, false),
                of(new int[]{99, 8, 1}, "[99, 8, 1]", 3, 88, "[99, 8, 1]", 3, false)
        );
    }

    public static Stream<Arguments> sourceAndGetTargetByIndex() {
        return Stream.of(
                of(new int[]{10, 20, 30, 40, 50}, 0, 10),
                of(new int[]{10, 20, 30, 40, 50}, 1, 20),
                of(new int[]{10, 20, 30, 40, 50}, 2, 30),
                of(new int[]{10, 20, 30, 40, 50}, 3, 40),
                of(new int[]{10, 20, 30, 40, 50}, 4, 50)
        );
    }

    public static Stream<Arguments> sourceAndWrongIndex() {
        return Stream.of(
                of(new int[]{10, 20, 30, 40, 50}, -2),
                of(new int[]{10, 20, 30, 40, 50}, -1),
                of(new int[]{10, 20, 30, 40, 50}, 5),
                of(new int[]{10, 20, 30, 40, 50}, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceAndResultsForAddFirst")
    void elementsAreAddedToListAtFirstPosition(int[] source, String expectedString, int expectedSize) {
        for (int element : source) {
            doublyLinkedList.addFirst(element);
        }

        assertThat(doublyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(doublyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndResultsForAddLast")
    void elementsAreAddedToListAtLastPosition(int[] source, String expectedString, int expectedSize) {
        for (int element : source) {
            doublyLinkedList.addLast(element);
        }

        assertThat(doublyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(doublyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemoveFirstAndResults")
    void firstElementIsRemoved(int[] source, String initialString, int initialSize, String expectedString, int expectedSize) {
        for (int element : source) {
            doublyLinkedList.addFirst(element);
        }

        assertThat(doublyLinkedList.toString()).isEqualTo(initialString);
        assertThat(doublyLinkedList.size()).isEqualTo(initialSize);

        doublyLinkedList.removeFirst();

        assertThat(doublyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(doublyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemoveLastAndResults")
    void lastElementIsRemoved(int[] source, String initialString, int initialSize, String expectedString, int expectedSize) {
        for (int element : source) {
            doublyLinkedList.addLast(element);
        }

        assertThat(doublyLinkedList.toString()).isEqualTo(initialString);
        assertThat(doublyLinkedList.size()).isEqualTo(initialSize);

        doublyLinkedList.removeLast();

        assertThat(doublyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(doublyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemovableAndResults")
    void elementIsRemoved(int[] source, String initialString, int initialSize, int removable, String expectedString, int expectedSize, boolean success) {
        for (int element : source) {
            doublyLinkedList.addLast(element);
        }

        assertThat(doublyLinkedList.toString()).isEqualTo(initialString);
        assertThat(doublyLinkedList.size()).isEqualTo(initialSize);

        assertThat(doublyLinkedList.remove(removable)).isEqualTo(success);

        assertThat(doublyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(doublyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndGetTargetByIndex")
    void getElementByIndex(int[] source, int index, int expectedData) {
        for (int element : source) {
            doublyLinkedList.addLast(element);
        }

        AssertionsForClassTypes.assertThat(doublyLinkedList.get(index)).isEqualTo(expectedData);
    }

    @ParameterizedTest
    @MethodSource("sourceAndWrongIndex")
    void indexOutOfBoundsWhenGetInvokedWithWrongIndex(int[] source, int wrongIndex) {
        for (int element : source) {
            doublyLinkedList.addLast(element);
        }

        assertThatThrownBy(() -> doublyLinkedList.get(wrongIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessage("Index must be between 0 and " + (source.length - 1) + ", got: " + wrongIndex);
    }
}
