package io.keepcoding.milliways.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.fragment.FragmentTablesList;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Table;
import io.keepcoding.milliways.model.Tables;

public class ActivityTablesList extends AppCompatActivity implements FragmentTablesList.TablesListListener {

    // Declare variables for model.
    private Tables mTablesModel;
    private LinkedList<Plate> mPlatesModel;
    private int mTableSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a data model.
        mTablesModel = (Tables) getIntent().getSerializableExtra(Constant.EXTRA_TABLES);
        ArrayList arrayListTemp = (ArrayList) getIntent().getSerializableExtra(Constant.EXTRA_PLATES);
        mPlatesModel = new LinkedList<>(arrayListTemp);

        // We load the the layout of activity.
        setContentView(R.layout.activity_tables_list);

        // We go to load dinamically the fragment, with FragmentManager.
        FragmentManager fragmentManager = getFragmentManager();

        // If we are not associated fragment of times before (this method is executed with the rotation of the device),
        // We indicate the Fragment Manager to load it.
        if (fragmentManager.findFragmentById(R.id.activity_tables_list_frame_id) == null) {

            // Transactions allow loading and removal of various fragment at the same time.
            // We inform the model with the tables to fragment.
            fragmentManager.beginTransaction()
                    .add(R.id.activity_tables_list_frame_id, FragmentTablesList.newInstance(mTablesModel))
                    .commit();
        }
    }

    // Implements the interface, to allow comunicate with our fragment
    @Override
    public void onTableSelected(Table table, int position) {

        // We save de table selected
        mTableSelected = position;

        // We started the activity ActivityTableList to show a table plates.
        Intent intent = new Intent(this, ActivityTableList.class);
        Bundle extras = new Bundle();

        // We included in an intent information of the plates from the table.
        extras.putSerializable(Constant.EXTRA_TABLE_DATA, mTablesModel.getTableAtIndex(position));
        extras.putSerializable(Constant.EXTRA_PLATES, mPlatesModel);
        intent.putExtras(extras);

        // Finally, we start the activity.
        startActivityForResult(intent, Constant.REQUEST_TABLE);
    }

    // Implements this method, for update data model
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verify Request Code.
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_TABLE) {
                Table table = (Table) data.getSerializableExtra(Constant.EXTRA_TABLE_RESULT);
                mTablesModel.setTableAtIndex(table, mTableSelected);
            }
        }
    }
}
