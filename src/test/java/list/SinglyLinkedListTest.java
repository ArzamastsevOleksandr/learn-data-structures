package list;

import learn.list.SinglyLinkedList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class SinglyLinkedListTest {

    SinglyLinkedList<Integer> singlyLinkedList = new SinglyLinkedList<>();

    public static Stream<Arguments> sourceAndResultsForAddFirst() {
        return Stream.of(
                of(new int[]{1, 1, 2, 3, 4, 4, 5}, "[5, 4, 4, 3, 2, 1, 1]", 7),
                of(new int[]{10, 9, 8, 7}, "[7, 8, 9, 10]", 4),
                of(new int[]{10}, "[10]", 1),
                of(new int[]{}, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndResultsForAddLast() {
        return Stream.of(
                of(new int[]{1, 1, 2, 3, 4, 4, 5}, "[1, 1, 2, 3, 4, 4, 5]", 7),
                of(new int[]{10, 9, 8}, "[10, 9, 8]", 3),
                of(new int[]{99}, "[99]", 7)
        );
    }

    public static Stream<Arguments> sourceAndRemoveFirstAndResults() {
        return Stream.of(
                of(new int[]{10, 20, 30}, "[30, 20, 10]", 3, "[20, 10]", 2),
                of(new int[]{10, 9}, "[9, 10]", 2, "[10]", 1),
                of(new int[]{99}, "[99]", 1, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndRemoveLastAndResults() {
        return Stream.of(
                of(new int[]{10, 20, 30}, "[10, 20, 30]", 3, "[10, 20]", 2),
                of(new int[]{11, 22}, "[11, 22]", 2, "[11]", 1),
                of(new int[]{99}, "[99]", 1, "[]", 0)
        );
    }

    public static Stream<Arguments> sourceAndRemovableAndResults() {
        return Stream.of(
                of(new int[]{10, 20, 30, 3, 4, 5}, "[10, 20, 30, 3, 4, 5]", 6, 30, "[10, 20, 3, 4, 5]", 5),
                of(new int[]{11, 22, 33}, "[11, 22, 33]", 3, 11, "[22, 33]", 2),
                of(new int[]{11, 22, 33}, "[11, 22, 33]", 3, 33, "[11, 22]", 2),
                of(new int[]{99}, "[99]", 1, 99, "[]", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceAndResultsForAddFirst")
    void elementsAreAddedToListAtFirstPosition(int[] source, String expectedString, int expectedSize) {
        for (int element : source) {
            singlyLinkedList.addFirst(element);
        }

        assertThat(singlyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(singlyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndResultsForAddLast")
    void elementsAreAddedToListAtLastPosition(int[] source, String expectedString, int expectedSize) {
        for (int element : source) {
            singlyLinkedList.addLast(element);
        }

        assertThat(singlyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(singlyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemoveFirstAndResults")
    void firstElementIsRemoved(int[] source, String initialString, int initialSize, String expectedString, int expectedSize) {
        for (int element : source) {
            singlyLinkedList.addFirst(element);
        }

        assertThat(singlyLinkedList.toString()).isEqualTo(initialString);
        assertThat(singlyLinkedList.size()).isEqualTo(initialSize);

        singlyLinkedList.removeFirst();

        assertThat(singlyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(singlyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemoveLastAndResults")
    void lastElementIsRemoved(int[] source, String initialString, int initialSize, String expectedString, int expectedSize) {
        for (int element : source) {
            singlyLinkedList.addLast(element);
        }

        assertThat(singlyLinkedList.toString()).isEqualTo(initialString);
        assertThat(singlyLinkedList.size()).isEqualTo(initialSize);

        singlyLinkedList.removeLast();

        assertThat(singlyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(singlyLinkedList.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndRemovableAndResults")
    void elementIsRemoved(int[] source, String initialString, int initialSize, int removable, String expectedString, int expectedSize) {
        for (int element : source) {
            singlyLinkedList.addLast(element);
        }

        assertThat(singlyLinkedList.toString()).isEqualTo(initialString);
        assertThat(singlyLinkedList.size()).isEqualTo(initialSize);

        singlyLinkedList.remove(removable);

        assertThat(singlyLinkedList.toString()).isEqualTo(expectedString);
        assertThat(singlyLinkedList.size()).isEqualTo(expectedSize);
    }

}
