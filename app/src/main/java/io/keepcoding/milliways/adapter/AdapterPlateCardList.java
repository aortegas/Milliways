package io.keepcoding.milliways.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Plate;

// IMPORTANT:
// The class AdapterPlateCardList work with all data to the list, in this case plates.
// The class PlateViewHolder work with the view for each element to the list.
public class AdapterPlateCardList extends RecyclerView.Adapter<AdapterPlateCardList.PlateViewHolder> {

    // Attributes for model.
    private LinkedList<Plate> mPlatesModel;
    private Context mContext;
    private PlateCardListener mParentClickListener;

    // Constructor, from data of all plates, context and parent listener.
    public AdapterPlateCardList(LinkedList<Plate> plates, Context context, PlateCardListener parentClickListener) {
        super();
        mPlatesModel = plates;
        mContext = context;
        mParentClickListener = parentClickListener;
    }

    // We implement mandatory method, to create de interface for each element with ViewHolder, also we return this ViewHolder.
    @Override
    public PlateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_plate_card, parent, false);
        return new PlateViewHolder(view, mParentClickListener);
    }

    // We implement mandatory method, to be call when data to show. This method call method bindForecast in ViewHolder.
    @Override
    public void onBindViewHolder(PlateViewHolder holder, int position) {

        holder.bindForecast(mPlatesModel.get(position), mContext);
    }

    // We implement mandatory method, to return the number of element to list.
    @Override
    public int getItemCount() {
        return mPlatesModel.size();
    }


    // Internal class to create the ViewHolder.
    // The ViewHolder get references to view, inform to controller to click into the view and inform the view with data model.
    public class PlateViewHolder extends RecyclerView.ViewHolder {

        // Atrribute for model.
        private Plate mPlateModel;

        // Attributes View.
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private TextView mDescriptionTextView;
        private ImageView mPlateImageView;
        private ImageView mAllergen1ImageView;
        private ImageView mAllergen2ImageView;
        private ImageView mAllergen3ImageView;

        // Constructor
        public PlateViewHolder(View itemView, final PlateCardListener parentClickListener) {
            super(itemView);

            // Associate view with PlaceViewHolder.
            mNameTextView = (TextView) itemView.findViewById(R.id.fragment_plate_card_name_textview_id);
            mPriceTextView = (TextView) itemView.findViewById(R.id.fragment_plate_card_price_textview_id);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.fragment_plate_card_description_textview_id);
            mPlateImageView = (ImageView) itemView.findViewById(R.id.fragment_plate_card_image_imageview_id);
            mAllergen1ImageView = (ImageView) itemView.findViewById(R.id.fragment_plate_card_allergen1_imageview_id);
            mAllergen2ImageView = (ImageView) itemView.findViewById(R.id.fragment_plate_card_allergen2_imageview_id);
            mAllergen3ImageView = (ImageView) itemView.findViewById(R.id.fragment_plate_card_allergen3_imageview_id);

            // Inform the listener to view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentClickListener.onPlateCardSelected(mPlateModel, v);
                }
            });
        }

        // Method to inform view with data model.
        public void bindForecast(Plate plate, Context context) {

            // Set de model.
            mPlateModel = plate;

            // Show the data into the view.
            mNameTextView.setText(mPlateModel.getName());
            mPriceTextView.setText(String.valueOf(mPlateModel.getPrice()) + " " + context.getString(R.string.DIVISA));
            mDescriptionTextView.setText(mPlateModel.getDescription());
            mPlateImageView.setImageResource(mPlateModel.getImage());

            for (int i = 0; i < mPlateModel.getAllegens().size(); i++) {
                if (i == 0) {
                    mAllergen1ImageView.setImageResource(mPlateModel.getAllegens().get(0).getImage());
                }
                else if (i == 1) {
                    mAllergen2ImageView.setImageResource(mPlateModel.getAllegens().get(1).getImage());
                }
                else if (i == 2) {
                    mAllergen3ImageView.setImageResource(mPlateModel.getAllegens().get(2).getImage());
                }
            }
        }
    }

    // Declare interface to selection plate.
    public interface PlateCardListener {
        void onPlateCardSelected(Plate plate, View view);
    }
}