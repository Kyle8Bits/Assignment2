package com.example.assignment2.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class DonateView extends RecyclerView.ViewHolder {
    public TextView donationSiteName;
    public TextView date;
    public TextView bloodAmount;
    public TextView status;
    public TextView timeRegis;

    public LinearLayout buttons, time;

    public DonateView(@NonNull View itemView) {
        super(itemView);
        donationSiteName =  itemView.findViewById(R.id.donationSiteName);
        date = itemView.findViewById(R.id.date);
        bloodAmount = itemView.findViewById(R.id.bloodAmount);
        status = itemView.findViewById(R.id.status);
        buttons = itemView.findViewById(R.id.buttonLayout);
        time = itemView.findViewById(R.id.timeDisplay);
        timeRegis = itemView.findViewById(R.id.timeRegis);
    }
}
