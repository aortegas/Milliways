package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by alberto on 12/4/16.
 * Class for definition of an array of plates
 */
public class Table implements Serializable {

    // Attributes.
    private String mName;
    private LinkedList<Order> mOrders;

    // Getter y Setter.
    public String getName() {
        return mName;
    }

    public LinkedList<Order> getOrders() {
        return mOrders;
    }

    // Constructors.
    public Table(String name) {
        mName = name;
        mOrders = new LinkedList<>();
    }

    // Methods.
    public void addOrderToTable(Order order) {
        this.mOrders.add(order);
    }

    // Override toString to show de table name at the list.
    public String toString() {
        return getName();
    }
}
