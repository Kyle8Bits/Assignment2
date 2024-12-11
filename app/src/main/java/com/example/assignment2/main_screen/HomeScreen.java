package com.example.assignment2.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.utils.Utils;
import com.example.assignment2.list_screen.DonationRecord;
import com.example.assignment2.list_screen.VolunteerRecord;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HomeScreen extends AppCompatActivity {

    TextView user_name ,userBloodType, donate_num, amountDonate ;
    Application app = new Application();
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        user_name = findViewById(R.id.user_name);
        userBloodType = findViewById(R.id.userBloodType);
        donate_num = findViewById(R.id.donate_num);
        amountDonate = findViewById(R.id.blood_donated);

        updateUIWithData();
        setupFooter();
        setNavigateTextView();
    }


    public void updateUIWithData(){
        FirebaseUser currentUser = app.getCurrentUserFirebase();
        if (currentUser != null) {

            app.getCurrentUserData(currentUser, new Application.UserDataCallback() {
                @Override
                public void onSuccess(User userData) {
                    updateLabel(userData);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("HomeScreen" +  "Error fetching user data:" + e);
                }
            });

            app.getUserRegisterData(currentUser, new Application.UserRegisterDonateCallback() {
                @Override
                public void onSuccess(List<DonateRegister> donateRegisters) {
                    try {
                        updateTotalBloodAmount(donateRegisters, currentUser);
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


        } else {
            System.out.println("No user data sign in");
        }
    }

    public void updateLabel(User user) {
        if (user != null) {
            user_name.setText(user.getFirstName()); // Set full name
            userBloodType.setText(user.getBloodType()); // Set blood type
             // Set donation count
        }
        else {
            System.out.println("Cannot find user");
        }
    }

    public void updateTotalBloodAmount(List<DonateRegister> donateRegisters, FirebaseUser currentUser) {
        double amount = utils.getUserTotalBloodAmount(donateRegisters, currentUser.getUid());
        amountDonate.setText(String.valueOf(amount));
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

    public void setNavigateTextView(){
        TextView recordDonate, recordVolunteer;
        recordDonate = findViewById(R.id.recordDonateButton);
        recordVolunteer = findViewById(R.id.recordVolunteerButton);

        recordDonate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, DonationRecord.class);
                startActivity(intent);
            }
        });

        recordVolunteer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreen.this, VolunteerRecord.class);
                startActivity(intent);
            }
        });

    }

}