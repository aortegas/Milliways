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
    private LinkedList<Plate> mPlates;

    // Getter y Setter.
    public String getName() {
        return mName;
    }
    public LinkedList<Plate> getPlates() {
        return mPlates;
    }

    // Constructors.
    public Table(String name) {
        mName = name;
        mPlates = new LinkedList<>();
    }

//    public Table(String name, LinkedList<Plate> plates) {
//        mName = name;
//        mPlates = plates;
//    }
//
    // Methods.
    public void addPlateToTable(Plate plate) {
        this.mPlates.add(plate);
    }

    // Override toString to show de table name at the list.
    public String toString() {
        return getName();
    }
}
