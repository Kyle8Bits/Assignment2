package com.example.assignment2.view;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class SiteView extends RecyclerView.ViewHolder{
    public TextView location, time, date, address;
    public Button button, close;
    public LinearLayout card;
    public SiteView(@NonNull View itemView) {
        super(itemView);

        location = itemView.findViewById(R.id.locationBook);
        time = itemView.findViewById(R.id.timeStart_End);
        date = itemView.findViewById(R.id.date_created);
        address = itemView.findViewById(R.id.addressMn);
        close = itemView.findViewById(R.id.closeSite);
        card = itemView.findViewById(R.id.card_site_list);

        button = itemView.findViewById(R.id.managerAction);
    }
}
