package ru.faimizufarov.simbirtraining.language_tasks.old_tasks.classes.task6;

public class Applicant {

    String name = "";
    int id = -1;
    Exam examMath = new Exam("", 0);
    Exam examRus = new Exam("", 0);

    Faculty faculty = new Faculty("Ещё не зарегистрирован");
    Grade gradeMath = new Grade("Math", 0);
    Grade gradeRus = new Grade("Rus", 0);

    Applicant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    void registerOnFaculty(String name) {
        faculty = new Faculty(name);
    }

    void passMath(String name, int score) {
        examMath = new Exam(name, score);
    }

    void passRus(String name, int score) {
        examRus = new Exam(name, score);
    }
}