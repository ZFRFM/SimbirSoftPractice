package ru.faimizufarov.simbirtraining.java.old_tasks;

import java.util.Arrays;

/**
 * Набор тренингов по работе с примитивными типами java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see ElementaryTrainingTest.
 */
public class ElementaryTraining {

    /**
     * Метод должен возвращать среднее значение
     * для введенных параметров
     *
     * @param firstValue  первый элемент
     * @param secondValue второй элемент
     * @return среднее значение для введенных чисел
     */
    public double averageValue(int firstValue, int secondValue) {
        return (firstValue + secondValue) / 2.0;
    }

    /**
     * Пользователь вводит три числа.
     * Произвести манипуляции и вернуть сумму новых чисел
     *
     * @param firstValue  увеличить в два раза
     * @param secondValue уменьшить на три
     * @param thirdValue  возвести в квадрат
     * @return сумма новых трех чисел
     */
    public double complicatedAmount(int firstValue, int secondValue, int thirdValue) {
        int result = firstValue * 2 + (secondValue - 3) + thirdValue * thirdValue;
        return (double) result;
    }

    /**
     * Метод должен поменять значение в соответствии с условием.
     * Если значение больше 3, то увеличить
     * на 10, иначе уменьшить на 10.
     *
     * @param value число для изменения
     * @return новое значение
     */
    public int changeValue(int value) {
        if (value > 3) {
            return value+10;
        }
        else {
            return value-10;
        }
    }

    /**
     * Метод должен менять местами первую
     * и последнюю цифру числа.
     * Обрабатывать максимум пятизначное число.
     * Если число < 10, вернуть
     * то же число
     *
     * @param value число для перестановки
     * @return новое число
     */
    public int swapNumbers(int value) {
        String strNumberValue = String.valueOf(value);
        if (strNumberValue.length() > 5) {
            System.out.println("Обработаю максимум пятизначное число. Больше не буду :)");
            return 0;
        }
        else if (value < 10) {
            return value;
        }
        else {
            int indexOfLastChar = strNumberValue.length() - 1;
            StringBuilder builder = new StringBuilder(strNumberValue.substring(1, indexOfLastChar));
            builder.insert(0, strNumberValue.charAt(indexOfLastChar))
                    .append(strNumberValue.charAt(0));
            return Integer.parseInt(String.valueOf(builder));
        }
    }

    /**
     * Изменить значение четных цифр числа на ноль.
     * Счет начинать с единицы.
     * Обрабатывать максимум пятизначное число.
     * Если число < 10 вернуть
     * то же число.
     *
     * @param value число для изменения
     * @return новое число
     */
    public int zeroEvenNumber(int value) {
        char[] chars = (String.valueOf(value)).toCharArray();
        if (chars.length > 5) {
            System.out.println("Обработаю максимум пятизначное число. Больше не буду :)");
            return 0;
        }
        else if (value < 10) {
            return value;
        }
        else {
            for (int i = 0; i < chars.length; i++){
                if ((int) chars[i] % 2 == 0) {
                    chars[i] = '0';
                }
            }
            return Integer.parseInt(new String(chars));
        }
    }
}