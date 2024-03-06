package ru.faimizufarov.simbirtraining.java.old_tasks.classes.task4;

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