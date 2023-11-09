// Activity to edit existing locations
package com.example.geocode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.geocode.data.DatabaseHelper;

public class EditLocationActivity extends AppCompatActivity {

    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private EditText editTextAddress;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSave = findViewById(R.id.buttonSave);
        databaseHelper = new DatabaseHelper(this);

        // Get data from intent and populate the EditText fields from view of all activities
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);
        String address = getIntent().getStringExtra("address");
        int locationId = getIntent().getIntExtra("id", -1); // Default value -1 if not provided

        editTextLatitude.setText(String.valueOf(latitude));
        editTextLongitude.setText(String.valueOf(longitude));
        editTextAddress.setText(address);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated latitude, longitude, and address from EditText fields
                double updatedLatitude = Double.parseDouble(editTextLatitude.getText().toString());
                double updatedLongitude = Double.parseDouble(editTextLongitude.getText().toString());
                String updatedAddress = editTextAddress.getText().toString();

                // Pass the updated data and ID back to the calling activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", locationId);
                resultIntent.putExtra("latitude", updatedLatitude);
                resultIntent.putExtra("longitude", updatedLongitude);
                resultIntent.putExtra("address", updatedAddress);
                setResult(RESULT_OK, resultIntent);

                // Update the location in the database based on the ID
                databaseHelper.updateLocation(locationId, updatedLatitude, updatedLongitude, updatedAddress);

                finish();
            }
        });



    }
}