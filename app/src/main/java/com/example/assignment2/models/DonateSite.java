package com.example.assignment2.models;

import java.util.ArrayList;
import java.util.List;

public class DonateSite {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String phone;
    private String managerUID;
    private String date;

    private String status;
    private List<String> donationRegisterIds = new ArrayList<>();
    private List<String> volunteerRegisterIds = new ArrayList<>();

    private String donationStartTime;
    private String donationEndTime;

    private double amountOfBlood;
    private String bloodCollectType;

    private String siteId;

    public DonateSite() {

    }

    public DonateSite(String name, String address, double latitude, double longitude, String phone, String managerUID, String date, String status, List<String> donationRegisterIds, List<String> volunteerRegisterIds, String donationStartTime, String donationEndTime, double amountOfBlood, String bloodCollectType, String siteId) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.managerUID = managerUID;
        this.date =  date;
        this.status = status;
        this.donationRegisterIds = donationRegisterIds;
        this.volunteerRegisterIds = volunteerRegisterIds;
        this.donationStartTime = donationStartTime;
        this.donationEndTime = donationEndTime;
        this.amountOfBlood = amountOfBlood;
        this.bloodCollectType = bloodCollectType;
        this.siteId = siteId;
    }

    public double getAmountOfBlood() {
        return amountOfBlood;
    }
    public void setAmountOfBlood(double ammountOfBlood) {
        this.amountOfBlood = ammountOfBlood;
    }
    public List<String> getVolunteerRegisterIds() {
        return volunteerRegisterIds;
    }

    public void setVolunteerRegisterIds(List<String> volunteerRegisterIds) {
        this.volunteerRegisterIds = volunteerRegisterIds;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getBloodCollectType() {
        return bloodCollectType;
    }

    public void setBloodCollectType(String bloodCollectType) {
        this.bloodCollectType = bloodCollectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getDonationRegisterIds() {
        return donationRegisterIds;
    }

    public void setDonationRegisterIds(List<String> donationRegisterIds) {
        this.donationRegisterIds = donationRegisterIds;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getManagerUID() {
        return managerUID;
    }

    public void setManagerUID(String managerUID) {
        this.managerUID = managerUID;
    }

    public String getDonationStartTime() {
        return donationStartTime;
    }

    public void setDonationStartTime(String donationStartTime) {
        this.donationStartTime = donationStartTime;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getDonationEndTime() {
        return donationEndTime;
    }

    public void setDonationEndTime(String donationEndTime) {
        this.donationEndTime = donationEndTime;
    }

    public void addDonationRegisterId(String donationRegisterId) {
        if (donationRegisterId != null && !donationRegisterId.isEmpty()) {
            this.donationRegisterIds.add(donationRegisterId);
        }
    }

    public void addBlood(double blood){
        this.amountOfBlood += blood;
    }
}
