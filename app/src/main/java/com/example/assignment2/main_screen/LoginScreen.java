package com.example.assignment2.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Application app = new Application();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        app.getCurrentUserData(currentUser, new Application.UserDataCallback() {
            @Override
            public void onSuccess(User userData) {
               if(currentUser != null){
                   if(userData.getUserType().equals("DONOR")){
                       Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                       startActivity(intent);
                       finish();
                   }
                   if(userData.getUserType().equals("MANAGER")){
                       Intent intent = new Intent(LoginScreen.this, ManagerScreen.class);
                       startActivity(intent);
                       finish();
                   }
               }
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("HomeScreen" +  "Error fetching user data:" + e);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        TextView signUp = findViewById(R.id.signUp);
        Button loginButton = findViewById(R.id.loginButton);
        EditText emailEditText = findViewById(R.id.email_account);
        EditText passwordEditText = findViewById(R.id.password);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterScreen activity when the TextView is clicked
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
                // Optionally, finish the current activity if you don't want the user to return to LoginScreen
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = String.valueOf(emailEditText.getText());
                String password = String.valueOf(passwordEditText.getText());

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginScreen.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    app.getCurrentUserData(currentUser, new Application.UserDataCallback() {
                                        @Override
                                        public void onSuccess(User userData) {
                                            if(currentUser != null){
                                                if(userData.getUserType().equals("DONOR")){
                                                    Intent intent = new Intent(LoginScreen.this, ManagerScreen.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                if(userData.getUserType().equals("MANAGER")){
                                                    Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Exception e) {
                                            System.out.println("HomeScreen" +  "Error fetching user data:" + e);
                                        }
                                    });
                                } else {
                                    Toast.makeText(LoginScreen.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }


}