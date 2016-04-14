package io.keepcoding.milliways.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.fragment.FragmentPlatesAdd;
import io.keepcoding.milliways.fragment.FragmentPlatesPager;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Table;

public class ActivityTableList extends AppCompatActivity implements FragmentPlatesPager.PlatesPagerListener, FragmentPlatesAdd.PlatesAddListener{

    // Declare a variable for data of table.
    private Table mTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Associated view with fragment.
        setContentView(R.layout.activity_table_list);

        // We retrieve information from the selected table of intent, with the public constant stated above.
        // If we don't have the information, we say charge the first table.
        mTable = (Table) getIntent().getSerializableExtra(Constant.EXTRA_TABLE_DATA);

        // We indicate the action bar a button to go back.
        // For this to work, we must implement the methods menu.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + mTable.getName());
        }

        // We go to load dinamically the fragment, with FragmentManager.
        FragmentManager fragmentManager = getFragmentManager();

        // If we are not associated fragment of times before (this method is executed with the rotation of the device),
        // We indicate the Fragment Manager to load it.
        if ((fragmentManager.findFragmentById(R.id.activity_table_list_frame_list_id) == null) &&
            (fragmentManager.findFragmentById(R.id.activity_table_list_frame_button_id) == null)) {

            // Transactions allow loading and removal of various fragment at the same time.
            // We inform the model with the tables to fragment.
            fragmentManager.beginTransaction()
                    .add(R.id.activity_table_list_frame_list_id, FragmentPlatesPager.newInstance(mTable.getPlates()))
                    .add(R.id.activity_table_list_frame_button_id, new FragmentPlatesAdd())
                    .commit();
        }
    }

    // Implements this method, for a selections of a ActionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        // We identify the item back
        if (item.getItemId() == android.R.id.home) {

            // Finally the activity and go back, return data modification.
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_TABLE_RESULT, mTable);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return superValue;
    }

    // Implements the interface, to allow comunicate with our fragment
    @Override
    public void onPlateSelected(Plate plate, int posicion) {


        Log.v("ActivityTableList", "Se ha seleccionado el plato numero: " + posicion);

    }

    @Override
    public void onAddPlateToTable() {


        Log.v("ActivityTableList", "Se ha seleccionado añadir un nuevo plato");

//        Plate plate = new Plate("Plato nuevo");
//        mTable.addPlateToTable(plate);



    }
}
