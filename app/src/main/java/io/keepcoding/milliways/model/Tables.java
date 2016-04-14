package io.keepcoding.milliways.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by alberto on 12/4/16.
 *  * Class for definition of an array of tables
 */
public class Tables implements Serializable {

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
    public Tables() {

        // Create list of tables.
        mTables = new LinkedList<>();

        // Add Table to Tables.
        mTables.add(new Table("Mesa 1"));
        mTables.add(new Table("Mesa 2"));
        mTables.add(new Table("Mesa 3"));
        mTables.add(new Table("Mesa 4"));
    }
}

