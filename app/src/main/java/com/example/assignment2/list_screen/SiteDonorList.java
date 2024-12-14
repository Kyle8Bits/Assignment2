package com.example.assignment2.list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.SiteDonorAdapter;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SiteDonorList extends AppCompatActivity {
    TextView name;
    FrameLayout goBack;
    Utils utils = new Utils();
    Application app = new Application();
    SearchView searchBar;
    SiteDonorAdapter siteDonorAdapter;
    TextView goToVolunteers;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);

        name = findViewById(R.id.listName);
        name.setText("Donor Registration");
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchBar = findViewById(R.id.manageFilter);
        searchBar.setVisibility(View.VISIBLE);

        goToVolunteers = findViewById(R.id.goToVolunteerList);
        goToVolunteers.setVisibility(View.VISIBLE);

        String siteID = getIntent().getStringExtra("siteId");

        goToVolunteers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteDonorList.this, SiteVolunteerList.class);
                intent.putExtra("siteId", siteID);
                startActivity(intent);
            }
        });

        getSiteDonorData(siteID);

    }

    private void getSiteDonorData(String id){

        app.getDonorRegisterInSite(id, new Application.DonorFromSite(){
            @Override
            public void onSuccess(List<DonateRegister> donateRegisters) {
                RecyclerView recyclerView = findViewById(R.id.donateRCV);
                recyclerView.setLayoutManager(new LinearLayoutManager(SiteDonorList.this));
                siteDonorAdapter = new SiteDonorAdapter(SiteDonorList.this,utils.getUndoneDonor(donateRegisters), new SiteDonorAdapter.OnCheckOutListener() {
                    @Override
                    public void onCheckOutClick(DonateRegister register, double amount) {
                        String registerID = register.getId();
                        app.updateStatus(registerID, amount, new Application.UpdateStatusCallback(){
                            @Override
                            public void onSuccess() {
                                Toast.makeText(SiteDonorList.this, "Checked out successfully"+ register.getFirstName(), Toast.LENGTH_SHORT).show();
                                siteDonorAdapter.removeDonor(register);
                            }
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(SiteDonorList.this, "fail", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                recyclerView.setAdapter(siteDonorAdapter);
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(SiteDonorList.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
