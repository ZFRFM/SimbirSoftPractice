package com.example.javacoretrainingpart1;

/**
 * Набор тренингов по работе с массивами в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see ArraysTrainingTest.
 */
public class ArraysTraining {

    /**
     * Метод должен сортировать входящий массив
     * по возрастранию пузырьковым методом
     *
     * @param valuesArray массив для сортировки
     * @return отсортированный массив
     */
    public int[] sort(int[] valuesArray) {
        for (int i = 0; i < valuesArray.length - 1; i++) {
            for (int j = 0; j < valuesArray.length - i - 1; j++) {
                if (valuesArray[j + 1] < valuesArray[j]) {
                    int swap = valuesArray[j];
                    valuesArray[j] = valuesArray[j + 1];
                    valuesArray[j + 1] = swap;
                }
            }
        }
        return valuesArray;
    }

    /**
     * Метод должен возвращать максимальное
     * значение из введенных. Если входящие числа
     * отсутствуют - вернуть 0
     *
     * @param values входящие числа
     * @return максимальное число или 0
     */
    public int maxValue(int... values) {
        int maxValue = 0;
        if (values.length > 0) {
            for (int value : values) {
                if (value > maxValue) {
                    maxValue = value;
                }
            }
            return maxValue;
        }
        else {
            return 0;
        }
    }

    /**
     * Переставить элементы массива
     * в обратном порядке
     *
     * @param array массив для преобразования
     * @return входящий массив в обратном порядке
     */
    public int[] reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
        return array;
    }

    /**
     * Метод должен вернуть массив,
     * состоящий из чисел Фибоначчи
     *
     * @param numbersCount количество чисел Фибоначчи,
     *                     требуемое в исходящем массиве.
     *                     Если numbersCount < 1, исходный
     *                     массив должен быть пуст.
     * @return массив из чисел Фибоначчи
     */
    public int[] fibonacciNumbers(int numbersCount) {
        if (numbersCount < 1) {
            return new int[0];
        }
        else {
            int[] array = new int[numbersCount];
            for (int i = 0; i < numbersCount; i++) {
                array[i] = fibonachi(i);
            }
            return array;
        }
    }

    static int fibonachi(int n) {
        if (n <= 1) {
            return 1;
        }
        else {
            return fibonachi(n - 1) + fibonachi(n - 2);
        }
    }

    /**
     * В данном массиве найти максимальное
     * количество одинаковых элементов.
     *
     * @param array массив для выборки
     * @return количество максимально встречающихся
     * элементов
     */
    public int maxCountSymbol(int[] array) {
        int currentSymbolValue = 0;
        int maxCountSymbolValue = 0;
        for (int i : array) {
            for (int j : array) {
                if (i == j) {
                    currentSymbolValue++;
                }
            }
            if (currentSymbolValue > maxCountSymbolValue) {
                maxCountSymbolValue = currentSymbolValue;
            }
            currentSymbolValue = 0;
        }
        return maxCountSymbolValue;
    }
}