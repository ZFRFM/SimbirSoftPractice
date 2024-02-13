package com.example.javacoretrainingpart1.classes.task1;

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

// Тут лучше использовать статические поля и методы, т.к. экземпляр MathOperations не обязателен,
// но понял Я это чуть позже, чем написал класс
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

