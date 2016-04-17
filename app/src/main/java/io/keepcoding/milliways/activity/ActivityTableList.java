package io.keepcoding.milliways.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.LinkedList;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.fragment.FragmentOrderAdd;
import io.keepcoding.milliways.fragment.FragmentPlateCardList;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Table;

public class ActivityTableList extends AppCompatActivity implements FragmentPlateCardList.PlatesPagerListener, FragmentOrderAdd.OrderAddListener{

    // Declare variables for model.
    private Table mTableModel;
    private LinkedList<Plate> mPlatesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We load the view of activity.
        setContentView(R.layout.activity_table_list);

        // We retrieve information from the selected table of intent, with the public constant stated above.
        // If we don't have the information, we say charge the first table.
        // Get a data model. If data exits in a instance previous, we get from that.
        if (savedInstanceState == null) {
            mTableModel = (Table) getIntent().getSerializableExtra(Constant.EXTRA_TABLE_DATA);
            ArrayList arrayListTemp = (ArrayList) getIntent().getSerializableExtra(Constant.EXTRA_PLATES_DATA);
            mPlatesModel = new LinkedList<>(arrayListTemp);
        }
        else {
            mTableModel = (Table) savedInstanceState.getSerializable("mTableModel");
            mPlatesModel = (LinkedList<Plate>) savedInstanceState.getSerializable("mPlatesModel");
        }

        // We indicate the action bar a button to go back.
        // For this to work, we must implement the methods menu.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + mTableModel.getName());
        }

        // We go to load dinamically the fragment, with FragmentManager.
        //FragmentManager fragmentManager = getFragmentManager();
        FragmentManager fragmentManager = getFragmentManager();

        // If we are not associated fragment of times before (this method is executed with the rotation of the device),
        // We indicate the Fragment Manager to load it.
        if ((fragmentManager.findFragmentById(R.id.activity_table_list_frame_list_id) == null) &&
            (fragmentManager.findFragmentById(R.id.activity_table_list_frame_button_id) == null)) {

            // Transactions allow loading and removal of various fragment at the same time.
            // We inform the model with the tables to fragment.
            // Important: See the method transformOrdersToPlates() for get list of plates.
            fragmentManager.beginTransaction()
                .add(R.id.activity_table_list_frame_list_id, FragmentPlateCardList.newInstance(transformOrdersToPlates()))
                .add(R.id.activity_table_list_frame_button_id, new FragmentOrderAdd())
                .commit();
        }
    }

    // We implement this method, for save data before this object is destroyed, for example when there are rotation of device.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putSerializable("mTableModel", mTableModel);
        savedInstanceState.putSerializable("mPlatesModel", mPlatesModel);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Implements this method to show the menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Show the menu.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        // Return true, for indicate that we update the menu with a new option.
        return true;
    }

    // Implements this method, for a selections of a ActionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        // We identify the item back.
        if (item.getItemId() == android.R.id.home) {

            // Finally the activity and go back, return data modification.
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_TABLE_RESULT, mTableModel);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        // We identify the item menu settings.
        if (item.getItemId() == R.id.menu_settings_detail_table) {

            // We started the activity ActivitySettingsDetailTable to show a menu.
            Intent intent = new Intent(this, ActivitySettingsDetailTable.class);
            Bundle extras = new Bundle();

            // We included in an intent information of the plates from the table.
            extras.putSerializable(Constant.EXTRA_TABLE_DATA, mTableModel);
            intent.putExtras(extras);

            // We included in an intent information of the plates from the table.
            startActivity(intent);
            return true;
        }

        return superValue;
    }

    // Implements this method, for button back action.
    @Override
    public void onBackPressed() {

        // Finally the activity and go back, return data modification.
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_TABLE_RESULT, mTableModel);
        setResult(RESULT_OK, intent);
        finish();
    }

    // Implements the interface, to allow comunicate with our fragment
    @Override
    public void onPlateSelected(Plate plate, int position) {

        Log.v("ActivityTableList", "Se ha seleccionado el plato: " + plate.getName() + "En la posicion" + position);

    }

    // Implements the add order for table action.
    @Override
    public void onAddOrderToTable() {

        // We started the activity ActivityTableList to show a table plates.
        Intent intent = new Intent(this, ActivityPlateList.class);
        Bundle extras = new Bundle();

        // We included in an intent information of the plates from the table.
        extras.putSerializable(Constant.EXTRA_TABLE_DATA, mTableModel);
        extras.putSerializable(Constant.EXTRA_PLATES_DATA, mPlatesModel);
        intent.putExtras(extras);

        // We included in an intent information of the plates from the table.
        startActivityForResult(intent, Constant.REQUEST_TABLE);
    }

    // Implements this method, for update data model.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verify Request Code.
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_TABLE) {

                // Update the model.
                mTableModel = (Table) data.getSerializableExtra(Constant.EXTRA_TABLE_RESULT);

                // Update data of the adapter of fragment and reload list.
                FragmentPlateCardList fragmentPlateCardList = (FragmentPlateCardList) this.getFragmentManager().findFragmentById(R.id.activity_table_list_frame_list_id);
                fragmentPlateCardList.getAdapterPlateCardList().updateData(transformOrdersToPlates());
            }
        }
    }

    // Private methods.
    // Create a Linkedlist<Plate> object from a table model, for list.
    private LinkedList<Plate> transformOrdersToPlates() {

        // Create a LinkedList<Plate> Object.
        LinkedList<Plate> platesModel = new LinkedList<>();

        // Get all orders and transform to plates.
        for (int i = 0; i < mTableModel.getOrders().size(); i++) {

            Plate plateModel = mTableModel.getOrders().get(i).getPlate();
            platesModel.add(plateModel);
        }

        // Finally we return LinkedList<Plates>
        return platesModel;
    }
}
