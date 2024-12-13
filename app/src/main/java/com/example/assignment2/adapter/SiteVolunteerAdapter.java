package com.example.assignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.view.DonateView;
import com.example.assignment2.view.SiteVolunteerView;

import java.util.List;

public class SiteVolunteerAdapter extends RecyclerView.Adapter<SiteVolunteerView>  {
    Context context;
    List<VolunteerRegister> list;

    public SiteVolunteerAdapter(Context context, List<VolunteerRegister> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public SiteVolunteerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteVolunteerView(LayoutInflater.from(context).inflate(R.layout.participant_display_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SiteVolunteerView holder, int position) {
        VolunteerRegister register = list.get(position);
        holder.gID.setText(register.getgID());
        holder.firstName.setText(register.getFirstName());
        holder.lastName.setText(register.getLastName());
        holder.phoneParti.setText(register.getUserPhone());

        holder.donorInfo.setVisibility(View.GONE);
        holder.checkOutGroup.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
