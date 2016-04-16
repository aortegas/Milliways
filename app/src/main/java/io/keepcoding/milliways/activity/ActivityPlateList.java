package io.keepcoding.milliways.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.fragment.FragmentPlateCardList;
import io.keepcoding.milliways.model.Order;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Table;

public class ActivityPlateList extends AppCompatActivity implements FragmentPlateCardList.PlatesPagerListener {

    // Declare a variable for data of table.
    private Table mTableModel;
    private LinkedList<Plate> mPlatesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We load the view of activity.
        setContentView(R.layout.activity_plate_list);

        // We retrieve information from the selected table of intent, with the public constant stated above.
        // If we don't have the information, we say charge the first table.
        mTableModel = (Table) getIntent().getSerializableExtra(Constant.EXTRA_TABLE_DATA);
        ArrayList arrayListTemp = (ArrayList) getIntent().getSerializableExtra(Constant.EXTRA_PLATES_DATA);
        mPlatesModel = new LinkedList<>(arrayListTemp);

        // We indicate the action bar a button to go back.
        // For this to work, we must implement the methods menu.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + getString(R.string.PLATE_SELECTION));
        }

        // We go to load dinamically the fragment, with FragmentManager.
        FragmentManager fragmentManager = getFragmentManager();

        // If we are not associated fragment of times before (this method is executed with the rotation of the device),
        // We indicate the Fragment Manager to load it.
        if (fragmentManager.findFragmentById(R.id.activity_plate_list_frame_list_id) == null) {

            // Transactions allow loading and removal of various fragment at the same time.
            // We inform the model with the tables to fragment.
            fragmentManager.beginTransaction()
                    .add(R.id.activity_plate_list_frame_list_id, FragmentPlateCardList.newInstance(mPlatesModel))
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
            intent.putExtra(Constant.EXTRA_TABLE_RESULT, mTableModel);
            setResult(RESULT_OK, intent);
            finish();
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
    public void onPlateSelected(Plate plate) {

        // We started the activity ActivityTableList to show a table plates.
        Intent intent = new Intent(this, ActivityPlateDetail.class);
        Bundle extras = new Bundle();

        // We included in an intent information of the plates from the table.
        extras.putSerializable(Constant.EXTRA_PLATE_DATA, plate);
        intent.putExtras(extras);

        // We included in an intent information of the plates from the table.
        startActivityForResult(intent, Constant.REQUEST_ORDER);
    }

    // Implements this method, for update data model
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verify Request Code.
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_ORDER) {
                Order order = (Order) data.getSerializableExtra(Constant.EXTRA_ORDER_RESULT);
                mTableModel.addOrderToTable(order);
            }
        }
    }
}
