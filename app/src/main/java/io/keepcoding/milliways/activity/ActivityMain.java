package io.keepcoding.milliways.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import io.keepcoding.milliways.Constant;
import io.keepcoding.milliways.R;
import io.keepcoding.milliways.model.Allergen;
import io.keepcoding.milliways.model.Plate;
import io.keepcoding.milliways.model.Tables;

public class ActivityMain extends AppCompatActivity {

    // Declare variables for model.
    LinkedList<Plate> mPlatesModel;
    Tables mTablesModel;
    // Declare variables for view.
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We load the the layout of activity.
        setContentView(R.layout.activity_main);

        // Access to view.
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_view_id);

        // Access to model of tables.
        mTablesModel = new Tables();

        // Download data of plates.
        downloadPlates();
    }

    // Private Methods.
    // Method for donwload the plates data.
    private void downloadPlates() {

        // We can't do operations network in the main thread, we will use object asyncTask for:
        // 1. Notice to the user, that is being downloading data. (main thread)
        // 2. Download the data. (other thread)
        // 3. Show the download data to the user. (main thread)

        // Inform to asyncTask:
        // 1. Param: In our case,
        // 2. Progress: In our case, is int for register the operation download.
        // 3. Result: In our case, is LinkedList<Plate>
        AsyncTask<LinkedList<Plate>, Integer, LinkedList<Plate>> platesDownloader = new AsyncTask<LinkedList<Plate>, Integer, LinkedList<Plate>>() {


            // We implements this optional method, for notice to the user, that is being downloading data. (main thread)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgressBar.setVisibility(View.VISIBLE);
            }

            // We implements onInBackground, for download the data. (other thread)
            @Override
            protected LinkedList<Plate> doInBackground(LinkedList<Plate>... params) {

                // We prepare for download.
                URL url;
                InputStream inputStream = null;

                try {

                    // We inform the url for download.
                    url = new URL(Constant.URL_PLATES);
                    // Open the connection.
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    // We Connect.
                    httpURLConnection.connect();
                    // We get channel from connection.
                    inputStream = httpURLConnection.getInputStream();

                    // We declare variables for download control.
                    long currentBytes = 0;
                    int downloadedBytes;
                    int responseLength = httpURLConnection.getContentLength();
                    byte data[] = new byte[1024];
                    // StringBuilder allows add pieces of download data
                    StringBuilder stringBuilder = new StringBuilder();

                    // We read the channel, until the end to the data.
                    while ((downloadedBytes = inputStream.read(data)) != -1) {

                        // We append data downloaded to stringBuilder.
                        stringBuilder.append(new String(data, 0, downloadedBytes));

                        if (responseLength > 0) {

                            currentBytes += downloadedBytes;
                            // Update progress.
                            publishProgress((int) (currentBytes * 100) / responseLength);
                        }
                    }

                    // After download, we convert JSON data to my own objets.
                    JSONObject jsonRoot = new JSONObject(stringBuilder.toString());

                    // We get root node, with all plates.
                    JSONArray jsonArrayPlates = jsonRoot.getJSONArray(getString(R.string.app_name));

                    // We create LinkedList<plate> with all plates.
                    LinkedList<Plate> platesModel = new LinkedList<>();

                    // We process JSON data.
                    for (int i = 0; i < jsonArrayPlates.length(); i++) {

                        JSONObject currentPlate = jsonArrayPlates.getJSONObject(i);
                        // We declare variables for plates data and get de data from JSON nodes.
                        String name = (String) currentPlate.get(Constant.PLATO);
                        String description = (String) currentPlate.get(Constant.DESCRIPCION);
                        String image = ("R.drawable.plato" + currentPlate.get(Constant.IMAGEN));
                        double price = currentPlate.getDouble(Constant.PRECIO);
                        LinkedList<Allergen> allergens = new LinkedList<>();

                        JSONArray jsonArrayAllergens = currentPlate.getJSONArray(Constant.ALERGENOS);
                        for (int j = 0; j < jsonArrayAllergens.length(); j++) {

                            JSONObject currentAllegen = jsonArrayAllergens.getJSONObject(j);
                            String allergenName = (String) currentAllegen.get(Constant.ALERGENO);
                            String allergenImage = ("R.drawable." + currentAllegen.get(Constant.ALER_IMG));
                            Allergen allergen = new Allergen(allergenName, allergenImage);
                            allergens.add(allergen);
                        }

                        // We create Plate Object.
                        Plate plate = new Plate(name, description, image, price, allergens);
                        platesModel.add(plate);
                    }

                    // We retard the response.
                    Thread.sleep(1000);

                    // Return data of plates.
                    return platesModel;

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    // With or without error, close the resources.
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // If not possible download plates, we return null.
                return null;
            }

            // We implement this opcional method, for using publishProgress from other thread to maid thread.
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

                // Update progressbar.
                mProgressBar.setProgress(values[0]);
            }

            // We implements this optional method, for notice to the user, that the downloading data is finish. (main thread)
            @Override
            protected void onPostExecute(LinkedList<Plate> platesModel) {
                super.onPostExecute(platesModel);

                if (platesModel != null) {

                    // Update model.
                    mPlatesModel = platesModel;

                    // We started the activity ActivityTableList to show a table plates.
                    // We create the bundle to pass the data model to next activity.
                    Intent intent = new Intent(ActivityMain.this, ActivityTablesList.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable(Constant.EXTRA_TABLES, mTablesModel);
                    extras.putSerializable(Constant.EXTRA_PLATES, mPlatesModel);
                    intent.putExtras(extras);

                    // Finally, we start the activity.
                    startActivity(intent);
                }
                else {

                    // Error control during download.
                    // Notice to user, with a message.
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityMain.this);
                    // Customize alert error.
                    alertDialog.setTitle(R.string.ERROR);
                    alertDialog.setMessage(R.string.DOWLOAD_KO);
                    // Apply buttons to alert error.
                    alertDialog.setPositiveButton(R.string.REINTENTAR, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Retry download.
                            downloadPlates();
                        }
                    });
                    alertDialog.setNegativeButton(R.string.ACABAR, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // End to application.
                            finish();
                        }
                    });
                    // Show alert.
                    alertDialog.show();
                }
            }
        };

        // Start the download.
        platesDownloader.execute(mPlatesModel);
    }
}
