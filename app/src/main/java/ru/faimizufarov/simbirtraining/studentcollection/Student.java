package ru.faimizufarov.simbirtraining.studentcollection;

/*
      Задание подразумевает создание класса(ов) для выполнения задачи.

      Дан список студентов. Элемент списка содержит фамилию, имя, отчество, год рождения,
      курс, номер группы, оценки по пяти предметам. Заполните список и выполните задание.
      Упорядочите студентов по курсу, причем студенты одного курса располагались
      в алфавитном порядке. Найдите средний балл каждой группы по каждому предмету.
      Определите самого старшего студента и самого младшего студентов.
      Для каждой группы найдите лучшего с точки зрения успеваемости студента.
 */

import java.util.Arrays;

public class Student {

    String surname;
    String name;
    String patronymic;

    int yearOfBirth;

    int group;

    int[] fiveSubjectsGrades = new int[5];

    double avgGrade = 0.0;

    Student(String surname, String name, String patronymic,
            int yearOfBirth, int group, int[] fiveSubjectsGrades
    ) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.yearOfBirth = yearOfBirth;
        this.group = group;
        this.fiveSubjectsGrades = fiveSubjectsGrades;
    }

    int getGroup() {
        return this.group;
    }

    String getSurname() {
        return this.surname;
    }

    int getYearOfBirth() {
        return this.yearOfBirth;
    }

    double getAvgGrade() {
        this.avgGrade = Arrays.stream(this.fiveSubjectsGrades).sum() / 5.0;
        return this.avgGrade;
    }
}