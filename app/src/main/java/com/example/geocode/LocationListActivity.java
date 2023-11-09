//Activity to list all locations and allow for a search
package com.example.geocode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.geocode.data.DatabaseHelper;
import com.example.geocode.data.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    private List<Location> locations;
    private LocationAdapter locationAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        databaseHelper = new DatabaseHelper(this);

        locations = databaseHelper.getAllLocations();

        locationAdapter = new LocationAdapter(this, locations, databaseHelper);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(locationAdapter);
    }

}