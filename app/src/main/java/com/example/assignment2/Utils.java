package com.example.assignment2;

import com.example.assignment2.models.DonateRegister;

import java.util.List;

public class Utils {

    public double getUserTotalBloodAmount(List<DonateRegister> donateRegisters, String ID) {
        double total = 0;

        for (DonateRegister donateRegister : donateRegisters) {
            if (donateRegister.getUserID().equals(ID) && donateRegister.getStatus().equals("DONE")) {
                total += donateRegister.getDonationAmount();
            }
        }

        return total;
    }

}
