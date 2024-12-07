package com.example.assignment2.models;

import com.google.firebase.Timestamp;

public class VolunteerRegister {
    private String userID;
    private String donateSiteId;
    private String status = "On Process";
    private String timeRegister;
    public VolunteerRegister() {}

    public VolunteerRegister(String userID, String donateSiteId, String timeRegister) {
        this.userID = userID;
        this.donateSiteId = donateSiteId;
        this.status = "Registered";
        this.timeRegister = timeRegister;
    }

    public String getTimeRegister() {
        return timeRegister;
    }

    public void setTimeRegister(String timeRegister) {
        this.timeRegister = timeRegister;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDonateSiteId() {
        return donateSiteId;
    }

    public void setDonateSiteId(String donateSiteId) {
        this.donateSiteId = donateSiteId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void checkAattendance (){
        this.status = "On site";
    }
}
