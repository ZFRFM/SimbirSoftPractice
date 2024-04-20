package ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.classes.task6;

public class Teacher {

    Teacher(Applicant applicant) {

    }

    void setMathGrade(Applicant applicant) {
        applicant.gradeMath.value = setGrade(applicant.examMath.score);
    }

    void setRusGrade(Applicant applicant) {
        applicant.gradeRus.value = setGrade(applicant.examRus.score);
    }

    int setGrade(int score) {
        if (score >= 85 && score <= 100) {
            return 5;
        }
        else if (score >= 70 && score < 85) {
            return 4;
        }
        else if (score >= 50 && score < 70) {
            return 3;
        }
        else if (score >= 0 && score < 50) {
            return 2;
        }
        else {
            return 0;
        }
    }
}
