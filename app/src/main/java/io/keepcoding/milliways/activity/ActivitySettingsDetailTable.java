package io.keepcoding.milliways.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Table;

public class ActivitySettingsDetailTable extends AppCompatActivity {

    // Declare variables for model.
    private Table mTableModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We load the view of activity.
        setContentView(R.layout.activity_settings_detail_table);

        // We retrieve information from the selected table of intent, with the public constant stated above.
        // If we don't have the information, we say charge the first table.
        mTableModel = (Table) getIntent().getSerializableExtra(Constant.EXTRA_TABLE_DATA);

        // We indicate the action bar a button to go back.
        // For this to work, we must implement the methods menu.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + mTableModel.getName() + " - " + getString(R.string.CUENTA));
        }

        // Access to view.
        TextView mNumberPlatesTextView = (TextView) findViewById(R.id.activity_settings_detail_number_id);
        TextView mTotalAmountTextView = (TextView) findViewById(R.id.activity_settings_detail_total_id);

        // Calculate total amount of table.
        float mTotalAmount = 0;
        for (int i = 0; i < mTableModel.getOrders().size(); i++) {
            mTotalAmount = mTotalAmount + ((float) mTableModel.getOrders().get(i).getPrice());
        }

        // Show the data into the view.
        if (mNumberPlatesTextView != null && mTableModel.getOrders() != null) {
            mNumberPlatesTextView.setText(String.valueOf(mTableModel.getOrders().size()));
        }
        if (mTotalAmountTextView != null) {
            mTotalAmountTextView.setText(String.valueOf(mTotalAmount));
        }
    }

    // Implements this method, for a selections of a ActionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        // We identify the item back
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return superValue;
    }
}
