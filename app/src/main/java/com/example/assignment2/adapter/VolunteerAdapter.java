package com.example.assignment2.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.view.VolunteerView;

import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerView> {

    Context context;
    List<VolunteerRegister> volunteerList;

    public VolunteerAdapter(Context context, List<VolunteerRegister> volunteerList) {
        this.context = context;
        this.volunteerList = volunteerList;
    }

    @NonNull
    @Override
    public VolunteerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
