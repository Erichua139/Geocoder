// Activity to get the details of a location from an address.
package com.example.geocode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.geocode.data.DatabaseHelper;
import com.google.android.gms.maps.model.LatLng;

public class LocationDetailsActivity extends AppCompatActivity {

    private EditText editTextAddress;
    private Button buttonSubmit;
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewLatitude = findViewById(R.id.textViewLatitude);
        textViewLongitude = findViewById(R.id.textViewLongitude);
        databaseHelper = new DatabaseHelper(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the address from the EditText
                String address = editTextAddress.getText().toString().trim();

                // Fetch latitude and longitude from the database based on the address
                LatLng location = databaseHelper.getLatLngForAddress(address);
                databaseHelper.close();

                // Display latitude and longitude if location is found, else show error message
                if (location != null) {
                    textViewLatitude.setText("Latitude: " + String.valueOf(location.latitude));
                    textViewLongitude.setText("Longitude: " + String.valueOf(location.longitude));
                } else {
                    textViewLatitude.setText("Latitude: N/A");
                    textViewLongitude.setText("Longitude: N/A");
                }
            }
        });

    }
}