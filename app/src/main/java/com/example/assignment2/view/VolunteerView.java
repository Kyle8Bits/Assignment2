package com.example.assignment2.view;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class VolunteerView extends RecyclerView.ViewHolder {

    public TextView date, locationName, contact, status, time, address;
    public LinearLayout buttons, timeLayout;
    public Button cancel;

    public VolunteerView(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.dateVLT);
        locationName = itemView.findViewById(R.id.siteNameVLT);
        contact = itemView.findViewById(R.id.contact);
        address = itemView.findViewById(R.id.adressGoTo);
        status = itemView.findViewById(R.id.status);
        cancel = itemView.findViewById(R.id.vlt_cancel_but);
        buttons = itemView.findViewById(R.id.vcl_buttons_layout);
        timeLayout = itemView.findViewById(R.id.timeLayout);

        time = itemView.findViewById(R.id.timeGoTo);


    }
}
