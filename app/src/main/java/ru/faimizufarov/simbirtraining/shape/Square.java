package ru.faimizufarov.simbirtraining.shape;

public class Square implements Shape {

    int lengthOfSide = 0;
    double perimeter = 0.0;
    double area = 0.0;

    Square(int lengthOfSide) {
        this.lengthOfSide = lengthOfSide;
        perimeter = lengthOfSide * 4.0;
        area = (double) (lengthOfSide * lengthOfSide);
    }
}
