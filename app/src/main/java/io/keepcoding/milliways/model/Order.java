package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Order extends Plate implements Serializable {

    // Attributes.
    private String mChanges;

    // Constructors.
    public Order(String name, String description, int image, double price, LinkedList<Allergen> allergens, String changes) {
        super(name, description, image, price, allergens);
        mChanges = changes;
    }

    public Order(Plate plate, String changes) {
        super(plate);
        mChanges = changes;
    }
}
