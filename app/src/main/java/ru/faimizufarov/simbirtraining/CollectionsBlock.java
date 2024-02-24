package ru.faimizufarov.simbirtraining;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see CollectionsBlockTest.
 */
public class CollectionsBlock<T extends Comparable> {

    /**
     * Даны два упорядоченных по убыванию списка.
     * Объедините их в новый упорядоченный по убыванию список.
     * Исходные данные не проверяются на упорядоченность в рамках данного задания
     *
     * @param firstList  первый упорядоченный по убыванию список
     * @param secondList второй упорядоченный по убыванию список
     * @return объединенный упорядоченный список
     * @throws NullPointerException если один из параметров null
     */
    // Fusion sort
    public List<T> collectionTask0(@NonNull List<T> firstList, @NonNull List<T> secondList) {
        if (firstList == null || secondList == null) {
            throw new NullPointerException();
        }

        // Creating new sorted list for result.
        List<T> result = new LinkedList();
        // Making indices, which will iterate over source lists.
        int firstListIndex = 0;
        int secondListIndex = 0;
        int resultSize = firstList.size() + secondList.size();
        // Start iterating over both lists simultaneously.
        while ((firstListIndex + secondListIndex) < resultSize) {
            // If we finished with the first list - adding remaining items from the second list.
            if (firstListIndex >= firstList.size()) {
                result.addAll(secondList.subList(secondListIndex, secondList.size()));
                break;
            }

            // If we finished with the second list - adding remaining items from the first list.
            if (secondListIndex >= secondList.size()) {
                result.addAll(firstList.subList(firstListIndex, firstList.size()));
                break;
            }

            T firstItem = firstList.get(firstListIndex);
            T secondItem = secondList.get(secondListIndex);
            // Adding the smallest item between two
            // and incrementing index of the list we took it from.
            if (firstItem.compareTo(secondItem) >= 0) {
                result.add(firstItem);
                firstListIndex++;
            } else {
                result.add(secondItem);
                secondListIndex++;
            }
        }
        return result;
    }

    /**
     * Дан список. После каждого элемента добавьте предшествующую ему часть списка.
     *
     * @param inputList с исходными данными
     * @return измененный список
     * @throws NullPointerException если один из параметров null
     */
    public List<T> collectionTask1(@NonNull List<T> inputList) {
        if (inputList == null) {
            throw new NullPointerException();
        }
        List<T> finalList = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            finalList.add(inputList.get(i));
            finalList.addAll(inputList.subList(0, i));
        }
        return finalList;
    }

    /**
     * Даны два списка. Определите, совпадают ли множества их элементов.
     *
     * @param firstList  первый список элементов
     * @param secondList второй список элементов
     * @return <tt>true</tt> если множества списков совпадают
     * @throws NullPointerException если один из параметров null
     */
    public boolean collectionTask2(@NonNull List<T> firstList, @NonNull List<T> secondList) {
        if (firstList == null || secondList == null) {
            throw new NullPointerException();
        }
        Set<T> set1 = new HashSet<>(firstList);
        Set<T> set2 = new HashSet<>(secondList);
        return set1.equals(set2);
    }

    /**
     * Создать список из заданного количества элементов.
     * Выполнить циклический сдвиг этого списка на N элементов вправо или влево.
     * Если N > 0 циклический сдвиг вправо.
     * Если N < 0 циклический сдвиг влево.
     *
     * @param inputList список, для которого выполняется циклический сдвиг влево
     * @param n         количество шагов циклического сдвига N
     * @return список inputList после циклического сдвига
     * @throws NullPointerException если один из параметров null
     */
    public List<T> collectionTask3(@NonNull List<T> inputList, int n) throws NullPointerException {
        if (inputList == null) {
            throw new NullPointerException();
        }

        if (n == 0 || inputList.isEmpty()) {
            return inputList;
        } else if (n < 0) {
            return rotateLeft(inputList, -n);
        } else {
            return rotateRight(inputList, n);
        }
    }

    private List<T> rotateRight(@NonNull List<T> inputList, int n) {
        ArrayList<T> result = new ArrayList<>();
        int cycledN = n % inputList.size();
        for (int i = 0; i < cycledN; i++) {
            result.add(inputList.get(inputList.size() - cycledN + i));
        }
        for (int i = cycledN; i < inputList.size(); i++) {
            result.add(inputList.get(i - cycledN));
        }
        return result;
    }

    private List<T> rotateLeft(@NonNull List<T> inputList, int n) {
        ArrayList<T> result = new ArrayList<>();
        int cycledN = n % inputList.size();
        for (int i = cycledN; i < inputList.size(); i++) {
            result.add(inputList.get(i));
        }
        for (int i = 0; i < cycledN; i++) {
            result.add(inputList.get(i));
        }
        return result;
    }

    /**
     * Элементы списка хранят слова предложения.
     * Замените каждое вхождение слова A на B.
     *
     * @param inputList список со словами предложения и пробелами для разделения слов
     * @param a         слово, которое нужно заменить
     * @param b         слово, на которое нужно заменить
     * @return список после замены каждого вхождения слова A на слово В
     * @throws NullPointerException если один из параметров null
     */
    public List<String> collectionTask4(@NonNull List<String> inputList, @NonNull String a,
                                        @NonNull String b) throws NullPointerException {
        if (inputList == null || a == null || b == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).equals(a)) {
                inputList.set(i, b);
            }
        }
        return inputList;
    }
}
