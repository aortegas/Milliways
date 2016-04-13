package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by alberto on 12/4/16.
 * Class for definition of a plate.
 */
public class Plate implements Serializable {

    // Attributes.
    private String mName;
    private String mDescription;
    private String mImage;
    private double  mPrice;
    private LinkedList<Allergen> mAllegens;

    // Getter y Setter.
    public String getName() {
        return mName;
    }

    // Constructors.
    public Plate(String name) {
        mName = name;
        mDescription = "";
        mImage = "";
        mPrice = 0.0;
        mAllegens = new LinkedList<Allergen>();
    }

    // Override toString to show de table name at the list.
    public String toString() {
        return getName();
    }
}
