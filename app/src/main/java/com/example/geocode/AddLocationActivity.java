// Activity to add a location given the long and lat
package com.example.geocode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocode.data.DatabaseHelper;

public class AddLocationActivity extends AppCompatActivity {

    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize activity and variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        buttonSave = findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(this);

        // Save on click listener
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitudeString = editTextLatitude.getText().toString().trim();
                String longitudeString = editTextLongitude.getText().toString().trim();
                //Check if empty
                if (TextUtils.isEmpty(latitudeString) || TextUtils.isEmpty(longitudeString)) {
                    showToast("Latitude and Longitude are required");
                } else {
                    double latitude = Double.parseDouble(latitudeString);
                    double longitude = Double.parseDouble(longitudeString);
                    // Check if they are valid
                    if (isValidLatLng(latitude, longitude)) {
                        // Add to database
                        long rowId = databaseHelper.addLocation(latitude, longitude);
                        if (rowId != -1) {
                            showToast("Location added successfully");
                            finish();
                        } else {
                            showToast("Failed to add location. Please try again.");
                        }
                    } else {
                        showToast("Invalid latitude or longitude. Please enter values within the valid range.");
                    }
                }
            }
        });
    }
    // Check if lat and long are valid
    private boolean isValidLatLng(double latitude, double longitude) {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180;
    }
    // Make a toast
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}