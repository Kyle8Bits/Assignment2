package com.example.assignment2.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindPlaceTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String apiKey = "YOUR_API_KEY"; // Replace with your API Key
        String input = params[0]; // Input text for the place
        String fields = "formatted_address,name,geometry";
        String urlString = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?"
                + "input=" + input
                + "&inputtype=textquery"
                + "&fields=" + fields
                + "&key=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();

        } catch (Exception e) {
            Log.e("FindPlaceTask", "Error: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.d("FindPlaceTask", "API Response: " + result);
            // Handle JSON parsing or UI update here
        } else {
            Log.e("FindPlaceTask", "Error fetching data.");
        }
    }
}
