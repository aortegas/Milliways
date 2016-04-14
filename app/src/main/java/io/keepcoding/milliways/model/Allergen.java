package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by alberto on 12/4/16.
 * Class for definition of a Allergen
 */
public class Allergen implements Serializable {

    // Attributes.
    private String mName;
    private String mImage;

    // Constructors.
    public Allergen(String name, String image) {
        mName = name;
        mImage = image;
    }
}
