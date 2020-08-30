package lesson_6;

//считаю, что это разные варианты одного задания
//1. Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере
//(запущен, произошла ошибка, клиент подключился, клиент прислал сообщение/команду).
//4. *Добавить на серверную сторону сетевого чата логирование событий.

import java.util.Arrays;
import java.util.Random;

public class ArrayManipulator {

    //2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    //Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    //идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
    //иначе в методе необходимо выбросить RuntimeException.
    //Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    public int[] getArrayOfElementsAfter(int[] sourceArray, int separator) {
        if (sourceArray == null) throw new RuntimeException("Your array is wack (null actually). COME ON!");
        int separatorIndex = -1;
        int sal = sourceArray.length;
        int[] funcResult = null;
        for (int i = sal - 1; i >= 0; i--) if (sourceArray[i] == separator) separatorIndex = i;
        if (separatorIndex != -1 && separatorIndex < sal - 1) {
            funcResult = Arrays.copyOfRange(sourceArray, separatorIndex + 1, sal);
        } else throw new RuntimeException(String.format("Массив заканчивается на цифру %d или не содержит ее", separator));
        return funcResult;
    }

    //3. Написать метод, который проверяет состав массива из чисел 1 и 4.
    //Если в нем нет хоть одной четверки или единицы, то метод вернет false;
    //Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    public boolean arrayContainsAnySetElement(int[] sourceArray, int[] elementsSet) {
        boolean funcResult = false;
        endOfSearch:
        for (int element: elementsSet) {
            for (int sourceNumber: sourceArray) {
                if (sourceNumber == element) {
                    funcResult = true;
                    break endOfSearch;
                }
            }
        }
        return funcResult;
    }

    public int[] generateRandomArray(int initialCapacity, int bound) {
        Random randomizer = new Random();
        int[] funcResult = new int[initialCapacity];
        for (int i = 0; i < funcResult.length; i++)
            funcResult[i] = randomizer.nextInt(bound);
        return funcResult;
    }

}