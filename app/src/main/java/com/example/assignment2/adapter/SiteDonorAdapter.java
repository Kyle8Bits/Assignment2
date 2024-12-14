package com.example.assignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.view.SiteDonorView;

import java.util.List;

public class SiteDonorAdapter extends RecyclerView.Adapter<SiteDonorView> {

    Context context;
    List<DonateRegister> list;

    OnCheckOutListener onCheckOutListener;

    private List<DonateRegister> listFull;

    public SiteDonorAdapter(Context context, List<DonateRegister> list, OnCheckOutListener onCheckOutListener) {
        this.onCheckOutListener = onCheckOutListener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SiteDonorView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteDonorView(LayoutInflater.from(context).inflate(R.layout.participant_display_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SiteDonorView holder, int position) {
        DonateRegister register = list.get(position);
        holder.gID.setText(register.getGovernmentID());
        holder.firstName.setText(register.getFirstName());
        holder.lastName.setText(register.getLastName());

        holder.bloodType_donor.setText(register.getBloodType());
        holder.phoneNumber.setVisibility(View.GONE);


        holder.checkOut_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountInput = holder.amountCollected.getText().toString();
                if (onCheckOutListener != null) {
                    if(amountInput.isEmpty()){
                        Toast.makeText(context, "Please input the amount collected", Toast.LENGTH_SHORT).show();
                    }else{
                        onCheckOutListener.onCheckOutClick(register, Double.parseDouble(amountInput));
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnCheckOutListener{
        void onCheckOutClick(DonateRegister register, double amount);
    }

    public void removeDonor(DonateRegister register) {
        int position = list.indexOf(register);
        if (position != -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void filter(String text) {
        list.clear();
        if (text.isEmpty()) {
            list.addAll(listFull);
        } else {
            text = text.toLowerCase();
            for (DonateRegister item : listFull) {
                if (item.getGovernmentID().toLowerCase().contains(text)) {
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
