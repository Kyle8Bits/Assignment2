package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    FirebaseAuth auth;
    TextView user_name ,userBloodType, donate_num, amountDonate ;
    FirebaseUser user;
    Application app = new Application();

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        user_name = findViewById(R.id.user_name);
        userBloodType = findViewById(R.id.userBloodType);
        donate_num = findViewById(R.id.donate_num);
        amountDonate = findViewById(R.id.blood_donated);

        getData();

        setupFooter();
    }


    private void getData(){
        if (user != null) {
            app.getCurrentUserData(user, new Application.UserDataCallback() {
                @Override
                public void onSuccess(User userData) {
                    // Data is ready, update UI
                    currentUser = userData;
                    updateUI(currentUser);
                }

                @Override
                public void onFailure(Exception e) {
                    // Handle failure
                    Log.d("Firebase", "Error: " + e.getMessage());
                }
            });
        } else {
            Log.d("Firebase", "No user is signed in");
        }
    }

    public void updateUI(User user) {
        if (user != null) {
            user_name.setText(user.getFirstName()); // Set full name
            userBloodType.setText(user.getBloodType()); // Set blood type
             // Set donation count
        }
        else {
            System.out.println("Cannot find user");
        }
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
                        Intent intent = new Intent(HomeScreen.this, DonateMapScreen.class);
                        startActivity(intent);
                    }
                });

        profileNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, ProfileScreen.class);
                startActivity(intent);
            }
        });
    }

}