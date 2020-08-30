package lesson_6;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ArraySplitTest {

    private static ArrayManipulator arrayer;

    @BeforeAll
    public static void initArrayer() {
        arrayer = new ArrayManipulator();
    }

    //2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    //Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    //идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
    //иначе в методе необходимо выбросить RuntimeException.
    //Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    //Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
    @ParameterizedTest
    @MethodSource("getDataForTestGetArrayOfElementsAfter")
    public void testGetArrayOfElementsAfter(int[] sourceArray, int valueToFind, int[] targetArray) {
        assertArrayEquals(targetArray, arrayer.getArrayOfElementsAfter(sourceArray, valueToFind));
    }

    public static Stream<Arguments> getDataForTestGetArrayOfElementsAfter() {
        return Stream.of(
                Arguments.of(new int[]{35, 79, 84, 94, 84, 37, 68, 65, 48, 89}, 68, new int[]{65, 48, 89}),
                Arguments.of(new int[]{73, 73, 28, 27, 6, 19, 7, 40, 92, 76}, 6, new int[]{19, 7, 40, 92, 76}),
                Arguments.of(new int[]{59, 61, 83, 16, 38, 67, 60, 13, 38, 46}, 83, new int[]{16, 38, 67, 60, 13, 38, 46}),
                Arguments.of(new int[]{15, 34, 82, 31, 1, 22, 66, 95, 18, 1}, 22, new int[]{66, 95, 18, 1})
        );
    }

}
