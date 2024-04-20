package ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks;

import java.text.ChoiceFormat;
import java.util.ArrayList;

/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see StringsTrainingTest.
 */
public class StringsTraining {

    /**
     * Метод по созданию строки,
     * состоящей из нечетных символов
     * входной строки в том же порядке
     * (нумерация символов идет с нуля)
     *
     * @param text строка для выборки
     * @return новая строка из нечетных
     * элементов строки text
     */
    public String getOddCharacterString(String text) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            if (i % 2 == 1) {
                result = result.concat(String.valueOf(text.charAt(i)));
            }
        }
        return result;
    }

    /**
     * Метод для определения количества
     * символов, идентичных последнему
     * в данной строке
     *
     * @param text строка для выборки
     * @return массив с номерами символов,
     * идентичных последнему. Если таких нет,
     * вернуть пустой массив
     */
    public int[] getArrayLastSymbol(String text) {
        char check = (char) text.length();
        if (!text.isEmpty()) {
            check = text.charAt(text.length() - 1);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        StringBuilder str = new StringBuilder(text);
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == check) {
                arrayList.add(str.indexOf(String.valueOf(str.charAt(0))) + i);
                str.deleteCharAt(0);
            }
        }
        return arrayList.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Метод по получению количества
     * цифр в строке
     *
     * @param text строка для выборки
     * @return количество цифр в строке
     */
    public int getNumbersCount(String text) {
        int numberCount = 0;
        if (!text.isEmpty()) {
            for (int i = 0; i < text.length() - 1; i++) {
                if (Character.isDigit(text.charAt(i))) {
                    numberCount++;
                }
            }
        }
        return numberCount;
    }

    /**
     * Дан текст. Заменить все цифры
     * соответствующими словами.
     *
     * @param text текст для поиска и замены
     * @return текст, где цифры заменены словами
     */
    public String replaceAllNumbers(String text) {
        String result = text;
        if (!text.isEmpty()) {
            for (int i = 0; i < text.length(); i++) {
                char actualChar = text.charAt(i);
                if (Character.isDigit(actualChar)) {
                    if (actualChar == '0') result = result.replaceAll(String.valueOf(actualChar), "zero");
                    if (actualChar == '1') result = result.replaceAll(String.valueOf(actualChar), "one");
                    if (actualChar == '2') result = result.replaceAll(String.valueOf(actualChar), "two");
                    if (actualChar == '3') result = result.replaceAll(String.valueOf(actualChar), "three");
                    if (actualChar == '4') result = result.replaceAll(String.valueOf(actualChar), "four");
                    if (actualChar == '5') result = result.replaceAll(String.valueOf(actualChar), "five");
                    if (actualChar == '6') result = result.replaceAll(String.valueOf(actualChar), "six");
                    if (actualChar == '7') result = result.replaceAll(String.valueOf(actualChar), "seven");
                    if (actualChar == '8') result = result.replaceAll(String.valueOf(actualChar), "eight");
                    if (actualChar == '9') result = result.replaceAll(String.valueOf(actualChar), "nine");
                }
            }
        }
        return result;
    }

    /**
     * Метод должен заменить заглавные буквы
     * на прописные, а прописные на заглавные
     *
     * @param text строка для изменения
     * @return измененная строка
     */
    public String capitalReverse(String text) {
        StringBuilder str = new StringBuilder();
        if (!text.isEmpty()) {
            for (int i = 0; i < text.length(); i++) {
                char actualChar = text.charAt(i);
                if (Character.isUpperCase(actualChar)) {
                    str.append(Character.toLowerCase(actualChar));
                }
                else {
                    str.append(Character.toUpperCase(actualChar));
                }
            }
        }

        return str.toString();
    }

    // Честно говоря, этот способ не прошёл через тесты?
    // Большое кол-во присваивания строки переменной перегрузило память?
    public String capitalReverse2(String text) {
        String resultText = text;
        if (!resultText.isEmpty()) {
            for (int i = 0; i < resultText.length() - 1; i++) {
                char actualChar = resultText.charAt(i);
                if (Character.isUpperCase(actualChar)) {
                    char lowercaseChar = Character.toLowerCase(actualChar);
                    resultText = resultText.replace(actualChar, lowercaseChar);
                }
                else {
                    char uppercaseChar = Character.toUpperCase(actualChar);
                    resultText = resultText.replace(actualChar, uppercaseChar);
                }
            }
        }
        return resultText;
    }
}
