package ru.faimizufarov.simbirtraining.classes.task3;

/*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
*/

class Triangle {

    public Point a;
    public Point b;
    public Point c;

    double getAB() {
        return a.distanceTo(b);
    }

    double getAC() {
        return a.distanceTo(c);
    }

    double getBC() {
        return a.distanceTo(c);
    }

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    double getPerimeterOfTriangle() {
        return getAB() + getBC() + getAC();
    }

    double getAreaOfTriangle() {
        double perim = this.getPerimeterOfTriangle();
        return Math.sqrt((perim * (perim - getAB()) * (perim - getAC()) * (perim - getBC())));
    }

    Point getMedianIntersectionPoint() {
        double x = (a.x + b.x + c.x) / 3.0;
        double y = (a.y + b.y + c.y) / 3.0;
        return new Point(x, y);
    }
}
