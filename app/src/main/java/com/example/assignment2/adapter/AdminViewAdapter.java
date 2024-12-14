package com.example.assignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.view.AdminView;
import com.example.assignment2.view.VolunteerView;

import java.util.List;

public class AdminViewAdapter extends RecyclerView.Adapter<AdminView>{

    Context context;
    List<DonateSite> donateSiteList;
    OnLongClick onLongClick;

    public AdminViewAdapter(Context context, List<DonateSite> donateSiteList, OnLongClick onLongClick) {
        this.context = context;
        this.donateSiteList = donateSiteList;
        this.onLongClick = onLongClick;
    }

    @NonNull
    @Override
    public AdminView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminView(LayoutInflater.from(context).inflate(R.layout.volunteer_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminView holder, int position) {
        DonateSite donateSite = donateSiteList.get(position);
        holder.date.setText(donateSite.getDate());
        holder.location.setText(donateSite.getName());
        holder.siteContact.setText(donateSite.getPhone());
        holder.address.setText(donateSite.getAddress());

        holder.textAdmin.setText("Location:");

        holder.longClickLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClick.onLongClick(donateSite);
                Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show();
                return true; // Return true to indicate the event was handled
            }
        });
    }

    @Override
    public int getItemCount() {
        return donateSiteList.size();
    }

    public interface OnLongClick{
        void onLongClick(DonateSite donateSite);
    }
}
