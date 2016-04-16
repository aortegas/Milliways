package io.keepcoding.milliways.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.LinkedList;

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.adapter.AdapterPlateCardList;
import io.keepcoding.milliways.model.Plate;

public class FragmentPlateCardList extends Fragment implements AdapterPlateCardList.PlateCardListener  {

    // Attributes.
    // We keep the data model to show.
    private LinkedList<Plate> mPlates;
    // We keep the Recicler View.
    private RecyclerView mRecyclerView;
    // We keep a reference to our activity, that will be our listener.
    private PlatesPagerListener mPlatesPagerListener;

    // Getter and Setter.
    // Public method for return the adapter of RecyclerView.
    public AdapterPlateCardList getAdapterPlateCardList() {
        return (AdapterPlateCardList) mRecyclerView.getAdapter();
    }

    // Constructors.
    public static FragmentPlateCardList newInstance(LinkedList<Plate> plates) {

        // We create, initialize and return the fragment.
        FragmentPlateCardList fragmentPlateCardList = new FragmentPlateCardList();
        fragmentPlateCardList.mPlates = plates;
        return fragmentPlateCardList;
    }

    // We implement onAttach methods to make sure when we have available access to our activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof PlatesPagerListener) {
            mPlatesPagerListener = (PlatesPagerListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_plate_card_list, container, false);

        // Connect with view.
        mRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_plates_card_list_recyclerview_id);

        // We specific to RecyclerView the show mode (simple list) with animation.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Create adapter for list. We give the context, we as listener and data.
        mRecyclerView.setAdapter(new AdapterPlateCardList(mPlates, getActivity(), this));

        // We return the view.
        return root;
    }

    // If we implements onAttach methods, we can implement onDetach method for disconnect activity.
    @Override
    public void onDetach() {
        super.onDetach();
        mPlatesPagerListener = null;
    }

    // We implements this method we as listener the Adapter.
    @Override
    public void onPlateCardSelected(Plate plate, View view, int position) {

        // In this case, inform our listener to the click in some plate.
        if (mPlatesPagerListener != null) {

            mPlatesPagerListener.onPlateSelected(plate, position);
        }
    }

    // We create a public interface for communicate with our activity.
    // Therefore, our activity is necessary that implements this interface.
    public interface PlatesPagerListener {

        // Method for plates selection.
        void onPlateSelected(Plate plate, int position);
    }
}
