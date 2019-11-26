package iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Solution0101 {
    public static void main(String[] args) {

        List<String> lots1 = new ArrayList<>();

        List<Lot> lots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Lot lot = new Lot(i);
            Shipment shipment = new Shipment(lot);
            Delivery delivery = new Delivery(shipment);
            Order order = new Order(delivery);
            lots.add(lot);
        }

    }
}

class Order {
    Delivery delivery;

    public Order(Delivery delivery) {
        this.delivery = delivery;
    }
}

class Shipment {
    Lot lot;

    public Shipment(Lot lot) {
        this.lot = lot;
    }
}

class Delivery {
    Shipment shipment;

    public Delivery(Shipment shipment) {
        this.shipment = shipment;
    }
}

class Lot {
    int sum;

    public Lot(int i) {
        this.sum = i;
    }
}

