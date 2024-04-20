package ru.faimizufarov.simbirtraining.java.java_tasks.old_tasks.classes.task7;

public class Expert {

    void addInfo(Product product, String name, int quantity) {
        product.name = name;
        product.quantity = quantity;
        System.out.println("addInfo() прошёл успешно");
    }

    void registerSale(Sale sale, String time) {
        sale.time = time;
        System.out.println("registerSale() прошёл успешно");
    }

    void blockClient(Client client, Order order) {
        client.blocked = !order.paid;
        System.out.println("blockClient() прошёл успешно");
    }

}
