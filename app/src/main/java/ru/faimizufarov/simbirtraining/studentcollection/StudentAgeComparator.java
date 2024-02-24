package ru.faimizufarov.simbirtraining.studentcollection;

import java.util.Comparator;

public class StudentAgeComparator implements Comparator<Student> {

    public int compare(Student a, Student b) {
        return Integer.compare(a.getYearOfBirth(), b.getYearOfBirth());
    }

}
