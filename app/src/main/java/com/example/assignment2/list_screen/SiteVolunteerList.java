package com.example.assignment2.list_screen;

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
import com.example.assignment2.adapter.SiteVolunteerAdapter;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SiteVolunteerList extends AppCompatActivity {
    TextView name;
    FrameLayout goBack;
    Utils utils = new Utils();
    Application app = new Application();
    SearchView searchBar;
    SiteVolunteerAdapter siteVolunteerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);
        name = findViewById(R.id.listName);
        name.setText("Volunteer Registration");
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchBar = findViewById(R.id.manageFilter);
        searchBar.setVisibility(View.VISIBLE);


        String siteID = getIntent().getStringExtra("siteId");

        volunteerParticipant(siteID);
    }

    private void volunteerParticipant(String siteId) {

        app.getVolunteerRegisterInSite(siteId, new Application.VolunteerFromSite() {
            @Override
            public void onSuccess(List<VolunteerRegister> volunteerRegisters) {
                RecyclerView recyclerView = findViewById(R.id.donateRCV);
                recyclerView.setLayoutManager(new LinearLayoutManager(SiteVolunteerList.this));
                siteVolunteerAdapter = new SiteVolunteerAdapter(SiteVolunteerList.this, volunteerRegisters);
                recyclerView.setAdapter(siteVolunteerAdapter);
                searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        siteVolunteerAdapter.filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        siteVolunteerAdapter.filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
