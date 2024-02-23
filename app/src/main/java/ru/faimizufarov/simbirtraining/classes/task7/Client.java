package ru.faimizufarov.simbirtraining.classes.task7;

import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    boolean blocked = false;

    Order makeOrder(Order order, Product... products) {
        if (!blocked) {
            order.orderList = new ArrayList<>(Arrays.asList(products));
            System.out.println("makeOrder() прошёл успешно");
            return order;
        }
        else {
            System.out.println("Этот чувак заблочен");
            return new Order();
        }
    }


    boolean payOrder(Order order) {
        order.paid = true;
        System.out.println("payOrder() прошёл успешно");
        return order.paid;
    }

}
