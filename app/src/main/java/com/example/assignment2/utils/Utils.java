package com.example.assignment2.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.assignment2.main_screen.DonateMapScreen;
import com.example.assignment2.models.DonateRegister;

import java.util.Calendar;
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

    public void showDatePicker(EditText date, Context context) {
        // Get the current date to show it as default
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is zero-based, so we add 1 to the month
                    String dobText = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    date.setText(dobText);  // Set the selected date to the EditText
                },
                year, month, day // Initial date set to the current date
        );

        // Show the date picker
        datePickerDialog.show();
    }

    public void showTimePicker(EditText time, Context context){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    // Set the selected time to the EditText
                    String timeSelected = String.format("%02d:%02d", selectedHour, selectedMinute);
                    time.setText(timeSelected);
                },
                hour,
                minute,
                true // Set to true for 24-hour format, false for AM/PM
        );
        timePickerDialog.show();
    }

    public boolean isTimeLogical(String startTime, String endTime) {
        // Split the start and end times into hours and minutes
        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);

        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        // Compare hours first
        if (startHour < endHour) {
            return true;
        } else if (startHour > endHour) {
            return false;
        } else {
            // If hours are the same, compare minutes
            return startMinute < endMinute;
        }
    }

}
