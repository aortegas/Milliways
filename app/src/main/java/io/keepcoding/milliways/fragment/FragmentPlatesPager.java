package io.keepcoding.milliways.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Table;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlatesPager extends Fragment {

    // Attributes.
    // We keep the data model to show.
    private Table mTable;
    // We keep a reference to our activity, that will be our listener.
    private PlatesPagerListener mPlatesPagerListener;
    // We keep the adapter from list.
    private ArrayAdapter<Plate> arrayAdapter;

    // Constructor.
    public FragmentPlatesPager(Table table) {
        mTable = table;
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
        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.fragment_plates_pager_button_add_id);




        // Create adapter for list. We give the context, style and data tables.
        arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mTable.getPlates());






        // Assign the adapter to ListView.
        listView.setAdapter(arrayAdapter);

        // Assign the listener to ListView from AdapterView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                if (mPlatesPagerListener != null) {

                    // We get the selected plate in the model.
                    Plate plateSelected = mTable.getPlates().get(position);

                    // We notice the plate selected to the listener.
                    mPlatesPagerListener.onPlateSelected(plateSelected, position);
                }
            }
        });

        // Assign the listener to FloatingActionButton.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                if (mPlatesPagerListener != null) {

                    // Notice my activity for add new plate to table.
                    mPlatesPagerListener.onAddPlateToTable(mTable);





                    // Notice to the adapter that data change.
                    arrayAdapter.notifyDataSetChanged();

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
        void onPlateSelected(Plate plate, int posicion);

        // Method for add plates selection.
        void onAddPlateToTable(Table table);
    }
}
