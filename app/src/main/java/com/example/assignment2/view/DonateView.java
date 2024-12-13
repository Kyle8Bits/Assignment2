package com.example.assignment2.view;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class DonateView extends RecyclerView.ViewHolder {
    public TextView donationSiteName, timeRegis, status, bloodAmount, date;
    public LinearLayout buttons, time;
    public Button cancel;

    public DonateView(@NonNull View itemView) {
        super(itemView);
        donationSiteName =  itemView.findViewById(R.id.siteNamVLT);
        date = itemView.findViewById(R.id.phoneManager);
        bloodAmount = itemView.findViewById(R.id.bloodAmount);
        status = itemView.findViewById(R.id.timeVolunteer);
        buttons = itemView.findViewById(R.id.vlt_buttons);
        cancel = itemView.findViewById(R.id.cancleButton);
        time = itemView.findViewById(R.id.timeDisplay);
        timeRegis = itemView.findViewById(R.id.timeRegis);
    }
}
