package com.example.assignment2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class RegisterScreen extends AppCompatActivity {

    private EditText email, password, confirmPassword, firstName, lastName, phone, dob, idNumber;
    private TextView createButton;
    private String bloodType;
    private Spinner bloodTypeSpinner;

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
        dob.setOnClickListener(v -> showDatePicker());
        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner);
        setupBloodTypeSpinner();
        // Load blood types from strings.xml into the Spinner

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
        String bloodTypeInput = bloodType;

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

                            User newUser = new User(firstNameText, lastNameText, emailText, phoneText, dobText, idNumberText, bloodTypeInput);

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

    private void showDatePicker() {
        // Get the current date to show it as default
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterScreen.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is zero-based, so we add 1 to the month
                    String dobText = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dob.setText(dobText);  // Set the selected date to the EditText
                },
                year, month, day // Initial date set to the current date
        );

        // Show the date picker
        datePickerDialog.show();
    }

    private void setupBloodTypeSpinner() {
        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner); // Get the Spinner by ID

        // Create an ArrayAdapter using the blood_types string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.blood_types, // Blood types array defined in strings.xml
                android.R.layout.simple_spinner_item // Layout for individual items
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the Spinner
        bloodTypeSpinner.setAdapter(adapter);

        // Set an item selection listener to handle the selected blood type
        bloodTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parentView, android.view.View view, int position, long id) {
                // Get the selected blood type as a String
                bloodType = parentView.getItemAtPosition(position).toString();

                // Optionally, display the selected blood type as a Toast (for debugging)
                Toast.makeText(RegisterScreen.this, "Selected Blood Type: " + bloodType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parentView) {
                // Handle the case where no item is selected (optional)
                bloodType = "";
            }
        });
    }

    private void backToLoginScreen() {
        // Implement the logic to go back to the login screen (e.g., using an Intent)
    }
}
