package io.keepcoding.milliways.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.LinkedList;

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Plate;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlatesPager extends Fragment {

    // Attributes.
    // We keep the data model to show.
    private LinkedList<Plate> mPlates;
    // We keep a reference to our activity, that will be our listener.
    private PlatesPagerListener mPlatesPagerListener;

    // Constructors.
    public FragmentPlatesPager() {
    }

    public static FragmentPlatesPager newInstance(LinkedList<Plate> plates) {

        // We create, initialize and return the fragment.
        FragmentPlatesPager fragmentPlatesPager = new FragmentPlatesPager();
        fragmentPlatesPager.mPlates = plates;
        return fragmentPlatesPager;
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
        View root = inflater.inflate(R.layout.fragment_plates_pager, container, false);

        // Conect with view.
        ListView listView = (ListView) root.findViewById(R.id.fragment_plates_pager_listview_id);

        // Create adapter for list. We give the context, style and data tables.
        ArrayAdapter<Plate> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mPlates);

        // Assign the adapter to ListView.
        listView.setAdapter(arrayAdapter);

        // Assign the listener to ListView from AdapterView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                if (mPlatesPagerListener != null) {

                    // We get the selected plate in the model.
                    Plate plateSelected = mPlates.get(position);

                    // We notice the plate selected to the listener.
                    mPlatesPagerListener.onPlateSelected(plateSelected, position);
                }
            }
        });

        return root;
    }

    // If we implements onAttach methods, we can implement onDetach method for disconnect activity.
    @Override
    public void onDetach() {
        super.onDetach();
        mPlatesPagerListener = null;
    }

    // We create a public interface for communicate with our activity.
    // Therefore, our activity is necessary that implements this interface.
    public interface PlatesPagerListener {

        // Method for plates selection.
        void onPlateSelected(Plate plate, int position);
    }
}
