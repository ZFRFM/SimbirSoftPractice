package ru.faimizufarov.simbirtraining.language_tasks.old_tasks.shape;

public class Circle implements Shape {

    double diameter = 0.0;
    double perimeter = 0;
    double area = 0;

    Circle(double diameter) {
        this.diameter = diameter;
        perimeter = diameter * 3.14;
        area = 3.14 * (diameter/2) * (diameter/2);
    }
}
