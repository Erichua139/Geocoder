//Code to manage the database
package com.example.geocode.data;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "location_db";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
// Create table with the 4 entries
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE location ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "address TEXT,"
                + "latitude REAL,"
                + "longitude REAL)");

        // Read data from CSV file and populate the database
        List<String> csvLines = CsvFileReader.readCsvFromAssets(context, "locations.csv");
        int lineNo = 0;
        for (String line : csvLines) {
            lineNo++;
            if(lineNo == 1) continue;
            try {
                String[] parts = line.split(",");
                double latitude = Double.parseDouble(parts[0]);
                double longitude = Double.parseDouble(parts[1]);
                String address = geocode(latitude, longitude); // Use geocoding to get address

                // Insert data into the database
                ContentValues values = new ContentValues();
                values.put("address", address);
                values.put("latitude", latitude);
                values.put("longitude", longitude);
                db.insert("location", null, values);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //Code to Geocode the adress from the lat and long
    private String geocode(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressText = "Address not found";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                addressText = address.getAddressLine(0); // Get the first address line
            }
        } catch (IOException e) {
            Log.e("GeocodingHelper", "Error geocoding coordinates: " + e.getMessage());
        }

        return addressText;
    }
    //Code to search for the lat and long given the adress
    public LatLng getLatLngForAddress(String keyword) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"latitude", "longitude", "address"};
        String selection = "address LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%"}; // "%" is used for partial match
        Cursor cursor = db.query("location", projection, selection, selectionArgs, null, null, null);

        LatLng latLng = null;
        if (cursor != null && cursor.moveToFirst()) {
            int latitudeIndex = cursor.getColumnIndex("latitude");
            int longitudeIndex = cursor.getColumnIndex("longitude");

            if (latitudeIndex != -1 && longitudeIndex != -1) {
                double latitude = cursor.getDouble(latitudeIndex);
                double longitude = cursor.getDouble(longitudeIndex);
                latLng = new LatLng(latitude, longitude);
            } else {
                Log.e("DatabaseHelper", "Latitude or longitude column not found in cursor.");
            }

            cursor.close();
        } else {
            Log.e("DatabaseHelper", "No data found for the address containing: " + keyword);
        }

        return latLng;
    }
    // All functions below are basic database operations
    public long addLocation(double latitude, double longitude) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("address", geocode(latitude, longitude));
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        return db.insert("location", null, values);
    }

    public void deleteLocationById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("location", whereClause, whereArgs);
    }

    public void updateLocation(int id, double newLatitude, double newLongitude, String newAddress) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("latitude", newLatitude);
        values.put("longitude", newLongitude);
        values.put("address", newAddress);

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        db.update("location", values, whereClause, whereArgs);
    }

    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Select all locations from the database
        String query = "SELECT * FROM location";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // Get the location data from the cursor
                int id = cursor.getInt(0);
                String address = cursor.getString(1);
                double latitude = cursor.getDouble(2);
                double longitude = cursor.getDouble(3);

                // Create a new Location object and add it to the list
                Location location = new Location(id, address, latitude, longitude);
                locations.add(location);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return locations;
    }



}
