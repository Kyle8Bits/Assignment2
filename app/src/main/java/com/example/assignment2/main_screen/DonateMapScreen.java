package com.example.assignment2.main_screen;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.utils.FindPlaceTask;
import com.example.assignment2.utils.Utils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.Location;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.assignment2.databinding.ActivityDonateMapScreenBinding;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.Calendar;

public class DonateMapScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityDonateMapScreenBinding binding;
    private FusedLocationProviderClient client;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Application app = new Application();
    Utils utils = new Utils();
    SearchView mapSearch;
    private String address, locationName;
    private LatLng coor ;
    String bloodType;


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
        getPosition();
        if(app.getCurrentUser().getUserType().equals("MANAGER")){
            setUpSearchBar();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if(app.getCurrentUser().getUserType().equals("MANAGER")){
            managerSetOnMarker();
        }
        else {
            donorSetOnMarker();
        }

    }

    public void managerSetOnMarker(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                try {
                    showCreateForm(locationName,address,coor);

                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                return true;
            }
        });
    }

    public void showCreateForm(String nameOfPlace, String address, LatLng coordinate){
        EditText nameLocation, addressLocation, amount, date, start, end;
        Spinner bloodType;
        Button create;

        Dialog createForm = new Dialog(this);
        createForm.setContentView(R.layout.pop_create_drive);
        createForm.setCancelable(true);
        createForm.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        nameLocation = createForm.findViewById(R.id.locationName);
        addressLocation = createForm.findViewById(R.id.address);
        amount = createForm.findViewById(R.id.amountCl);
        date = createForm.findViewById(R.id.dateCl);
        start = createForm.findViewById(R.id.startTCL);
        end = createForm.findViewById(R.id.endTCL);
        bloodType = createForm.findViewById(R.id.bloodTypeCl);
        create = createForm.findViewById(R.id.createDonateDrive);

        createForm.show();

        date.setOnClickListener(v -> utils.showDatePicker(date, DonateMapScreen.this) );
        end.setOnClickListener(v -> utils.showTimePicker(end, DonateMapScreen.this) );
        start.setOnClickListener(v -> utils.showTimePicker(start, DonateMapScreen.this) );

        setupBloodTypeSpinner(bloodType);

        nameLocation.setText(nameOfPlace);
        addressLocation.setText(address);

        create.setOnClickListener(v -> {
            if (amount.getText().toString().isEmpty() || date.getText().toString().isEmpty() ||
                    start.getText().toString().isEmpty() || end.getText().toString().isEmpty() ||
                    bloodType.getSelectedItem() == null ) {
                Toast.makeText(DonateMapScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

            } else if (!utils.isTimeLogical(start.getText().toString(), end.getText().toString())) {
                Toast.makeText(DonateMapScreen.this, "The start time must be earlier then the end time", Toast.LENGTH_SHORT).show();
            } else {
                DonateSite site = new DonateSite(locationName, address, coordinate.latitude, coordinate.longitude,
                        app.getCurrentUser().getPhone(), app.getCurrentUser().getUserId(), date.getText().toString(),
                        "Open Register", null, null, start.getText().toString(), end.getText().toString(),
                        Double.parseDouble(amount.getText().toString()), bloodType.getSelectedItem().toString()
                        );
                createDonateSite(site);
            }
        });
    }

    public void createDonateSite(DonateSite site){
        app.createNewSite(site, new Application.CreateSiteCallback() {
            @Override
            public void onSuccess(String documentId) {
                Toast.makeText(DonateMapScreen.this,"Success creating new site", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(DonateMapScreen.this,"Fail creating new site", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void donorSetOnMarker(){
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

        mapSearch.setVisibility(View.VISIBLE);
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
                                String name =  candidate.getString("name");
                                double lat = location.getDouble("lat");
                                double lng = location.getDouble("lng");

                                LatLng locationLatLng = new LatLng(lat, lng);
                                mMap.clear();
                                mMap.addMarker(new MarkerOptions().position(locationLatLng).title(formattedAddress)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15));

                                address = formattedAddress;
                                coor = locationLatLng;
                                locationName = name;

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

    private void setupBloodTypeSpinner(Spinner bloodTypeSpinner) {
        // Create an ArrayAdapter using the blood_types string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.blood_types, // Blood types array defined in strings.xml
                android.R.layout.simple_spinner_item // Layout for individual items
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        bloodTypeSpinner.setAdapter(adapter);

        // Set an item selection listener to handle the selected blood type
        bloodTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parentView, android.view.View view, int position, long id) {
                // Get the selected blood type as a String
                bloodType = parentView.getItemAtPosition(position).toString();

                // Optionally, display the selected blood type as a Toast (for debugging)
                Toast.makeText(DonateMapScreen.this, "Selected Blood Type: " + bloodType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parentView) {
                // Handle the case where no item is selected (optional)
                bloodType = "";
            }
        });
    }

}