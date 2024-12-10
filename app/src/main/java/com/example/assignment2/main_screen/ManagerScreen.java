package com.example.assignment2.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.R;

public class ManagerScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_screen);

        setupFooter();
    }

    public void setupFooter() {
        ImageButton homeNav, bookNav, profileNav;
        homeNav = findViewById(R.id.homeNav);
        bookNav = findViewById(R.id.bookingNav);
        profileNav = findViewById(R.id.profileNav);

        homeNav.setActivated(true);

        bookNav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ManagerScreen.this, DonateMapScreen.class);
                        startActivity(intent);
                    }
                });

        profileNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerScreen.this, ProfileScreen.class);
                startActivity(intent);
            }
        });
    }
}