package ru.faimizufarov.simbirtraining.studentcollection;

import java.util.Comparator;

public class StudentNameComparator implements Comparator<Student> {

    public int compare(Student a, Student b){
        return a.surname.compareTo(b.surname);
    }

}
