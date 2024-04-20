package ru.faimizufarov.simbirtraining.old_tasks_tests;

import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.studentcollection.ClassTraining;
import ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.studentcollection.Student;

public class ClassTrainingTest {

    private List<Student> students = ClassTraining.createStudentsList();

    @Test
    public void showSortedStudents_assertNothing() {
        ClassTraining.showSortedStudents(students);
    }

    @Test
    public void showAverageGradeForSubjectsInGroup_assertNothing() {
        ClassTraining.showAvgGradeForEverySubjectInEveryGroup(students);
    }

}