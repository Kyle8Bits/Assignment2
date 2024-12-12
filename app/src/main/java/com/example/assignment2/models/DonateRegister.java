package com.example.assignment2.models;
import com.google.firebase.Timestamp;

public class DonateRegister {
    private String id;
    private String donateSiteId;
    private String siteName;
    private String userID;
    private String status;
    private String timeRegister;
    private String dateRegister;
    private String bloodType;
    private double donationAmount = 0;
    private String lastName;
    private String firstName;
    private String dob;
    private String governmentID;
    public DonateRegister() {}

    public DonateRegister(String donateSiteId, String userID, String timeRegister, String dateRegister, String bloodType, String lastName, String firstName, String dob, String governmentID, double donationAmount, String status,String siteName, String id) {
        this.donateSiteId = donateSiteId;
        this.id = id;
        this.userID = userID;
        this.status = status;
        this.timeRegister = timeRegister;
        this.dateRegister = dateRegister;
        this.bloodType = bloodType;
        this.donationAmount = donationAmount;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.governmentID = governmentID;
        this.siteName = siteName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeRegister() {
        return timeRegister;
    }

    public String getDateRegister() {return dateRegister;}

    public void setDateRegister(String dateRegister) {this.dateRegister = dateRegister;}

    public void setTimeRegister(String timeRegister) {
        this.timeRegister = timeRegister;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmmount) {
        this.donationAmount = donationAmmount;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGovernmentID() {
        return governmentID;
    }

    public void setGovernmentID(String governmentID) {
        this.governmentID = governmentID;
    }

    public void completed(double bloodAmount){
        this.donationAmount += bloodAmount;
        this.status = "Finished";
    }
}