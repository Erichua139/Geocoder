//Location adapter. Helps display the locations and the buttons
package com.example.geocode;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.geocode.data.DatabaseHelper;
import com.example.geocode.data.Location;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {
    private List<Location> locations;
    DatabaseHelper databaseHelper;

    public LocationAdapter(Context context, List<Location> locations, DatabaseHelper databaseHelper) {
        super(context, 0, locations);
        this.locations = locations;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Location location = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_location, parent, false);
        }

        TextView addressTextView = convertView.findViewById(R.id.textAddress);
        Button editButton = convertView.findViewById(R.id.editButton);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        // Set data to the views
        if (location != null) {
            String m = "Lat: " + location.getLatitude() + "\tLon: " + location.getLongitude() + "\nAddress: " + location.getAddress();
            addressTextView.setText(m);

            // Handle edit button click here
            editButton.setOnClickListener(v -> {
                // Handle edit button click for the specific location
                int locationId = location.getId(); // Get the ID of the clicked location
                Intent intent = new Intent(getContext(), EditLocationActivity.class);
                intent.putExtra("id", locationId);
                intent.putExtra("latitude", location.getLatitude());
                intent.putExtra("longitude", location.getLongitude());
                intent.putExtra("address", location.getAddress());
                getContext().startActivity(intent);
            });

            // Handle delete button click here
            deleteButton.setOnClickListener(v -> {
                int locationId = location.getId();
                databaseHelper.deleteLocationById(locationId);
                notifyDataSetChanged();
            });
        }

        return convertView;
    }
}
