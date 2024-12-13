package com.example.assignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.view.SiteDonorView;

import java.util.List;

public class SiteDonorAdapter extends RecyclerView.Adapter<SiteDonorView> {

    Context context;
    List<DonateRegister> list;

    @NonNull
    @Override
    public SiteDonorView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteDonorView((LayoutInflater.from(context).inflate(R.layout.participant_display_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SiteDonorView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
