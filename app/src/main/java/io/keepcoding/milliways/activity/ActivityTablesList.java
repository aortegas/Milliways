package io.keepcoding.milliways.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.fragment.FragmentTablesList;
import io.keepcoding.milliways.model.Table;
import io.keepcoding.milliways.model.Tables;

public class ActivityTablesList extends AppCompatActivity implements FragmentTablesList.TablesListListener {

    // Declare variables for model.
    private Tables mTables;
    private int mTableSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Access to model of tables.
        mTables = new Tables();

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
                    .add(R.id.activity_tables_list_frame_id, FragmentTablesList.newInstance(mTables))
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

        // We included in an intent information of the plates from the table.
        intent.putExtra(Constant.EXTRA_TABLE_DATA, mTables.getTableAtIndex(position));

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
                mTables.setTableAtIndex(table, mTableSelected);
            }
        }
    }
}
