package com.example.assignment2.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class DonateView extends RecyclerView.ViewHolder {
    public TextView donationSiteName;
    TextView date;
    TextView bloodAmount;
    TextView status;

    public DonateView(@NonNull View itemView) {
        super(itemView);
        donationSiteName =  itemView.findViewById(R.id.donationSiteName);
        date = itemView.findViewById(R.id.date);
        bloodAmount = itemView.findViewById(R.id.bloodAmount);
        status = itemView.findViewById(R.id.status);
    }
}
