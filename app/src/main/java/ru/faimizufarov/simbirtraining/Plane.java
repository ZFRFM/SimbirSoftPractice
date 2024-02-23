package ru.faimizufarov.simbirtraining;

import kotlin.Pair;
/*

Условия: есть начальная позиция на двумерной плоскости,
можно осуществлять последовательность шагов по четырем направлениям up, down, left, right.
Размерность каждого шага равна 1.
Задание: Создать enum Directions с возможными направлениями движения
Создать метод, принимающий координаты и одно из направлений и
возвращающий координаты после перехода по направлению
Создать метод, осуществляющий несколько переходов от первоначальных координат и
выводящий координаты после каждого перехода. Для этого внутри метода следует определить
переменную location с начальными координатами (0,0) и массив направлений,
содержащий элементы [up, up, left, down, left, down, down, right, right, down, right], и
програмно вычислить какие будут координаты у переменной location после выполнения
этой последовательности шагов. Для вычисленеия результата каждого перемещения следует
использовать созданный ранее метод перемещения на один шаг.


    Рабочий код:
    public static void main(String[] args) {
        Plane plane = new Plane();
        plane.oneStep(plane.coordinates, Direction.LEFT);
        plane.oneStep(plane.coordinates, Direction.DOWN);
        plane.oneStep(plane.coordinates, Direction.RIGHT);
        plane.oneStep(plane.coordinates, Direction.UP);
        System.out.println();
        plane.fewSteps();
    }

*/

public class Plane {

    Pair<Integer, Integer> coordinates = new Pair<>(3,3);
    Pair<Integer, Integer> oneStep(Pair<Integer, Integer> coordinates, Direction direction) {
        Pair<Integer, Integer> localCoordinates = coordinates;
        switch (direction) {
            case UP:
                localCoordinates = new Pair<> (coordinates.component1(), coordinates.component2() + 1);
                break;
            case DOWN:
                localCoordinates = new Pair<> (coordinates.component1(), coordinates.component2() - 1);
                break;
            case LEFT:
                localCoordinates = new Pair<> (coordinates.component1() - 1, coordinates.component2());
                break;
            case RIGHT:
                localCoordinates = new Pair<> (coordinates.component1() + 1, coordinates.component2());
                break;
        }
        System.out.println(localCoordinates);
        this.coordinates = localCoordinates;
        return localCoordinates;
    }

    void fewSteps() {
        Pair<Integer, Integer> location = new Pair<>(0,0);
        Direction[] directionsArray = new Direction[]{
                Direction.UP,
                Direction.UP,
                Direction.LEFT,
                Direction.DOWN,
                Direction.LEFT,
                Direction.DOWN,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.RIGHT,
                Direction.DOWN,
                Direction.RIGHT
        };
        for (int i = 0; i < directionsArray.length; i++) {
            location = oneStep(location, directionsArray[i]);
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}