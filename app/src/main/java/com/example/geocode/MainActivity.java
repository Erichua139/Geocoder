// Main page with navigation menu
package com.example.geocode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddLocation = findViewById(R.id.buttonAddLocation);
        Button buttonEditLocation = findViewById(R.id.buttonEditLocation);
        Button btnViewLocations = findViewById(R.id.btnViewLocations);
        Button buttonViewAllLocations = findViewById(R.id.buttonViewAllLocations);

        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLocationActivity.class));
            }
        });

        buttonEditLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditLocationActivity.class));
            }
        });

        btnViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationListActivity.class));
            }
        });


        buttonViewAllLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationDetailsActivity.class));
            }
        });

    }

}