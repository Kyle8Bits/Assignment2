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
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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

        if (user != null) {
            getCurrentUserData(user);
        } else {
            Log.d("Firebase", "No user is signed in");
        }

        setupFooter();
    }

    public void getCurrentUserData(FirebaseUser user) {
        if (user != null) {
            // Example: Get a specific document from a collection
            db.collection("users") // Replace with your collection name
                    .document(user.getUid()) // Document ID is the user's UID
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Document data is available
                            Map<String, Object> data = documentSnapshot.getData();
                            // Use the data
                            currentUser = new User(data.get("firstName").toString(), data.get("lastName").toString(),data.get("email").toString(),data.get("phone").toString(),data.get("dob").toString(),data.get("idNumber").toString(),data.get("bloodType").toString());
                            updateUI(currentUser);
                        } else {
                            Log.d("Firestore", "No such document");
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle error
                        Log.w("Firestore", "Error getting document", e);
                    });
        } else {
            // No user is signed in
            Log.d("Firebase", "No user is signed in");
        }
    }

    public void updateUI(User user) {
        if (user != null) {
            user_name.setText(user.getFirstName() + " " + auth.getCurrentUser().getUid()); // Set full name
            userBloodType.setText(user.getBloodType()); // Set blood type
             // Set donation count
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