package ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.studentcollection;

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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassTraining {

    public static void main(String[] args) {
        ArrayList<Student> arrayOfStudents = createStudentsList();
        showSortedStudents(arrayOfStudents);
        showAvgGradeForEverySubjectInEveryGroup(arrayOfStudents);
        showOldestStudent(arrayOfStudents);
        showYoungestStudent(arrayOfStudents);
        showTheBestStudentInEveryGroup(arrayOfStudents);
    }

    public static ArrayList<Student> createStudentsList() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(
                "Зуфаров",
                "Файми",
                "Полатович",
                2001,
                4402,
                new int[]{5, 5, 5, 5, 5}
        ));
        students.add(new Student(
                "Зайцев",
                "Александр",
                "Сергеевич",
                2002,
                4402,
                new int[]{4, 5, 5, 5, 5}
        ));
        students.add(new Student(
                "Абдусаматов",
                "Нематулло",
                "Азизуллаевич",
                2002,
                4402,
                new int[]{4, 5, 4, 5, 4}
        ));
        students.add(new Student("Жирнов",
                "Даниил",
                "Андреевич",
                2002,
                4402,
                new int[]{4, 5, 3, 3, 2}
        ));
        students.add(new Student(
                "Третьяченко",
                "Ярослава",
                "Дмитриевна",
                2002,
                4402,
                new int[]{4, 5, 4, 4, 5}
        ));
        students.add(new Student(
                "Барабошкина",
                "Ангелина",
                "Владимировна",
                2002,
                4402,
                new int[]{4, 5, 5, 5, 5}
        ));
        students.add(new Student(
                "Рыбала",
                "Алексей",
                "Николаевич",
                2003,
                8802,
                new int[]{3, 3, 4, 2, 4}
        ));
        students.add(new Student(
                "Бахтулджамолов",
                "Богдан",
                "Гулрухович",
                2000,
                6302,
                new int[]{4, 5, 3, 4, 5}
        ));
        students.add(new Student(
                "Баранова",
                "Мария",
                "Андреевна",
                2002,
                4402,
                new int[]{4, 5, 4, 4, 5}
        ));
        students.add(new Student(
                "Валеева",
                "Диана",
                "Фаридовна",
                2003,
                8806,
                new int[]{5, 5, 4, 5, 4}
        ));
        return students;
    }

    public static void showSortedStudents(List<Student> arrayOfStudents) {
        StudentGroupComparator studentGroupComparator = new StudentGroupComparator();
        StudentNameComparator studentNameComparator = new StudentNameComparator();
        Comparator<Student> studentComparator =
                studentGroupComparator.thenComparing(studentNameComparator);
        ArrayList<Student> sortedStudents = new ArrayList<>(arrayOfStudents);
        sortedStudents.sort(studentComparator);
        for (int i = 0; i < sortedStudents.size(); i++) {
            System.out.println(sortedStudents.get(i).toString());
        }
    }


    public static void showAvgGradeForEverySubjectInEveryGroup(List<Student> arrayOfStudents) {
        Set<GroupGrades> groupGrades = arrayOfStudents.stream()
                .map(student -> student.group)
                .map(group -> new GroupGrades(arrayOfStudents, group))
                .collect(Collectors.toSet());

        StringBuilder stringBuilder = new StringBuilder();
        for (GroupGrades groupGrade : groupGrades) {
            stringBuilder.append(
                    String.format("For group %d average groupGrades are:", groupGrade.group)
            );
            for (int i = 0; i < 5; i++) {
                stringBuilder.append(
                        String.format("\tSubject %d: %.2f", i, groupGrade.averageGrades.get(i))
                );
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder.toString());
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
