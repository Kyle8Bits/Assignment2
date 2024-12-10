package com.example.assignment2.list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment2.R;
import com.example.assignment2.main_screen.DonateMapScreen;
import com.example.assignment2.main_screen.HomeScreen;
import com.example.assignment2.main_screen.ProfileScreen;

public class DonationRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_record);

        setupFooter();
    }

    public void setupFooter() {
        ImageButton homeNav, bookNav, profileNav;
        homeNav = findViewById(R.id.homeNav);
        bookNav = findViewById(R.id.bookingNav);
        profileNav = findViewById(R.id.profileNav);

        homeNav.setActivated(true);

        homeNav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

        bookNav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DonationRecord.this, DonateMapScreen.class);
                        startActivity(intent);
                        finish();
                    }
                });

        profileNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonationRecord.this, ProfileScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}