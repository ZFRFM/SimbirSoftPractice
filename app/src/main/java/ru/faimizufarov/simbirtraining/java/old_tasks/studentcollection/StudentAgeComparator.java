package ru.faimizufarov.simbirtraining.java.old_tasks.studentcollection;

import java.util.Comparator;

public class StudentAgeComparator implements Comparator<Student> {

    public int compare(Student a, Student b) {
        return Integer.compare(a.yearOfBirth, b.yearOfBirth);
    }

}
