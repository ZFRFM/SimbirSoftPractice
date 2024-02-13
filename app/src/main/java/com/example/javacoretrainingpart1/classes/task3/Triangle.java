package com.example.javacoretrainingpart1.classes.task3;

/*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
     */

class Triangle {
    private double side1 = 1.0;
    private double side2 = 1.0;
    private double side3 = 1.0;

    Triangle(double side1, double side2, double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    double getSide1() {
        return side1;
    }

    double getSide2() {
        return side2;
    }

    double getSide3() {
        return side3;
    }

    double perimeterOfTriangle() {
        return side1 + side2 + side3;
    }

    double areaOfTriangle() {
        double perim = this.perimeterOfTriangle();
        return Math.sqrt((perim * (perim -  side1) * (perim - side2) * (perim - side3)));
    }
    // для определния точки пересечения медиан нужно вводить
    // координаты -> полностью переписывать класс(

}
