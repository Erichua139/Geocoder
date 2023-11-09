// Code for the database task. Runs Aysnchronous
package com.example.geocode.data;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

public class DatabaseTask extends AsyncTask<Void, Void, LatLng> {

    private Context context;
    private DatabaseHelper databaseHelper;
    private String address;
    private DatabaseListener listener;

    public interface DatabaseListener {
        void onResult(LatLng latLng);
    }

    public DatabaseTask(Context context, String address, DatabaseListener listener) {
        this.context = context;
        this.address = address;
        this.listener = listener;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    protected LatLng doInBackground(Void... voids) {
        return databaseHelper.getLatLngForAddress(address);
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        super.onPostExecute(latLng);
        if (listener != null) {
            listener.onResult(latLng);
        }
    }
}
