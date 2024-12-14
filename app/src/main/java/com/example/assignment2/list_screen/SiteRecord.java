package com.example.assignment2.list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.SiteAdapter;
import com.example.assignment2.main_screen.DonateMapScreen;
import com.example.assignment2.main_screen.HomeScreen;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SiteRecord extends AppCompatActivity {
    Application app = new Application();
    SearchView filter;
    TextView listName;
    FrameLayout goBack;
    SiteAdapter adapter;
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);
        goBack = findViewById(R.id.goBack);
        filter = findViewById(R.id.manageFilter);
        listName = findViewById(R.id.listName);
        listName.setText("Your sites");
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getSiteForAdmin();
    }

    private void getSiteForAdmin() {
        FirebaseUser currentUser= app.getCurrentUserFirebase();
        RecyclerView recyclerView = findViewById(R.id.donateRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(SiteRecord.this));
        app.getSiteData(currentUser, new Application.SiteDataCallBack() {
            @Override
            public void onSuccess(List<DonateSite> donateSites) {
                app.getVolunteerRegisterData(currentUser, new Application.UserRegisterVolunteerCallback() {
                    @Override
                    public void onSuccess(List<VolunteerRegister> volunteerRegisters) {
                        adapter = new SiteAdapter(SiteRecord.this, utils.getSiteForManager(donateSites), new SiteAdapter.onActionListener() {
                            @Override
                            public void OnAction(DonateSite site, boolean status) {
                                if(!status){
                                    VolunteerRegister volunteerRegister = new VolunteerRegister(
                                            app.getCurrentUser().getUserId(),
                                            site.getSiteId(),

                                            "WAITING",
                                            site.getDate(),

                                            site.getDonationStartTime(),
                                            "" ,

                                            app.getCurrentUser().getFirstName(),
                                            app.getCurrentUser().getLastName(),

                                            app.getCurrentUser().getPhone(),
                                            app.getCurrentUser().getIdNumber(),

                                            site.getPhone(),
                                            site.getName(),

                                            site.getAddress());

                                    app.createNewVolunteerRegister(volunteerRegister, new Application.CreateVolunteerRegisterCallback(){
                                        @Override
                                        public void onSuccess(String documentId) {
                                            Toast.makeText(SiteRecord.this,"Successfully volunteer", Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(SiteRecord.this,"Fail creating new register", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    app.deleteVolunteerRegFromSite(site, app.getCurrentUser().getUserId(), new Application.deleteVolunteerRegFromSiteCallback(){
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(SiteRecord.this,"Successfully delete volunteer", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(SiteRecord.this,"Fail deleting volunteer", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }, new SiteAdapter.onCloseSiteListener() {
                            @Override
                            public void OnClose(DonateSite site) {
                                app.closeSite(site, new Application.closeSiteCallback(){
                                    @Override
                                    public void onSuccess() {
                                        finish();
                                        Toast.makeText(SiteRecord.this,"Successfully close site", Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(SiteRecord.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }, new SiteAdapter.onClickListener() {
                            @Override
                            public void OnClick(DonateSite site) {
                                Intent intent = new Intent(SiteRecord.this, SiteVolunteerList.class);
                                intent.putExtra("siteId", site.getSiteId());
                                startActivity(intent);
                            }
                        },volunteerRegisters);

                        recyclerView.setAdapter(adapter);
                    }
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }
}