package com.example.assignment2.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.list_screen.SiteRecord;
import com.example.assignment2.models.User;

public class ManagerScreen extends AppCompatActivity {
    Application app = new Application();
    TextView manager;
    Button create, view;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_screen);
        manager = findViewById(R.id.managerName);
        user = app.getCurrentUser();
        manager.setText("Hi manager " + user.getFirstName() + ",");
        setUpButton();
        setupFooter();
    }

    void setUpButton(){
        create = findViewById(R.id.createSiteStart);
        view = findViewById(R.id.viewDrive);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerScreen.this, DonateMapScreen.class);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerScreen.this, SiteRecord.class);
                startActivity(intent);
            }
        });
    }

    public void setupFooter() {
        TextView bookingText =  findViewById(R.id.bookingText);
        ImageButton homeNav, bookNav, profileNav;
        homeNav = findViewById(R.id.homeNav);
        bookNav = findViewById(R.id.bookingNav);
        profileNav = findViewById(R.id.profileNav);

        bookingText.setText("Site");

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