package com.example.assignment2.list_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.DonateAdapter;
import com.example.assignment2.adapter.VolunteerAdapter;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class VolunteerRecord extends AppCompatActivity {
    TextView name;
    FrameLayout goBack;
    Utils utils = new Utils();
    Application app = new Application();
    VolunteerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);
        name = findViewById(R.id.listName);
        name.setText("Volunteer record");
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getVolunteerRegister();
    }

    private void getVolunteerRegister() {
        FirebaseUser currentUser = app.getCurrentUserFirebase();
        app.getVolunteerRegisterData(currentUser, new Application.UserRegisterVolunteerCallback() {
            @Override
            public void onSuccess(List<VolunteerRegister> volunteerRegisters) {
                RecyclerView recyclerView = findViewById(R.id.donateRCV);
                recyclerView.setLayoutManager(new LinearLayoutManager(VolunteerRecord.this));
                adapter = new VolunteerAdapter(VolunteerRecord.this, utils.getUserVolunteerRegister(volunteerRegisters), new VolunteerAdapter.onCancelListener() {
                    @Override
                    public void onCancel(VolunteerRegister register) {

                        app.deleteVolunteerRegister(register, new Application.deleteVolunteerRegisterCallback(){
                            @Override
                            public void onSuccess() {
                                Toast.makeText(
                                        VolunteerRecord.this,
                                        "Cancel success",
                                        Toast.LENGTH_SHORT
                                ).show();
                                adapter.removeVolunteerRegister(register);
                            }
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(
                                        VolunteerRecord.this,
                                        "Delete failed, please try again",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        });

                    }
                });
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

}