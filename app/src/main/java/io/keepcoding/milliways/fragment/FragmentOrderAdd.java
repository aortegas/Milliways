package io.keepcoding.milliways.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.milliways.R;

public class FragmentOrderAdd extends Fragment {

    // Attributes.
    // We keep a reference to our activity, that will be our listener.
    private OrderAddListener mOrderAddListener;

    // Constructor.
    public FragmentOrderAdd() {
    }

    // We implement onAttach methods to make sure when we have available access to our activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof OrderAddListener) {
            mOrderAddListener = (OrderAddListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_add, container, false);

        // Conect with view.
        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.fragment_plates_add_button_add_id);

        // Assign the listener to FloatingActionButton.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                if (mOrderAddListener != null) {

                    // Notice my activity for add new plate to table.
                    mOrderAddListener.onAddOrderToTable();
                }
            }
        });

        return root;
    }

    // If we implements onAttach methods, we can implement onDetach method for disconnect activity.
    @Override
    public void onDetach() {
        super.onDetach();
        mOrderAddListener = null;
    }

    // We create a public interface for communicate with our activity.
    // Therefore, our activity is necessary that implements this interface.
    public interface OrderAddListener {

        // Method for add plates selection.
        void onAddOrderToTable();
    }
}
