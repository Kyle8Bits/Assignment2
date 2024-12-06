package com.example.assignment2;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterScreen extends AppCompatActivity {

    private EditText email, password, confirmPassword, firstName, lastName, phone, dob, idNumber;
    private TextView createButton;
    private String bloodType;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        idNumber = findViewById(R.id.id_number);
        createButton = findViewById(R.id.create_acc); // Assuming you have a button for creating the account

        createButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        String confirmPasswordText = confirmPassword.getText().toString().trim();
        String firstNameText = firstName.getText().toString().trim();
        String lastNameText = lastName.getText().toString().trim();
        String phoneText = phone.getText().toString().trim();
        String dobText = dob.getText().toString().trim();
        String idNumberText = idNumber.getText().toString().trim();

        if (emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Create user object
                            User newUser = new User(firstNameText, lastNameText, emailText, phoneText, dobText, idNumberText, bloodType);

                            // Save user data in Firestore
                            db.collection("users").document(user.getUid())  // Store under user's UID
                                    .set(newUser)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegisterScreen.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        // Navigate to the next screen (e.g., login)
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(RegisterScreen.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(RegisterScreen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void backToLoginScreen() {
        // Implement the logic to go back to the login screen (e.g., using an Intent)
    }
}
