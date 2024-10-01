package stack;

import learn.stack.LLStack;
import learn.stack.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LLStackTest {

    Stack<Integer> stack = new LLStack<>();

    public static Stream<Arguments> sourceAndPush() {
        return Stream.of(
                Arguments.of(new int[]{5}, "[5]", 1),
                Arguments.of(new int[]{5, 4}, "[4, 5]", 2),
                Arguments.of(new int[]{5, 4, 3}, "[3, 4, 5]", 3),
                Arguments.of(new int[]{5, 4, 3, 2}, "[2, 3, 4, 5]", 4),
                Arguments.of(new int[]{5, 4, 3, 2, 1}, "[1, 2, 3, 4, 5]", 5)
        );
    }

    public static Stream<Arguments> sourceAndPop() {
        return Stream.of(
                Arguments.of(new int[]{5}, 5, "[]", 0),
                Arguments.of(new int[]{5, 4}, 4, "[5]", 1),
                Arguments.of(new int[]{4, 3, 2}, 2, "[3, 4]", 2),
                Arguments.of(new int[]{6, 4, 3, 2}, 2, "[3, 4, 6]", 3),
                Arguments.of(new int[]{1, 5, 4, 3, 2}, 2, "[3, 4, 5, 1]", 4)
        );
    }

    public static Stream<Arguments> sourceAndPeek() {
        return Stream.of(
                Arguments.of(new int[]{5}, 5, "[5]", 1),
                Arguments.of(new int[]{5, 4}, 4, "[4, 5]", 2),
                Arguments.of(new int[]{4, 3, 2}, 2, "[2, 3, 4]", 3),
                Arguments.of(new int[]{6, 4, 3, 2}, 2, "[2, 3, 4, 6]", 4),
                Arguments.of(new int[]{1, 5, 4, 3, 2}, 2, "[2, 3, 4, 5, 1]", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceAndPush")
    void pushDataToStack(int[] source, String expectedString, int expectedSize) {
        for (int element : source) {
            stack.push(element);
        }

        assertThat(stack.toString()).isEqualTo(expectedString);
        assertThat(stack.size()).isEqualTo(expectedSize);
    }

    @ParameterizedTest
    @MethodSource("sourceAndPop")
    void popDataFromStack(int[] source, int expectedPop, String expectedString, int expectedSize) {
        for (int element : source) {
            stack.push(element);
        }

        assertThat(stack.pop()).isEqualTo(expectedPop);
        assertThat(stack.toString()).isEqualTo(expectedString);
        assertThat(stack.size()).isEqualTo(expectedSize);
    }

    @Test
    void throwExceptionWhenPoppingEmptyStack() {
        assertThatThrownBy(() -> stack.pop())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Stack is empty");
    }

    @ParameterizedTest
    @MethodSource("sourceAndPeek")
    void peekDataFromStack(int[] source, int expectedPeek, String expectedString, int expectedSize) {
        for (int element : source) {
            stack.push(element);
        }

        assertThat(stack.peek()).isEqualTo(expectedPeek);
        assertThat(stack.toString()).isEqualTo(expectedString);
        assertThat(stack.size()).isEqualTo(expectedSize);
    }
}
