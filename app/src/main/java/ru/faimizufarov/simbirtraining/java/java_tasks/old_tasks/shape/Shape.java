package ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.shape;


/*

Создать интерфейс Shape с двумя методами perimeter и area,
выводящими периметр и площадь фигуры соответственно,
после чего реализовать и использовать для вывода
периметра и площади следующие классы, реализующие интерфейс Shape:
Rectangle - прямоугольник с двумя свойствами: ширина и длина
Square - квадрат с одним свойством: длина стороны
Circle - круг с одним свойством: диаметр круга

*/
public interface Shape {
    default void perimeter(double perimeter) {
        System.out.println(perimeter);
    }
    default void area(double area) {
        System.out.println(area);
    }
}

/*
Пример рабочего кода для системы:
public class Main {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(3, 7);
        Square square = new Square(5);
        Circle circle = new Circle(7.5);
        rectangle.perimeter(rectangle.perimeter);
        rectangle.area(rectangle.area);
        square.perimeter(square.perimeter);
        square.area(square.area);
        circle.perimeter(circle.perimeter);
        circle.area(circle.area);
    }
}
*/