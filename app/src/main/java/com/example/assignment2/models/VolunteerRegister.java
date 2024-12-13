package com.example.assignment2.models;

import com.google.firebase.Timestamp;

public class VolunteerRegister {
    private String userID;
    private String donateSiteId;
    private String status = "WAITING";
    private String dateRegister;
    private String timeRegister;
    private String id;

    private String firstName;
    private String lastName;
    private String phone;
    private String gID;

    private String managerPhone;
    public VolunteerRegister() {}

    public VolunteerRegister(String userID, String donateSiteId, String status, String dateRegister, String timeRegister, String id, String firstName, String lastName, String phone, String gID, String managerPhone) {
        this.userID = userID;
        this.donateSiteId = donateSiteId;
        this.status = status;
        this.dateRegister = dateRegister;
        this.timeRegister = timeRegister;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.gID = gID;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getgID() {
        return gID;
    }

    public void setgID(String gID) {
        this.gID = gID;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }
    public String getTimeRegister() {
        return timeRegister;
    }

    public void setTimeRegister(String timeRegister) {
        this.timeRegister = timeRegister;
    }

    public String getDateRegister() {return dateRegister;}

    public void setDateRegister(String dateRegister) {this.dateRegister = dateRegister;}
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
