package ru.faimizufarov.simbirtraining.language_tasks.old_tasks.studentcollection;

import java.util.Comparator;

class StudentSumOfGradesComparator implements Comparator<Student> {

    @Override
    public int compare(Student b, Student a) {
        return Double.compare(a.getAvgGrade(), b.getAvgGrade());
    }

}