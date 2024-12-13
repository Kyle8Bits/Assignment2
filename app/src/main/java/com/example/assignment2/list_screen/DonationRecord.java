package com.example.assignment2.list_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.adapter.DonateAdapter;
import com.example.assignment2.utils.Utils;

public class DonationRecord extends AppCompatActivity {
    Application app = new Application();
    FrameLayout goBack;
    Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_card_layout);

        RecyclerView recyclerView = findViewById(R.id.donateRCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(DonationRecord.this));

        DonateAdapter adapter = new DonateAdapter(DonationRecord.this, utils.getUserDonateRegister(app.getUserDonateRegister()));
        recyclerView.setAdapter(adapter);

        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}