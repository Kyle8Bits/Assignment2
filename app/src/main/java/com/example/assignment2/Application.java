package com.example.assignment2;

import android.media.tv.interactive.AppLinkInfo;
import android.util.Log;

import com.example.assignment2.models.DonateRegister;
import com.example.assignment2.models.DonateSite;
import com.example.assignment2.models.User;
import com.example.assignment2.models.VolunteerRegister;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class Application {
    private User currentUser;
    private List<DonateRegister> currentUserDonateRegister;
    private List<VolunteerRegister> currentUserVolunteer;
    private List<DonateSite> allDonateSite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void getCurrentUserData(FirebaseUser user, final UserDataCallback callback) {
        if (user != null) {
            // Fetch user data from Firestore
            db.collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {

                            Map<String, Object> data = documentSnapshot.getData();
                            currentUser = new User(
                                    data.get("firstName").toString(),
                                    data.get("lastName").toString(),
                                    data.get("email").toString(),
                                    data.get("phone").toString(),
                                    data.get("dob").toString(),
                                    data.get("idNumber").toString(),
                                    data.get("bloodType").toString()
                            );
                            callback.onSuccess(currentUser);  // Notify that data is ready
                        } else {
                            Log.d("Firestore", "No such document");
                            callback.onFailure(new Exception("No data found"));
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle error
                        Log.w("Firestore", "Error getting document", e);
                        callback.onFailure(e);  // Notify failure
                    });
        } else {
            // No user is signed in
            Log.d("Firebase", "No user is signed in");
            callback.onFailure(new Exception("No user signed in"));
        }
    }

    // Callback interface
    public interface UserDataCallback {
        void onSuccess(User userData);
        void onFailure(Exception e);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<DonateRegister> getCurrentUserDonateRegister() {
        return currentUserDonateRegister;
    }

    public void setCurrentUserDonateRegister(List<DonateRegister> currentUserDonateRegister) {
        this.currentUserDonateRegister = currentUserDonateRegister;
    }

    public List<VolunteerRegister> getCurrentUserVolunteer() {
        return currentUserVolunteer;
    }

    public void setCurrentUserVolunteer(List<VolunteerRegister> currentUserVolunteer) {
        this.currentUserVolunteer = currentUserVolunteer;
    }

    public List<DonateSite> getAllDonateSite() {
        return allDonateSite;
    }

    public void setAllDonateSite(List<DonateSite> allDonateSite) {
        this.allDonateSite = allDonateSite;
    }
}
