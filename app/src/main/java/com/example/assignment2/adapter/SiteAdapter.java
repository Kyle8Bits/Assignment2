package com.example.assignment2.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.models.DonateSite;
import com.example.assignment2.view.SiteView;

import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteView> {
    Context context;
    List<DonateSite> siteList;

    public SiteAdapter(Context context, List<DonateSite> siteList) {
        this.context = context;
        this.siteList = siteList;
    }

    @NonNull
    @Override
    public SiteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SiteView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }
}
