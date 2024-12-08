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

    private String status = "Open Register";
    private List<String> donationRegisterIds = new ArrayList<>();

    private String donationStartTime;
    private String donationEndTime;

    private double A_positive = 0;
    private double B_positive = 0;
    private double O_positive = 0;
    private double AB_positive = 0;
    private double A_negative = 0;
    private double B_negative = 0;
    private double O_negative = 0;
    private double AB_negative = 0;
    public DonateSite() {

    }

    public DonateSite(String name, String address, double latitude, double longitude, String phone, String managerUID,String date, String status, List<String> donationRegisterIds, String donationStartTime, String donationEndTime, double a_positive, double b_positive, double o_positive, double AB_positive, double a_negative, double b_negative, double o_negative, double AB_negative) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.managerUID = managerUID;
        this.date =  date;
        this.status = status;
        this.donationRegisterIds = donationRegisterIds;
        this.donationStartTime = donationStartTime;
        this.donationEndTime = donationEndTime;

        this.A_positive = a_positive;
        this.B_positive = b_positive;
        this.O_positive = o_positive;
        this.AB_positive = AB_positive;

        this.A_negative = a_negative;
        this.B_negative = b_negative;
        this.O_negative = o_negative;
        this.AB_negative = AB_negative;
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

    public double getABnegative() {
        return AB_negative;
    }

    public double getOnegative() {
        return O_negative;
    }

    public double getBnegative() {
        return B_negative;
    }

    public double getAnegative() {
        return A_negative;
    }

    public double getABpositive() {
        return AB_positive;
    }

    public double getOpositive() {
        return O_positive;
    }

    public double getBpositive() {
        return B_positive;
    }

    public double getApositive() {
        return A_positive;
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

    public void closeSite (){
        this.status = "Close";
    }

    public void addApositive(double apositive){
        this.A_positive += apositive;
    }
    public void addBpositive(double bpositive){
        this.B_positive += bpositive;
    }
    public void addOpositive(double opositive){
        this.O_positive += opositive;
    }
    public void addABpositive(double abpositive) {
        this.AB_positive += abpositive;
    }
    public void addAnegative(double anegative){
        this.A_negative += anegative;
    }
    public void addBnegative(double bnegative){
        this.B_negative += bnegative;
    }
    public void addOnegative(double onegative) {
        this.O_negative += onegative;
    }
    public void addABnegative(double abnegative) {
        this.AB_negative += abnegative;
    }
    public double getTotalAmount(){
        double total = this.A_positive + this.B_positive + this.O_positive + this.AB_positive + this.A_negative + this.B_negative + this.O_negative + this.AB_negative;
        return total;
    }
}
