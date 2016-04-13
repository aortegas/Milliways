package io.keepcoding.milliways.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Table;
import io.keepcoding.milliways.model.Tables;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTablesList extends Fragment {

    // Attributes.
    // We keep the data model to show.
    private Tables mTables;
    // We keep a reference to our activity, that will be our listener.
    private TablesListListener mTablesListListener;

    // Constructor.
    public FragmentTablesList(Tables tables) {
        mTables = tables;
    }

    // We implement onAttach methods to make sure when we have available access to our activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof TablesListListener) {
            mTablesListListener = (TablesListListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof  TablesListListener) {
            mTablesListListener = (TablesListListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tables_list, container, false);

        // Conect with view.
        ListView listView = (ListView) root.findViewById(R.id.tables_list_view_fragment);

        // Create adapter for list. We give the context, style and data tables.
        final ArrayAdapter<Table> arrayAdapter = new ArrayAdapter<Table>(getActivity(),android.R.layout.simple_list_item_1,mTables.getTables());

        // Assign the adapter to ListView.
        listView.setAdapter(arrayAdapter);

        // Assign the listener to ListView from AdapterView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Notice my activity about the selection. We ensure that our activity implements our interface: TableListListener
                    if (mTablesListListener != null) {

                    // We get the selected table in the model.
                    Table tableSelected = mTables.getTables().get(position);

                    // We notice the table selected to the listener.
                    mTablesListListener.onTableSelected(tableSelected, position);
                }
            }
        });

        return root;
    }

    // If we implements onAttach methods, we can implement onDetach method for disconnect activity.
    @Override
    public void onDetach() {
        super.onDetach();
        mTablesListListener = null;
    }

    // We create a public interface for communicate with our activity.
    // Therefore, our activity is necessary that implements this interface.
    public interface TablesListListener {

        void onTableSelected(Table table, int posicion);
    }
}

