package ru.faimizufarov.simbirtraining.classes.task2;

/*
      II

      Создать класс, содержащий динамический массив и количество элементов в нем.
      Добавить конструктор, который выделяет память под заданное количество элементов.
      Добавить методы, позволяющие заполнять массив случайными числами,
      переставлять в данном массиве элементы в случайном порядке, находить количество
      различных элементов в массиве, выводить массив на экран.

*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

class ArrayFlow {
    ArrayList<Integer> arrayList;
    int sizeOfArray = 0;
    ArrayFlow(int sizeOfArray) {
        this.sizeOfArray = sizeOfArray;
        arrayList = new ArrayList<>(this.sizeOfArray);
    }

    ArrayList<Integer> fillArray() {
        Random random = new Random();
        for (int i = 0; i <= this.sizeOfArray; i++) {
            arrayList.set(i, random.nextInt(100));
        }
        return arrayList;
    }

    ArrayList<Integer> shuffleArray() {
        Collections.shuffle(arrayList);
        return arrayList;
    }

    int uniqueElements() {
        HashSet<Integer> hastSet = new HashSet<>(arrayList);
        return hastSet.size();
    }

    void printArray() {
        System.out.println(arrayList);
    }
}