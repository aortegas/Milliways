package io.keepcoding.milliways.model;

import java.io.Serializable;

public class Allergen implements Serializable {

    // Attributes.
    private String mName;
    private int mImage;

    // Getters and Setters.
    public String getName() {
        return mName;
    }

    public int getImage() {
        return mImage;
    }

    // Constructors.
    public Allergen(String name, int image) {
        mName = name;
        mImage = image;
    }
}
