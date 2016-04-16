package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Restaurant implements Serializable {

    // Attributes.
    private LinkedList<Table> mTables;

    // Getter y Setter.
    public LinkedList<Table> getTables() {
        return mTables;
    }

    public Table getTableAtIndex(int position) {
        return mTables.get(position);
    }

    public void setTableAtIndex(Table table, int position) {
        mTables.set(position, table);
    }

    // Constructors.
    public Restaurant() {

        // Create list of tables.
        mTables = new LinkedList<>();

        // Add Table to Restaurant.
        mTables.add(new Table("Mesa 1"));
        mTables.add(new Table("Mesa 2"));
        mTables.add(new Table("Mesa 3"));
        mTables.add(new Table("Mesa 4"));
    }
}

