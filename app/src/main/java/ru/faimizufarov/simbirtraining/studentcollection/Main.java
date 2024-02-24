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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ArrayList<Student> arrayOfStudents = new ArrayList<Student>();
        fillTheList(arrayOfStudents);
        showSortedStudents(arrayOfStudents);
        showAvgGradeForEverySubjectInEveryGroup(arrayOfStudents);
        showOldestStudent(arrayOfStudents);
        showYoungestStudent(arrayOfStudents);
        showTheBestStudentInEveryGroup(arrayOfStudents);
    }

    static void fillTheList(ArrayList<Student> arrayOfStudents) {
        arrayOfStudents.add(new Student("Зуфаров", "Файми", "Полатович", 2001, 4402, new int[]{5, 5, 5, 5, 5}));
        arrayOfStudents.add(new Student("Зайцев", "Александр", "Сергеевич", 2002, 4402, new int[]{4, 5, 5, 5, 5}));
        arrayOfStudents.add(new Student("Абдусаматов", "Нематулло", "Азизуллаевич", 2002, 4402, new int[]{4, 5, 4, 5, 4}));
        arrayOfStudents.add(new Student("Жирнов", "Даниил", "Андреевич", 2002, 4402, new int[]{4, 5, 3, 3, 2}));
        arrayOfStudents.add(new Student("Третьяченко", "Ярослава", "Дмитриевна", 2002, 4402, new int[]{4, 5, 4, 4, 5}));
        arrayOfStudents.add(new Student("Барабошкина", "Ангелина", "Владимировна", 2002, 4402, new int[]{4, 5, 5, 5, 5}));
        arrayOfStudents.add(new Student("Рыбала", "Алексей", "Николаевич", 2003, 8802, new int[]{3, 3, 4, 2, 4}));
        arrayOfStudents.add(new Student("Бахтулджамолов", "Богдан", "Гулрухович", 2000, 6302, new int[]{4, 5, 3, 4, 5}));
        arrayOfStudents.add(new Student("Баранова", "Мария", "Андреевна", 2002, 4402, new int[]{4, 5, 4, 4, 5}));
        arrayOfStudents.add(new Student("Валеева", "Диана", "Фаридовна", 2003,  8806, new int[]{5, 5, 4, 5, 4}));
    }

    static void showSortedStudents(ArrayList<Student> arrayOfStudents) {
        arrayOfStudents.sort(new StudentGroupComparator().thenComparing(new StudentNameComparator()));
        for (int i = 0; i < arrayOfStudents.size(); i++) {
            System.out.println(arrayOfStudents.get(i).surname);
        }
    }

    static void showAvgGradeForEverySubjectInEveryGroup(ArrayList<Student> arrayOfStudents) {
        ArrayList<Integer> addedGroups = new ArrayList<>();
        arrayOfStudents.sort(new StudentGroupComparator());
        int[] avgGrades = new int[5];
        for (int i = 0; i < arrayOfStudents.size(); i++) {
            while (!addedGroups.contains(arrayOfStudents.get(i).group)) {
                addedGroups.add(arrayOfStudents.get(i).group);
            }
        }
        for (int j = 0; j < avgGrades.length; j++) {
            for (int i = 0; i < addedGroups.size(); i++) {
                final int finalI = i;
                ArrayList<Student> currentlyGroupStudents =
                        (ArrayList<Student>) arrayOfStudents.stream()
                                .filter(p -> p.group == addedGroups.get(finalI)).collect(Collectors.toList());
                for (int z = 0; z < currentlyGroupStudents.size(); z++) {
                    avgGrades[j] = avgGrades[j] + currentlyGroupStudents.get(z).fiveSubjectsGrades[j];
                }
                for (int k = 0; k < avgGrades.length; k++) {
                    avgGrades[k] = avgGrades[k] / 5;
                }
            }
        }
        for (int z = 0; z < addedGroups.size(); z++) {
            System.out.println(addedGroups.get(z) + Arrays.toString(avgGrades));
        }
    }

    static void showOldestStudent(ArrayList<Student> arrayOfStudents) {
        arrayOfStudents.sort(new StudentAgeComparator());
        System.out.println(arrayOfStudents.get(0).surname);
    }

    static void showYoungestStudent(ArrayList<Student> arrayOfStudents) {
        arrayOfStudents.sort(new StudentAgeComparator());
        System.out.println(arrayOfStudents.get(arrayOfStudents.size() - 1).surname);
    }

    static void showTheBestStudentInEveryGroup(ArrayList<Student> arrayOfStudents) {
        arrayOfStudents.sort(new StudentSumOfGradesComparator());
        ArrayList<Student> bestStudents = new ArrayList<>();
        ArrayList<Integer> addedGroups = new ArrayList<>();
        for (int i = 0; i < arrayOfStudents.size(); i++) {
            while (!addedGroups.contains(arrayOfStudents.get(i).group)) {
                bestStudents.add(arrayOfStudents.get(i));
                addedGroups.add(arrayOfStudents.get(i).group);
            }
        }
        for (int i = 0; i < bestStudents.size(); i++) {
            System.out.println(bestStudents.get(i).surname);
        }
    }
}