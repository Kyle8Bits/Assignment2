package com.example.assignment2.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

import org.w3c.dom.Text;

public class AdminView extends RecyclerView.ViewHolder{

    public TextView date, location, siteContact,address, textAdmin;
    public LinearLayout longClickLayout;
    public AdminView(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.dateVLT);
        location = itemView.findViewById(R.id.siteNameVLT);
        siteContact = itemView.findViewById(R.id.contact);
        address = itemView.findViewById(R.id.adressGoTo);
        textAdmin = itemView.findViewById(R.id.textAdmin);

        longClickLayout = itemView.findViewById(R.id.longClickAdmin);
    }
}
