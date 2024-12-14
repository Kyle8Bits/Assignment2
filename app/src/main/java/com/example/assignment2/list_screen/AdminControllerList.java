package com.example.assignment2.list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.AdminViewAdapter;
import com.example.assignment2.adapter.SiteVolunteerAdapter;
import com.example.assignment2.main_screen.LoginScreen;
import com.example.assignment2.main_screen.ProfileScreen;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.User;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.utils.CreatePDF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AdminControllerList extends AppCompatActivity {
    Application app = new Application();
    CreatePDF createPDF = new CreatePDF();
    List<DonateRegister> donorRegistration;
    List<DonateSite> donationDrives = new ArrayList<>();
    List<VolunteerRegister> volunteerRegistration = new ArrayList<>();
    AdminViewAdapter adminViewAdapter;
    TextView signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_controller_list);
        signOut = findViewById(R.id.signOut);

        signOut.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(AdminControllerList.this, LoginScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear the activity stack
                        startActivity(intent);  // Start the LoginActivity
                        // Optionally, finish() to ensure the current activity is closed
                        finish();
                    }
                }
        );

        getData();
    }

    public void getData() {
        try {
            FirebaseUser currentUser = app.getCurrentUserFirebase();
            if (currentUser != null) {


                app.getDonateRegisterData(currentUser, new Application.UserRegisterDonateCallback() {
                    @Override
                    public void onSuccess(List<DonateRegister> donateRegisters) {
                        try {
                            donorRegistration = donateRegisters;
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("HomeScreen" +  "Error fetching user data:" + e);
                    }
                });

                app.getSiteData(currentUser, new Application.SiteDataCallBack() {
                    @Override
                    public void onSuccess(List<DonateSite> donateSites) {
                        donationDrives = donateSites;
                        RecyclerView recyclerView = findViewById(R.id.adminListRCV);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AdminControllerList.this));
                        adminViewAdapter = new AdminViewAdapter(AdminControllerList.this, getComplete(donateSites), new AdminViewAdapter.OnLongClick() {
                            @Override
                            public void onLongClick(DonateSite donateSite) {
                                fromDataToPDF(donateSite, new PDFDataCallback() {
                                    @Override
                                    public void onDataReady(String[] data) {
                                        createPDF.createPdf(AdminControllerList.this, data, donateSite);
                                        Toast.makeText(AdminControllerList.this, "Saved", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        recyclerView.setAdapter(adminViewAdapter);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("HomeScreen", "Error fetching user data:", e);
                    }
                });

                app.getVolunteerRegisterData(currentUser, new Application.UserRegisterVolunteerCallback() {
                    @Override
                    public void onSuccess(List<VolunteerRegister> volunteerRegisters) {
                        volunteerRegistration = volunteerRegisters;
                    }
                    @Override
                    public void onFailure(Exception e) {

                    }
                });

            } else {
                Log.d("HomeScreen", "No user data sign in");
            }
        } catch (Exception e) {
            System.out.println("HomeScreen" + "Exception in getData:" +e);
        }
    }

    public interface PDFDataCallback {
        void onDataReady(String[] data);
    }
    public void fromDataToPDF(DonateSite donateSite, PDFDataCallback callback){
        String[] data = new String[12];
        data[0] = donateSite.getSiteId();
        data[1] = donateSite.getName();
        data[2] = donateSite.getAddress();
        data[3] = donateSite.getDate();
        data[4] = donateSite.getDonationStartTime();
        data[5] = donateSite.getDonationEndTime(); // end time
        data[6] = donateSite.getBloodCollectType();

        data[7] = getTotalBlood(donateSite);
        data[8] = getTotalDonor(donateSite);
        data[9] = String.valueOf(donateSite.getDonationRegisterIds().size());

        getManager(donateSite, new ManagerCallback() {
            @Override
            public void onSuccess(String managerName) {
                data[10] = managerName;
                callback.onDataReady(data);
            }

            @Override
            public void onFailure(Exception e) {
                data[10] = "Unknown";
                callback.onDataReady(data);
            }
        });

        data[11] = donateSite.getPhone();
    }

    public String getTotalBlood(DonateSite donateSite){
        String result;
        List<String> donorIds = donateSite.getDonationRegisterIds();

        double total = 0;

        for(String donorId : donorIds){
            for(DonateRegister donor : donorRegistration){
                if(donorId.equals(donor.getId())){
                    total += donor.getDonationAmount();
                }
            }
        }
        result = String.valueOf(total);
        return result;
    }

    public String getTotalDonor(DonateSite donateSite){
        String result;
        List<String> donorIds = donateSite.getDonationRegisterIds();
        int total = 0;

        for(String donorId : donorIds){
            for(DonateRegister donor : donorRegistration){
                if(donorId.equals(donor.getId()) && donor.getDonationAmount() != 0){
                    total++;
                }
            }
        }
        result = String.valueOf(total);
        return result;
    }

    public List<DonateSite> getComplete(List<DonateSite> donationDrives){
        List<DonateSite> result = new ArrayList<>();
        for (DonateSite d : donationDrives){
            if(d.getStatus().equals("CLOSE")){
                result.add(d);
            }
        }
        return result;
    }

    public void getManager(DonateSite donateSite, ManagerCallback callback) {
        app.getUserByID(donateSite.getManagerUID(), new Application.UserByIDCallback() {
            @Override
            public void onSuccess(User userData) {
                String managerName = userData.getFirstName() + " " + userData.getLastName();
                callback.onSuccess(managerName);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
    public interface ManagerCallback {
        void onSuccess(String managerName);
        void onFailure(Exception e);
    }

}