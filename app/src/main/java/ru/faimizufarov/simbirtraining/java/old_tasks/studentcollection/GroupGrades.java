package ru.faimizufarov.simbirtraining.java.studentcollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class GroupGrades {

    public int group;

    public Map<Integer, Double> averageGrades;

    GroupGrades(List<Student> students, int groupNumber) {
        group = groupNumber;
        List<Student> groupStudents = students.stream()
                .filter(student -> student.group == groupNumber)
                .collect(Collectors.toList());
        Map<Integer, Integer> subjectGradeSum = new HashMap<>();
        for (Student student : groupStudents) {
            for (int i = 0; i < 5; i++) {
                int currentGrade = subjectGradeSum.getOrDefault(i, 0);
                subjectGradeSum.put(i, currentGrade + student.fiveSubjectsGrades[i]);
            }
        }
        averageGrades = new HashMap<>();
        for (int key : subjectGradeSum.keySet()) {
            averageGrades.put(key, subjectGradeSum.get(key) / (double) groupStudents.size());
        }
    }

}
