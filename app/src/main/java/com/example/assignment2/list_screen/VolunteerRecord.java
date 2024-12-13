package com.example.assignment2.list_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.DonateAdapter;
import com.example.assignment2.adapter.VolunteerAdapter;
import com.example.assignment2.utils.Utils;

public class VolunteerRecord extends AppCompatActivity {
    TextView name;
    FrameLayout goBack;
    Utils utils = new Utils();
    Application app = new Application();
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

        RecyclerView recyclerView = findViewById(R.id.donateRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(VolunteerRecord.this));

        VolunteerAdapter adapter = new VolunteerAdapter(VolunteerRecord.this, utils.getUserVolunteerRegister(app.getUserVolunteerRegister()));
        recyclerView.setAdapter(adapter);

    }

}