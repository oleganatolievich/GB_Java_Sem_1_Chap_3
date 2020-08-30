package lesson_6;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ArrayEqualityTest {

    private static ArrayManipulator arrayer;

    @BeforeAll
    public static void initArrayer() {
        arrayer = new ArrayManipulator();
    }

    //3. Написать метод, который проверяет состав массива из чисел 1 и 4.
    //Если в нем нет хоть одной четверки или единицы, то метод вернет false;
    //Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    //[ 1 1 1 4 4 1 4 4 ] -> true
    //[ 1 1 1 1 1 1 ] -> false
    //[ 4 4 4 4 ] -> false
    //[ 1 4 4 1 1 4 3 ] -> false
    @ParameterizedTest
    @MethodSource("getDataForTestArrayContainsAnySetElement")
    public void testArrayContainsAnySetElement(int[] sourceArray, int[] elementsSet, boolean expectedResult) {
        boolean actualResult = arrayer.arrayContainsAnySetElement(sourceArray, elementsSet);
        assertEquals(actualResult, expectedResult);
        //можно еще assertTrue, тогда по идее автоупаковка в объекты не потребуется
    }

    public static Stream<Arguments> getDataForTestArrayContainsAnySetElement() {
        return Stream.of(
                Arguments.of(new int[]{35, 79, 84, 94, 84, 37, 68, 65, 48, 89}, new int[]{1, 4}, false),
                Arguments.of(new int[]{73, 73, 28, 27, 6, 19, 7, 40, 92, 76}, new int[]{1, 4}, false),
                Arguments.of(new int[]{59, 61, 83, 16, 38, 67, 60, 13, 38, 46}, new int[]{1, 4}, false),
                Arguments.of(new int[]{15, 34, 82, 31, 1, 22, 66, 95, 18, 1}, new int[]{1, 4}, true)
        );
    }

}