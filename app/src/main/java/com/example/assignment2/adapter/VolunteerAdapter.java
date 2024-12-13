package com.example.assignment2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.view.VolunteerView;

import java.util.List;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerView> {

    Context context;
    List<VolunteerRegister> volunteerList;
    onCancelListener onCancelListener;

    public VolunteerAdapter(Context context, List<VolunteerRegister> volunteerList, onCancelListener onCancelListener) {
        this.context = context;
        this.volunteerList = volunteerList;
        this.onCancelListener = onCancelListener;
    }

    @NonNull
    @Override
    public VolunteerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VolunteerView(LayoutInflater.from(context).inflate(R.layout.volunteer_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerView holder, int position) {
        VolunteerRegister register = volunteerList.get(position);
        holder.date.setText(volunteerList.get(position).getDateRegister());
        holder.locationName.setText(volunteerList.get(position).getSiteName());
        holder.contact.setText(volunteerList.get(position).getManagerPhone());
        holder.address.setText(volunteerList.get(position).getAddress());
        holder.status.setText(volunteerList.get(position).getStatus());

        if(!volunteerList.get(position).getStatus().equals("Completed")){
            holder.buttons.setVisibility(View.VISIBLE);
            holder.timeLayout.setVisibility(View.VISIBLE);
            holder.time.setText(volunteerList.get(position).getTimeRegister());

            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCancelListener != null){
                        onCancelListener.onCancel(register);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return volunteerList.size();
    }

    public void removeVolunteerRegister(VolunteerRegister register) {
        int position = volunteerList.indexOf(register);
        if (position != -1) {
            volunteerList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface onCancelListener{
        void onCancel(VolunteerRegister register);
    }
}
