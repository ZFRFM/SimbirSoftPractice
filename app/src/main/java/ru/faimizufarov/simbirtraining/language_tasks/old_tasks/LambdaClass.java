package ru.faimizufarov.simbirtraining.language_tasks.old_tasks;


/*

Написать простое лямбда-выражение в переменной myClosure,
лямбда-выражение должно выводить в консоль фразу "I love Java".
Вызвать это лямбда-выражение. Далее написать функцию, которая будет
запускать заданное лямбда-выражение заданное количество раз.
Объявить функцию так: public void repeatTask (int times, Runnable task).
Функция должна запускать times раз лямбда-выражение task .
Используйте эту функцию для печати "I love Java" 10 раз.

*/

public class LambdaClass {
    public static void main(String[] args) {
        Javable myClosure = System.out::println;
        myClosure.print("I love Java");
        Runnable task = () -> {
            myClosure.print("I love Java");
        };
        repeatTask(10, task);
    }

    public static void repeatTask (int times, Runnable task) {
        for (int i = 0; i < times; i++) {
            task.run();
        }
    }
}

interface Javable {
    void print(String s);
}