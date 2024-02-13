package com.example.javacoretrainingpart1;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import kotlin.KotlinNothingValueException;

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
    public List<T> collectionTask0(@NonNull List<T> firstList, @NonNull List<T> secondList) {
        if (firstList == null || secondList == null) {
            throw new NullPointerException();
        }
        TreeSet<T> treeSet = new TreeSet<>(Collections.reverseOrder());
        treeSet.addAll(firstList);
        treeSet.addAll(secondList);
        return new ArrayList<>(treeSet);
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

    /*
    * Честное сердечное: это задание решал около 2 часов,
    * и с помощью for, и с помощью System.arraycopy().
    * По итогу закинул в чат гпт и получил такое решение.
    * Решение понятное, упустил момент с наличием в интерфейсе
    * Collections метода rotate.
    * */
    public List<T> collectionTask3(@NonNull List<T> inputList, int n) throws NullPointerException {
        if (inputList == null) {
            throw new NullPointerException();
        }
        if (n > 0) {
            Collections.rotate(inputList, n);
        } else if (n < 0) {
            int shift = -n % inputList.size();
            Collections.rotate(inputList, shift == 0 ? -(inputList.size()) : -shift);
        }
        return inputList;
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

    /*
      Задание подразумевает создание класса(ов) для выполнения задачи.

      Дан список студентов. Элемент списка содержит фамилию, имя, отчество, год рождения,
      курс, номер группы, оценки по пяти предметам. Заполните список и выполните задание.
      Упорядочите студентов по курсу, причем студенты одного курса располагались
      в алфавитном порядке. Найдите средний балл каждой группы по каждому предмету.
      Определите самого старшего студента и самого младшего студентов.
      Для каждой группы найдите лучшего с точки зрения успеваемости студента.
     */
}
