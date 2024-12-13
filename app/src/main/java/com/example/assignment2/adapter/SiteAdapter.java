package com.example.assignment2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.Application;
import com.example.assignment2.R;
import com.example.assignment2.list_screen.SiteVolunteerList;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.VolunteerRegister;
import com.example.assignment2.view.SiteView;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteView> {
    Context context;
    List<DonateSite> siteList;
    onActionListener onActionListener;
    onCloseSiteListener onCloseSiteListener;
    onClickListener onClickListener;
    List<VolunteerRegister> volunteerRegisterList;

    Application app = new Application();

    public SiteAdapter(Context context, List<DonateSite> siteList, onActionListener onActionListener, onCloseSiteListener onCloseSiteListener, onClickListener onClickListener, List<VolunteerRegister> volunteerRegisterList) {
        this.context = context;
        this.siteList = siteList;
        this.onActionListener = onActionListener;
        this.onCloseSiteListener = onCloseSiteListener;
        this.volunteerRegisterList = volunteerRegisterList;
        this.onClickListener = onClickListener;
    }

    String currentUserId = app.getCurrentUser().getUserId();

    @NonNull
    @Override
    public SiteView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteView(LayoutInflater.from(context).inflate(R.layout.card_sites_management, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SiteView holder, int position) {
        DonateSite site = siteList.get(position);
        holder.location.setText(siteList.get(position).getName());
        holder.time.setText(siteList.get(position).getDonationStartTime() + " - " + siteList.get(position).getDonationEndTime());
        holder.blood.setText(String.valueOf(siteList.get(position).getBloodCollectType()));
        holder.address.setText(siteList.get(position).getAddress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(site);
            }
        });

        if(site.getStatus().equals("Open Register")){
            if(isVolunteer(volunteerRegisterList, site, currentUserId)){
                holder.button.setText("Cancle volunteer");
            }
            else{
                holder.button.setText("Volunteer");
            }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isVolunteer(volunteerRegisterList, site, currentUserId)) {
                        onActionListener.OnAction(site, true);
                        holder.button.setText("Cancle volunteer");
                    }else {
                        onActionListener.OnAction(site, false);
                        holder.button.setText("Volunteer");
                    }

                }
            });

            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCloseSiteListener.OnClose(site);
                }
            });


        }
        else {
            holder.close.setVisibility(View.GONE);
            holder.button.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }

    public interface onActionListener{
        void OnAction(DonateSite site, boolean status);
    }

    public interface onCloseSiteListener{
        void OnClose(DonateSite site);
    }

    public interface onClickListener{
        void OnClick(DonateSite site);
    }

    private boolean isVolunteer(List<VolunteerRegister> list, DonateSite site, String curentUserId){
        if(list != null){
            for(VolunteerRegister register : list){
                if(register.getDonateSiteId().equals(site.getSiteId()) && register.getUserID().equals(curentUserId)){
                    return true;
                }
            }
        }

        return false;
    }


}
