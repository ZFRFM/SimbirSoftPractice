package com.example.javacoretrainingpart1.shape;

public class Rectangle implements Shape {

    int width = 0;
    int length = 0;
    double perimeter = 0.0;
    double area = 0.0;

    Rectangle(int width, int length) {
        this.width = width;
        this.length = length;
        perimeter = (width + length) * 2.0;
        area = (double) (width * length);
    }
}