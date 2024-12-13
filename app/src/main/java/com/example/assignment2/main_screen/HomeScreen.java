package com.example.assignment2.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.VolunteerRegister;
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

        getData();
        setupFooter();
        setNavigateTextView();
    }


    public void getData() {
        try {
            FirebaseUser currentUser = app.getCurrentUserFirebase();
            if (currentUser != null) {

                app.getCurrentUserData(currentUser, new Application.UserDataCallback() {
                    @Override
                    public void onSuccess(User userData) {
                        app.setCurrentUser(userData);
                        updateLabel(app.getCurrentUser());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("HomeScreen" +  "Error fetching user data:" + e);
                    }
                });

                app.getDonateRegisterData(currentUser, new Application.UserRegisterDonateCallback() {
                    @Override
                    public void onSuccess(List<DonateRegister> donateRegisters) {
                        try {
                            app.setUserDonateRegister(donateRegisters);
                            updateTotalBloodAmount(app.getUserDonateRegister(), currentUser);
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
                        app.setAllDonateSite(donateSites);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("HomeScreen", "Error fetching user data:", e);
                    }
                });


                app.getVolunteerRegisterData(currentUser, new Application.UserRegisterVolunteerCallback() {
                    @Override
                    public void onSuccess(List<VolunteerRegister> volunteerRegisters) {
                        app.setUserVolunteerRegister(volunteerRegisters);
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
        Button book_db, register_vl;
        recordDonate = findViewById(R.id.recordDonateButton);
        recordVolunteer = findViewById(R.id.recordVolunteerButton);
        book_db = findViewById(R.id.book_bd);
        register_vl = findViewById(R.id.register_vl);

        book_db.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this, DonateMapScreen.class);
                        startActivity(intent);
                    }
                });

        register_vl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeScreen.this, DonateMapScreen.class);
                        startActivity(intent);
                    }
                });

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