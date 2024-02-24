package ru.faimizufarov.simbirtraining.classes.task6;

/*
      VI

      Задача на взаимодействие между классами. Разработать систему «Вступительные экзамены».
      Абитуриент регистрируется на Факультет, сдает Экзамены. Преподаватель выставляет Оценку.
      Система подсчитывает средний бал и определяет Абитуриента, зачисленного в учебное заведение.
*/

/*
        Абитуриент - Applicant
        Экзамен - Exam
        Факультет - Faculty
        Оценка - Grade
        Преподаватель - Teacher
*/

public class EntranceExamsSystem {
    void whatAboutHim(Applicant testedApplicant) {
        double grade = ((double)testedApplicant.gradeMath.value
                        + testedApplicant.gradeRus.value) / 2;
        if (grade > 3.0 && grade <= 5.0) {
            System.out.println("You are welcome!");
        }
        else if (grade > 1.0 && grade <= 3.0) {
            System.out.println("Learn more!");
        }
        else {
            System.out.println("Value is not applicable");
        }
    }
}
