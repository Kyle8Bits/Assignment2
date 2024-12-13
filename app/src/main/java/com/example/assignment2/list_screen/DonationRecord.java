package com.example.assignment2.list_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.DonateAdapter;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DonationRecord extends AppCompatActivity {
    Application app = new Application();
    FrameLayout goBack;
    Utils utils = new Utils();
    DonateAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getDonateRegisterData();
    }

    private void getDonateRegisterData(){
        FirebaseUser currentUser = app.getCurrentUserFirebase();
        app.getDonateRegisterData(currentUser, new Application.UserRegisterDonateCallback() {
            @Override
            public void onSuccess(List<DonateRegister> donateRegisters) {
                try {
                    RecyclerView recyclerView = findViewById(R.id.donateRCV);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DonationRecord.this));
                    adapter = new DonateAdapter(DonationRecord.this, utils.getUserDonateRegister(donateRegisters), new DonateAdapter.onCancelListener() {
                        @Override
                        public void onCancel(DonateRegister register) {
                            app.deleteDonateRegister(register, new Application.deleteDonationRegisterCallback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(
                                            DonationRecord.this,
                                            "Cancel success",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    adapter.removeDonateRegister(register);

                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(
                                            DonationRecord.this,
                                            "Delete failed, please try again",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });
                        }
                    });
                    recyclerView.setAdapter(adapter);
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
    }

}