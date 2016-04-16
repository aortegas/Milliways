package io.keepcoding.milliways.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Order;
import io.keepcoding.milliways.model.Plate;

public class ActivityPlateDetail extends AppCompatActivity {

    // Declare a variable for data of table.
    private Plate mPlateModel;
    private EditText mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We load the view of activity.
        setContentView(R.layout.activity_plate_detail);

        // We retrieve information from the selected table of intent, with the public constant stated above.
        // If we don't have the information, we say charge the first table.
        mPlateModel = (Plate) getIntent().getSerializableExtra(Constant.EXTRA_PLATE_DATA);

        // We indicate the action bar a button to go back.
        // For this to work, we must implement the methods menu.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + getString(R.string.NUEVO_PLATO));
        }

        // Access to view.
        TextView mNameTextView = (TextView) findViewById(R.id.activity_plate_detail_name_textview_id);
        TextView mPriceTextView = (TextView) findViewById(R.id.activity_plate_detail_price_textview_id);
        TextView mDescriptionTextView = (TextView) findViewById(R.id.activity_plate_detail_description_textview_id);
        ImageView mPlateImageView = (ImageView) findViewById(R.id.activity_plate_detail_imageview_id);
        ImageView mAllergen1ImageView = (ImageView) findViewById(R.id.activity_plate_detail_allergen1_imageview_id);
        ImageView mAllergen2ImageView = (ImageView) findViewById(R.id.activity_plate_detail_allergen2_imageview_id);
        ImageView mAllergen3ImageView = (ImageView) findViewById(R.id.activity_plate_detail_allergen3_imageview_id);
        Button mAcceptButtonNewOrder = (Button) findViewById(R.id.activity_plate_detail_accept_button);
        Button mCancelButtonNewOrder = (Button) findViewById(R.id.activity_plate_detail_cancel_button);
        mComments = (EditText) findViewById(R.id.activity_plate_detail_comments_id);

        // Buttons actions.
        if (mAcceptButtonNewOrder != null) {
            mAcceptButtonNewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptNewOrder();
                }
            });
        }

        if (mCancelButtonNewOrder != null) {
            mCancelButtonNewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelNewOrder();
                }
            });
        }

        // Show the data into the view.
        if (mNameTextView != null) {
            mNameTextView.setText(mPlateModel.getName());
        }
        if (mPriceTextView != null) {
            String price = String.valueOf(mPlateModel.getPrice() + " " + getString(R.string.DIVISA));
            mPriceTextView.setText(price);
        }
        if (mDescriptionTextView != null) {
            mDescriptionTextView.setText(mPlateModel.getDescription());
        }
        if (mPlateImageView != null) {
            mPlateImageView.setImageResource(mPlateModel.getImage());
        }

        for (int i = 0; i < mPlateModel.getAllegens().size(); i++) {
            if (i == 0) {
                if (mAllergen1ImageView != null) {
                    mAllergen1ImageView.setImageResource(mPlateModel.getAllegens().get(0).getImage());
                }
            }
            else if (i == 1) {
                if (mAllergen2ImageView != null) {
                    mAllergen2ImageView.setImageResource(mPlateModel.getAllegens().get(1).getImage());
                }
            }
            else if (i == 2) {
                if (mAllergen3ImageView != null) {
                    mAllergen3ImageView.setImageResource(mPlateModel.getAllegens().get(2).getImage());
                }
            }
        }
    }

    // Implements this method, for a selections of a ActionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        // We identify the item back
        if (item.getItemId() == android.R.id.home) {

            cancelNewOrder();
            return true;
        }

        return superValue;
    }

    // Accept new order.
    private void acceptNewOrder() {

        // Create a new order, from plate and data view.
        Order order = new Order(mPlateModel, mComments.getText().toString());

        // Finally the activity and go back, return data modification.
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_ORDER_RESULT, order);
        setResult(RESULT_OK, intent);
        finish();
    }

    // Cancel new order.
    private void cancelNewOrder() {

        setResult(RESULT_CANCELED);
        finish();
    }
}
