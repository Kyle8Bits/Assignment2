package com.example.assignment2.main_screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileScreen extends AppCompatActivity {
    Application app = new Application();
    TextView usernameHeader, username, email, phone, dob, blood, signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        signOut = findViewById(R.id.signOut);


            signOut.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ProfileScreen.this, LoginScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear the activity stack
                            startActivity(intent);  // Start the LoginActivity

                            // Optionally, finish() to ensure the current activity is closed
                            finish();
                        }
                    }
            );

        displayInformation(app.getCurrentUser());
        setupFooter();
    }

    @SuppressLint("SetTextI18n")
    public void displayInformation(User user){
        usernameHeader = findViewById(R.id.userNameProfile);
        username = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        dob = findViewById(R.id.profileDOB);
        blood = findViewById(R.id.bloodTypeProfile);

        usernameHeader.setText("Hi " + user.getFirstName() + ",");
        username.setText(user.getFirstName() + " " + user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        dob.setText(user.getDob());
        blood.setText(user.getBloodType());
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

        bookNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileScreen.this, DonateMapScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
}