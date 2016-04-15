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
    private int mImage;
    private double  mPrice;
    private LinkedList<Allergen> mAllegens;

    // Getters and Setters.
    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImage() {
        return mImage;
    }

    public double getPrice() {
        return mPrice;
    }

    public LinkedList<Allergen> getAllegens() {
        return mAllegens;
    }

    // Constructors.
    public Plate(String name, String description, int image, double price, LinkedList<Allergen> allergens) {
        mName = name;
        mDescription = description;
        mImage = image;
        mPrice = price;
        mAllegens = allergens;
    }
}
