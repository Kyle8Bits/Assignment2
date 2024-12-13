package com.example.assignment2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.view.DonateView;

import java.util.List;

public class DonateAdapter extends RecyclerView.Adapter<DonateView> {

    Context context;
    List<DonateRegister> donateList;

    public DonateAdapter(Context context, List<DonateRegister> donateList) {
        this.context = context;
        this.donateList = donateList;
    }
    @NonNull 
    @Override
    public DonateView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonateView(LayoutInflater.from(context).inflate(R.layout.donation_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonateView holder, int position) {
        holder.donationSiteName.setText(donateList.get(position).getSiteName());
        holder.date.setText(donateList.get(position).getDateRegister());
        holder.bloodAmount.setText(String.valueOf(donateList.get(position).getDonationAmount()));

        if(!donateList.get(position).getStatus().equals("DONE")){
            holder.buttons.setVisibility(View.VISIBLE);
            holder.status.setTextColor(Color.parseColor("#eb9234"));
            holder.time.setVisibility(View.VISIBLE);
        }

        holder.timeRegis.setText(donateList.get(position).getTimeRegister());
        holder.status.setText(donateList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return donateList.size();
    }
}
