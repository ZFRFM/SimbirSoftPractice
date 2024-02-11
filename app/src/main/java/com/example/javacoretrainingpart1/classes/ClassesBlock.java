package com.example.javacoretrainingpart1.classes;

import android.hardware.GeomagneticField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Набор заданий по работе с классами в java.
 * <p>
 * Задания подразумевают создание класса(ов), выполняющих задачу.
 * <p>
 * Проверка осуществляется ментором.
 */
public interface ClassesBlock {

    /*
      I

      Создать класс с двумя переменными. Добавить функцию вывода на экран
      и функцию изменения этих переменных. Добавить функцию, которая находит
      сумму значений этих переменных, и функцию, которая находит наибольшее
      значение из этих двух переменных.

      Этот код для класса ниже работает:
      public static void main(String[] args) {
          MathOperations check = new MathOperations(4, 5);
          check.displayNumbers();
          check.changeNumbers(3,7);
          check.displayNumbers();
          System.out.println(check.sumOfNumbers());
          System.out.println(check.maxValue());
      }

     */

    class MathOperations {
        int firstNumber = 0;
        int secondNumber = 0;

        MathOperations(int firstNumber, int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }

        void displayNumbers() {
            System.out.printf("First number: %d, Second number: %d\n", firstNumber, secondNumber);
        }

        void changeNumbers(int firstNumber, int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }

        int sumOfNumbers() {
            return this.firstNumber + this.secondNumber;
        }

        int maxValue() {
            return Math.max(this.firstNumber, this.secondNumber);
        }
    }

    /*
      II

      Создать класс, содержащий динамический массив и количество элементов в нем.
      Добавить конструктор, который выделяет память под заданное количество элементов.
      Добавить методы, позволяющие заполнять массив случайными числами,
      переставлять в данном массиве элементы в случайном порядке, находить количество
      различных элементов в массиве, выводить массив на экран.
     */

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

    /*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
     */

    class Triangle {
        private double side1 = 1.0;
        private double side2 = 1.0;
        private double side3 = 1.0;

        Triangle(double side1, double side2, double side3) {
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }

        double getSide1() {
            return side1;
        }

        double getSide2() {
            return side2;
        }

        double getSide3() {
            return side3;
        }

        double perimeterOfTriangle() {
            return side1 + side2 + side3;
        }

        double areaOfTriangle() {
            double perim = this.perimeterOfTriangle();
            return Math.sqrt((perim * (perim -  side1) * (perim - side2) * (perim - side3)));
        }
        // для определния точки пересечения медиан нужно вводить
        // координаты -> полностью переписывать класс

    }

    /*
      IV

      Составить описание класса для представления времени.
      Предусмотреть возможности установки времени и изменения его отдельных полей
      (час, минута, секунда) с проверкой допустимости вводимых значений.
      В случае недопустимых значений полей выбрасываются исключения.
      Создать методы изменения времени на заданное количество часов, минут и секунд.
     */

    class Time {
        private int hour = 0;
        private int minute = 0;
        private int second = 0;

        void setTime(int hour, int minute, int second) throws Exception {
            setHour(hour);
            setMinute(minute);
            setSecond(second);
        }

        void setHour(int hour) throws Exception {
            if (hour > 24 || hour < 0) {
                throw new Exception("Некорректное значение");
            }
            else {
                this.hour = hour;
            }
        }

        void setMinute(int minute) throws Exception {
            if (minute > 60 || minute < 0) {
                throw new Exception("Некорректное значение");
            }
            else {
                this.minute = minute;
            }
        }

        void setSecond(int second) throws Exception {
            if (second > 60 || second < 0) {
                throw new Exception("Некорректное значение");
            }
            else {
                this.second = second;
            }
        }

        void showTime() {
            System.out.printf("Time - %d:%d:%d", hour, minute, second);
        }
    }

    /*
      V

      Класс Абонент: Идентификационный номер, Фамилия, Имя, Отчество, Адрес,
      Номер кредитной карточки, Дебет, Кредит, Время междугородных и городских переговоров;
      Конструктор; Методы: установка значений атрибутов, получение значений атрибутов,
      вывод информации. Создать массив объектов данного класса.
      Вывести сведения относительно абонентов, у которых время городских переговоров
      превышает заданное.  Сведения относительно абонентов, которые пользовались
      междугородной связью. Список абонентов в алфавитном порядке.
     */

    class User {
        private long id;
        private String surname, name, lastName, address;
        private int creditCard, debet, credit, minOfInternal, minOfInternational;

        User(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(int creditCard) {
            this.creditCard = creditCard;
        }

        public int getDebet() {
            return debet;
        }

        public void setDebet(int debet) {
            this.debet = debet;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getMinOfInternal() {
            return minOfInternal;
        }

        public void setMinOfInternal(int minOfInternal) {
            this.minOfInternal = minOfInternal;
        }

        public int getMinOfInternational() {
            return minOfInternational;
        }

        public void setMinOfInternational(int minOfInternational) {
            this.minOfInternational = minOfInternational;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", surname='" + surname + '\'' +
                    ", name='" + name + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", address='" + address + '\'' +
                    ", creditCard=" + creditCard +
                    ", debet=" + debet +
                    ", credit=" + credit +
                    ", minOfInternal=" + minOfInternal +
                    ", minOfInternational=" + minOfInternational +
                    '}';
        }

        void printUserInfo() {
            System.out.println(this);
        }
    }

    /*
      VI

      Задача на взаимодействие между классами. Разработать систему «Вступительные экзамены».
      Абитуриент регистрируется на Факультет, сдает Экзамены. Преподаватель выставляет Оценку.
      Система подсчитывает средний бал и определяет Абитуриента, зачисленного в учебное заведение.
     */



    /*
      VII

      Задача на взаимодействие между классами. Разработать систему «Интернет-магазин».
      Товаровед добавляет информацию о Товаре. Клиент делает и оплачивает Заказ на Товары.
      Товаровед регистрирует Продажу и может занести неплательщика в «черный список».
     */
}
