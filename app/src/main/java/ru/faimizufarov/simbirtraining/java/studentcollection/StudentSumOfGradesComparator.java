package ru.faimizufarov.simbirtraining.java.studentcollection;

import java.util.Comparator;

class StudentSumOfGradesComparator implements Comparator<Student> {

    @Override
    public int compare(Student b, Student a) {
        return Double.compare(a.getAvgGrade(), b.getAvgGrade());
    }

}