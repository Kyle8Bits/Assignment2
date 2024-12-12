package com.example.assignment2.list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.DonateAdapter;
import com.example.assignment2.main_screen.DonateMapScreen;
import com.example.assignment2.main_screen.HomeScreen;
import com.example.assignment2.main_screen.ProfileScreen;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DonationRecord extends AppCompatActivity {
    Application app = new Application();
    FrameLayout goBack;
    Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_record_layout);

        RecyclerView recyclerView = findViewById(R.id.donateRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(DonationRecord.this));

        FirebaseUser currentUser = app.getCurrentUserFirebase();
        app.getUserRegisterData(currentUser, new Application.UserRegisterDonateCallback() {
            @Override
            public void onSuccess(List<DonateRegister> donateRegisters) {
                try {
                    DonateAdapter adapter = new DonateAdapter(DonationRecord.this, utils.getUserRegister(donateRegisters));
                    recyclerView.setAdapter(adapter);
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


        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}