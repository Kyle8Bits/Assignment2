package com.example.assignment2.main_screen;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.assignment2.R;
import com.example.assignment2.utils.FindPlaceTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.Location;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assignment2.databinding.ActivityDonateMapScreenBinding;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

public class DonateMapScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityDonateMapScreenBinding binding;
    private FusedLocationProviderClient client;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    SearchView mapSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateMapScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        mapSearch = findViewById(R.id.mapSearchBar);
        mapSearch.requestFocus();
        setupFooter();
        setUpSearchBar();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Toast.makeText(DonateMapScreen.this,"Your location", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @SuppressLint("MissingPermission")
    public void getPosition(){
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                try {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));


                    Toast.makeText(DonateMapScreen.this,"Your location", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        });
    }

    public void requestPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setUpSearchBar(){
        mapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public boolean onQueryTextSubmit(String query) {
                // When search is submitted, call the FindPlaceTask with the query
                new FindPlaceTask() {
                    @Override
                    protected void onPostExecute(String result) {
                        if (result != null) {
                            try {
                                // Parse the JSON result
                                JSONObject jsonObject = new JSONObject(result);
                                JSONObject candidate = jsonObject.getJSONArray("candidates").getJSONObject(0);
                                String formattedAddress = candidate.getString("formatted_address");
                                JSONObject geometry = candidate.getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                double lat = location.getDouble("lat");
                                double lng = location.getDouble("lng");

                                // Move the map to the location
                                LatLng locationLatLng = new LatLng(lat, lng);
                                mMap.clear();  // Clear previous markers
                                mMap.addMarker(new MarkerOptions().position(locationLatLng).title(formattedAddress));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));

                                // Optionally show a toast with the place name

                            } catch (Exception e) {
                                Log.e("MapActivity", "Error parsing JSON response: " + e.getMessage(), e);
                            }
                        } else {
                            Log.e("MapActivity", "Error fetching place.");
                        }
                    }
                }.execute(query); // Pass the query to the AsyncTask
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false; // No action needed for text changes
            }
        });

    }
    public void setupFooter() {
        ImageButton homeNav, bookNav, profileNav;

        homeNav = findViewById(R.id.homeNav);
        bookNav = findViewById(R.id.bookingNav);
        profileNav = findViewById(R.id.profileNav);



        homeNav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        profileNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonateMapScreen.this, ProfileScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
}