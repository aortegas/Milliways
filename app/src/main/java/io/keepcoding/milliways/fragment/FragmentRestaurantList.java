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

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Restaurant;
import io.keepcoding.milliways.model.Table;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRestaurantList extends Fragment {

    // Attributes.
    // We keep the data model to show.
    private Restaurant mRestaurant;
    // We keep a reference to our activity, that will be our listener.
    private TableListListener mTableListListener;

    // Constructors.
    public static FragmentRestaurantList newInstance(Restaurant restaurant) {

        // We create, initialize and return the fragment.
        FragmentRestaurantList fragmentRestaurantList = new FragmentRestaurantList();
        fragmentRestaurantList.mRestaurant = restaurant;
        return fragmentRestaurantList;
    }

    // We implement onAttach methods to make sure when we have available access to our activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof TableListListener) {
            mTableListListener = (TableListListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        // Conect with view.
        ListView listView = (ListView) root.findViewById(R.id.fragment_restaurant_list_listview_id);

        // Create adapter for list. We give the context, style and data tables.
        final ArrayAdapter<Table> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, mRestaurant.getTables());

        // Assign the adapter to ListView.
        listView.setAdapter(arrayAdapter);

        // Assign the listener to ListView from AdapterView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                if (mTableListListener != null) {

                    // We get the selected table in the model.
                    Table tableSelected = mRestaurant.getTables().get(position);

                    // We notice the table selected to the listener.
                    mTableListListener.onTableSelected(tableSelected, position);
                }
            }
        });

        return root;
    }

    // If we implements onAttach methods, we can implement onDetach method for disconnect activity.
    @Override
    public void onDetach() {
        super.onDetach();
        mTableListListener = null;
    }

    // We create a public interface for communicate with our activity.
    // Therefore, our activity is necessary that implements this interface.
    public interface TableListListener {

        void onTableSelected(Table table, int posicion);
    }
}

