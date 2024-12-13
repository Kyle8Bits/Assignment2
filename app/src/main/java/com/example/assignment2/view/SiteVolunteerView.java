package com.example.assignment2.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class SiteVolunteerView extends RecyclerView.ViewHolder {

    public TextView gID, firstName, lastName, phoneParti, bloodType_donor, birth_day;
    public EditText amountCollected;
    public Button checkOut_button;
    public LinearLayout donorInfo, checkOutGroup;
    public SiteVolunteerView(@NonNull View itemView) {
        super(itemView);
        gID = itemView.findViewById(R.id.gID);
        firstName = itemView.findViewById(R.id.firstName);
        lastName = itemView.findViewById(R.id.lastName);
        phoneParti = itemView.findViewById(R.id.phoneParti);

        bloodType_donor = itemView.findViewById(R.id.bloodType_donor);
        birth_day = itemView.findViewById(R.id.birth_day);

        amountCollected = itemView.findViewById(R.id.amountCollected);
        checkOut_button = itemView.findViewById(R.id.checkOut_button);

        donorInfo = itemView.findViewById(R.id.donorInfo);
        checkOutGroup = itemView.findViewById(R.id.checkOutGroup);
    }
}
